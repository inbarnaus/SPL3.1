package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.User;

public class Login extends Command {
    private String username;
    private String password;

    public Login(String username, String password){
        this.password=password;
        this.username=username;
    }
    @Override
    public void execute(Database arg, Connections connections, int connectionId) {
        User user=arg.checkIfExist(username);
        if(user==null || connections.isLoggedIn(connectionId) || !user.correctPassword(password))
            connections.send(connectionId, new ERRORCommand("login failed"));
        else{//TODO need to check if username is logged in
            connections.logIn(connectionId);
            connections.send(connectionId, new ACKCommand("login succeeded"));
        }
    }
}
