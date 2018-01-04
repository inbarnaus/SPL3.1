package bgu.spl181.net.movierental;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class USTBP implements BidiMessagingProtocol<Command>{

    private Connections<Command> connections;
    private int connectionId;
    private Database<Command> database;

    public USTBP(Database<Command> database){
        this.database=database;
    }

    @Override
    public void start(int connectionId, Connections<Command> connections) {
        this.connections = connections;
        this.connectionId=connectionId;
    }

    public void process(Command message){
        message.execute(database, connections,connectionId);
    }
}
