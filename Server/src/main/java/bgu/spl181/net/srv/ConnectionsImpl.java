package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.User;
import bgu.spl181.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private Map<Integer, ConnectionHandler<T>> handlers = new ConcurrentHashMap<>();
    private Map<Integer, ConnectionHandler<T>> loggedin = new ConcurrentHashMap<>();
    private Map<String, Integer> loggedinUsers = new ConcurrentHashMap<>();

    @Override
    public boolean send(int connectionId, T msg) {
        System.out.println("bla");
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
                loggedinUsers.remove(idToUser(connectionId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void add(int connectionid ,ConnectionHandler<T> handler){
        handlers.put(connectionid, handler);
    }

    @Override
    public void logIn(int connectionId, User user) {
        loggedin.put(connectionId, handlers.get(connectionId));
        loggedinUsers.put(user.getUsername(),connectionId);

    }

    @Override
    public boolean isLoggedIn(int connectionId) {
        return loggedin.containsKey(connectionId);
    }

    @Override
    public boolean isLoggedIn(String username) {
        return loggedinUsers.containsKey(username);
    }

    public String idToUser(int connectionId){
        Iterator it = loggedinUsers.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getValue()==connectionId){
                return (String)pair.getKey();
            }
        }
        return null;
    }
}
