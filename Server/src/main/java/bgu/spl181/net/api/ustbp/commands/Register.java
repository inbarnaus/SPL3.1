package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.RentalServiceSection;
import bgu.spl181.net.api.ustbp.User;

import java.util.List;

public class Register extends Command {
    private String username;
    private String password;
    private List<String> dataBlock;

    public Register(String username, String password, List<String> dataBlock) {
        this.name="REGISTER";
        this.username = username;
        this.password = password;
        this.dataBlock = dataBlock;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(List<String> dataBlock) {
        this.dataBlock = dataBlock;
    }

    @Override
    public void execute(Database database, Connections connections, int connectionId) {
        User user=database.checkIfExist(username);
        if(user!=null || connections.isLoggedIn(connectionId))
            connections.send(connectionId, new ERRORCommand("registration failed"));
        else
            connections.send(connectionId, new ACKCommand("registration succeeded"));
    }
}
