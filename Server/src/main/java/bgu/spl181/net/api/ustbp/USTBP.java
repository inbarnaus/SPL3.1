package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

import java.io.Serializable;

public abstract class USTBP  implements BidiMessagingProtocol<Serializable>{

    private Connections<Serializable> connections;
    private int connectionId;
    private Database<Serializable> database;

    public USTBP(Database<Serializable> database){
        this.database=database;
    }

    @Override
    public void start(int connectionId, Connections<Serializable> connections) {
        this.connections = connections;
        this.connectionId=connectionId;
    }

    public void process(Serializable message){

    }
}
