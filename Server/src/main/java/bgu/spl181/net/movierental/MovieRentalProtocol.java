package bgu.spl181.net.movierental;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.commands.Register;

import java.util.HashMap;
import java.util.List;

public class MovieRentalProtocol implements BidiMessagingProtocol<Command> {
    private Connections<Command> connections;
    private int connectionId;
    private List<Movie> movies;
    private HashMap<String, MovieUser> users;
    @Override
    public void start(int connectionId, Connections<Command> connections) {
        this.connections=connections;
        this.connectionId=connectionId;
    }

    @Override
    public void process(Command message) {
 //       message.execute(connections, connections);
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    private Command handle(Command message){
        String commandName=message.getName();
        Command response =null;
        switch (commandName){
            case "REGISTER":
                response=handleRegister((Register) message);
                break;
        }
        return response;
    }

    private Command handleRegister(Register resiter){

    }
}
