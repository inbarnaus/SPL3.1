package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.bidi.ConnectionHandler;

import java.io.IOException;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void broadcast(T msg);

    void disconnect(int connectionId);

    void logIn(int connectionsId);

    boolean isLoggedIn(int connectionId);

    void add(int connectionid, ConnectionHandler<T> handler);
}
