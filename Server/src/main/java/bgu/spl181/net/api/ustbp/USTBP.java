package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

public abstract class USTBP  implements BidiMessagingProtocol<Command>{

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
        throw new NotImplementedException();
    }

    @Override
    public boolean shouldTerminate() {
        throw new NotImplementedException();
    }
}
