package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TPCConnections<T> implements Connections<T> {
    private Map<Integer, ConnectionHandler<T>> handlers = new ConcurrentHashMap<>();
    private Map<Integer, ConnectionHandler<T>> loggedin = new ConcurrentHashMap<>();

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
        Iterator it = loggedin.entrySet().iterator();
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
                loggedin.remove(connectionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void add(int connectionid ,ConnectionHandler<T> handler){
        handlers.put(connectionid, handler);
    }

    @Override
    public void logIn(int connectionsId) {
        loggedin.put(connectionsId, handlers.get(connectionsId));
    }

    @Override
    public boolean isLoggedIn(int connectionId) {
        return loggedin.containsKey(connectionId);
    }
}
