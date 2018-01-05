package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;

import java.util.List;

public class Request extends Command {
    private String reqName;
    private List<String> parameters;

    public Request(String reqName, List<String> parameters){
        this.reqName=reqName;
        this.parameters=parameters;
    }

    @Override
    public void execute(Database database, Connections connections, int connectionId) {

    }
}
