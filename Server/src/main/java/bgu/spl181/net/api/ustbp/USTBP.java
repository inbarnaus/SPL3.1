package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.commands.ACKCommand;
import bgu.spl181.net.api.ustbp.commands.ERRORCommand;
import bgu.spl181.net.impl.movierental.MovieUser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class USTBP  implements BidiMessagingProtocol<Command>{

    protected Connections<Command> connections;
    protected int connectionId;
    protected Database<String> database;
    protected String username;

    public USTBP(Database<String> database){
        this.database=database;
    }

    @Override
    public void start(int connectionId, Connections<Command> connections) {
        this.connections = connections;
        this.connectionId=connectionId;
    }

    public void process(String message){
        String[] commandParts = message.split(" ");
        switch (commandParts[0]){
            case "LOGIN":
                User user=database.checkIfExist(commandParts[1]);
                if(commandParts.length<3 || user==null || connections.isLoggedIn(connectionId) || !user.correctPassword(commandParts[2]))
                    connections.send(connectionId, new ERRORCommand("login failed"));
                else{//TODO need to check if username is logged in
                    connections.logIn(connectionId,user);
                    connections.send(connectionId, new ACKCommand("login succeeded"));
                }

            case "SIGNOUT":
                boolean ans = connections.isLoggedIn(connectionId);
                if(ans)
                    connections.send(connectionId, new ERRORCommand("signout failed"));
                else
                    connections.send(connectionId, new ACKCommand("signout succeeded"));

            case "REGISTER":
                registerCommand(commandParts);

            case "REQUEST":
                requestCommands(commandParts);
        }
    }

    public abstract void requestCommands(String[] commandParts);

    public void registerCommand(String[] commandParts){
        User user1=database.checkIfExist(commandParts[1]);
        if(commandParts.length<3 || user1!=null || connections.isLoggedIn(connectionId))
            connections.send(connectionId, new ERRORCommand("registration failed"));
        else {
            String[] country=commandParts[3].split("\"\"");
            User newUser=new MovieUser(commandParts[1], commandParts[2],country[1], "normal");
            database.addUser(newUser);
            connections.send(connectionId, new ACKCommand("registration succeeded"));
        }
    }

    @Override
    public boolean shouldTerminate() {
        throw new NotImplementedException();
    }
    public void setUsername(String username){ this.username=username; }
}
