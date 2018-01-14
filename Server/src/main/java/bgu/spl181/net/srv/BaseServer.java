package bgu.spl181.net.srv;

import bgu.spl181.net.api.MessageEncoderDecoder;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server<T> {

    private final int port;
    protected final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    protected final Supplier<MessageEncoderDecoder<T>> encoderdecoderFactory;
    protected final  Connections<T> connections;
    private ServerSocket sock;


    public BaseServer(int port, Supplier<BidiMessagingProtocol<T>> protocolFactory, Supplier<MessageEncoderDecoder<T>> encoderdecoderFactory) {
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encoderdecoderFactory = encoderdecoderFactory;
        this.connections = new ConnectionsImpl<>();
        this.sock = null;
    }

    @Override
    public void serve() {

        try (ServerSocket serverSock = new ServerSocket(port)) {
			System.out.println("Server started");

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {
                Socket sock = serverSock.accept();
                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler<>(
                        sock,
                        protocolFactory.get(),
                        encoderdecoderFactory.get(),
                        connections
                );
                handler.setPrint(false);
                connections.add(sock.getPort(), handler);
                execute(handler);

            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
		if (sock != null)
			sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T> handler);

}
