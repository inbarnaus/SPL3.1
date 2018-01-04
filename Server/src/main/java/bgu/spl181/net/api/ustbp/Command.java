package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.Connections;

import java.io.Serializable;
import java.util.List;

public abstract class Command implements Serializable {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract void execute(Connections<Command> connections, int connectionsId);

}
