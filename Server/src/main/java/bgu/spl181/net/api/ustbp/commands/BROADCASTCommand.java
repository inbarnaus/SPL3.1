package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;

public class BROADCASTCommand extends Command {
    private String message;

    public BROADCASTCommand(String message) {
        this.name="BROADCAST";
        this.message = message;
    }

    public String toString(){
        return name+" "+message;
    }
    @Override
    public void execute(Database database, Connections<Command> connections, int connectionId) {}
}
