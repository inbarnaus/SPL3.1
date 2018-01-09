package bgu.spl181.net.srv;

import bgu.spl181.net.api.MessageEncoderDecoder;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.srv.bidi.ConnectionHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BlockingConnectionHandler<T> implements Runnable, ConnectionHandler<T> {

    private final BidiMessagingProtocol<T> protocol;
    private final MessageEncoderDecoder<T> encdec;
    private final Socket sock;
    private final Connections<T> connections;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private volatile boolean connected = true;
    private boolean print =false;


    public BlockingConnectionHandler(Socket sock, BidiMessagingProtocol<T> protocol, MessageEncoderDecoder<T> reader, Connections<T> connections) {
        this.sock = sock;
        this.encdec = reader;
        this.protocol = protocol;
        this.connections = connections;
    }

    @Override
    public void run() {
        protocol.start(sock.getPort(), connections);
        try (Socket sock = this.sock) { //just for automatic closing
            int read;

            in = new BufferedInputStream(sock.getInputStream());
            out = new BufferedOutputStream(sock.getOutputStream());
            if(print){
                System.out.println("Shouldterminate  is "+protocol.shouldTerminate()+" for client: "+sock.getPort());
            }
            while (!protocol.shouldTerminate() && connected && (read = in.read()) >= 0) {
                if(print){
                    System.out.println("Got bytes from client: "+sock.getPort());
                }
                T nextMessage = encdec.decodeNextByte((byte) read);
                if (nextMessage != null) {
                    if(print){
                        System.out.println("Got message from client: "+sock.getPort());
                    }
                    protocol.process(nextMessage);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void send(T msg) {
        try{
            if (msg != null) {
                out.write(encdec.encode(msg));
                out.flush();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        connected = false;
        sock.close();
    }

    public void setPrint(boolean print) {
        this.print = print;
    }
}
