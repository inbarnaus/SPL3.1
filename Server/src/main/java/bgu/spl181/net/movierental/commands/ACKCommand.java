package bgu.spl181.net.movierental.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.movierental.Command;
import bgu.spl181.net.movierental.Database;

import java.io.Serializable;

public class ACKCommand extends Command {
    private String message;

    public ACKCommand(String message) {
        this.name="ACK";
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ACK "+ message ;
    }

    @Override
    public void execute(Database database, Connections connections, int connectionId) {

    }
}
