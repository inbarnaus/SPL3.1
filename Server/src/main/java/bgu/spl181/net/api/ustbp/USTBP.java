package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.commands.ACKCommand;
import bgu.spl181.net.api.ustbp.commands.ERRORCommand;
import bgu.spl181.net.impl.movierental.MovieUser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class USTBP implements BidiMessagingProtocol<Serializable>{

    protected Connections<Serializable> connections;
    protected int connectionId;
    protected Database<Serializable> database;
    protected String username;

    public USTBP(Database<Serializable> database){
        this.database=database;
    }

    @Override
    public void start(int connectionId, Connections<Serializable> connections) {
        this.connections = connections;
        this.connectionId=connectionId;
    }

    public void process(Serializable message){
        String[] moviesParts=((String)message).split("\"");
        List<String> commandParts=new ArrayList<>();
        for(int i=0; i<moviesParts.length; i++){
            if(moviesParts[i].contains("\""))
                commandParts.add(moviesParts[i]);
            else{
                String[] s=moviesParts[i].split(" ");
                for(int j=0;j<s.length;j++)
                    commandParts.add(s[j]);
            }
        }

        switch (commandParts.get(0)){
            case "LOGIN":
                User user=database.checkIfExist(commandParts.get(1));
                if(commandParts.size()<3 || user==null || connections.isLoggedIn(connectionId) || !user.correctPassword(commandParts.get(2)))
                    connections.send(connectionId, new ERRORCommand("login failed"));
                else{//TODO need to check if username is logged in
                    connections.logIn(connectionId,user);
                    connections.send(connectionId, new ACKCommand("login succeeded"));
                }
                break;
            case "SIGNOUT":
                boolean ans = connections.isLoggedIn(connectionId);
                if(ans)
                    connections.send(connectionId, new ERRORCommand("signout failed"));
                else
                    connections.send(connectionId, new ACKCommand("signout succeeded"));
                break;

            case "REGISTER":
                this.registerCommand(commandParts);
                break;

            case "REQUEST":
                requestCommands(commandParts);
                break;
        }
    }

    public abstract void requestCommands(List<String> commandParts);

    public void registerCommand(List<String> commandParts){
        User user1=database.checkIfExist(commandParts.get(1));
        if(commandParts.size()<3 || user1!=null || connections.isLoggedIn(connectionId)) {
            connections.send(connectionId, new ERRORCommand("registration failed"));

        }
        else {
            String[] country=commandParts.get(3).split("\"\"");
            User newUser=new MovieUser(commandParts.get(1), commandParts.get(2),country[1], "normal",0);
            database.addUser(newUser);
            connections.send(connectionId, new ACKCommand("registration succeeded"));

        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
    public void setUsername(String username){ this.username=username; }
}
