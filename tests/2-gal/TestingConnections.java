package bgu.spl181.net.impl.Blockbuster;                // Change to the name of your package

/**
 *                          TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 *
 *                          REMEMBER TO DELETE THIS FILE BEFORE SUBMISSION!!!
 *
 *                          TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 */

import bgu.spl181.net.api.bidi.Connections;

import java.util.ArrayList;
import java.util.HashMap;

public class TestingConnections<T> implements Connections<T> {
    
    // Here you have to include all your other parameters (with some modifications...)
    
    public boolean send(int connection_id, T testMesssge) {
        System.out.println(">> Sent msg to client #" + connection_id + ": " + msg + "\n");
        return true;
    }
    
    public void broadcast(T testMesssge) {
        for(Integer current_connection_id : handlers)
            System.out.println(">> Sent msg to client #" + current_connection_id + ": " + msg);
    }
    
    public void disconnect(int connection_id) {}
    
    // Here you have to include all your other Connections methods
    
}

