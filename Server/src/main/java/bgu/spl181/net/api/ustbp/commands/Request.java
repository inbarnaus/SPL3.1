package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.impl.movierental.MovieDatabase;

import java.util.List;

public abstract class Request extends Command {
    protected String reqName;
    protected List<String> parameters;

    public Request(String reqName, List<String> parameters){
        this.reqName=reqName;
        this.parameters=parameters;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public abstract void execute(Database database, Connections<Command> connections, int connectionId);
}
