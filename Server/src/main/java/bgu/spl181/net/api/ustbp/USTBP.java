package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.commands.Request;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class USTBP  implements BidiMessagingProtocol<Command>{

    protected Connections<Command> connections;
    protected int connectionId;
    protected Database<Command> database;

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
    public abstract void process(Request request);

    @Override
    public boolean shouldTerminate() {
        throw new NotImplementedException();
    }
}
