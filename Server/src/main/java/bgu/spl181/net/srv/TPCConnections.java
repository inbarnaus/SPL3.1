package bgu.spl181.net.srv;

import bgu.spl181.net.api.MessageEncoderDecoder;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class TPCConnections<T> implements Connections<T> {
    private HashMap<Integer, ConnectionHandler<T>> handlers;
    private final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private final Supplier<MessageEncoderDecoder<T>> encdecFactory;

    @Override
    public void logIn(int connectionsId) {

    }

    @Override
    public boolean isLoggedIn(int connectionId) {
        return false;
    }

    public TPCConnections(Supplier<BidiMessagingProtocol<T>> protocolFactory, Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
    }

    @Override
    public boolean send(int connectionId, T msg) {
        if(handlers.containsKey(connectionId)){
            handlers.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void broadcast(T msg) {
        Iterator it = handlers.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ((BlockingConnectionHandler)pair.getValue()).send(msg);
        }
    }

    @Override
    public void disconnect(int connectionId) {
        if(handlers.containsKey(connectionId)){
            try {
                handlers.get(connectionId).close();
                handlers.remove(connectionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void add(int connectionid ,ConnectionHandler<T> handler){
        handlers.put(connectionid, handler);
    }
}
