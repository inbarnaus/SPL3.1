package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.ustbp.Command;

import java.util.List;

public class Register extends Command {
    private String username;
    private String password;
    private List<String> dataBlock;

    public Register(String name,String username, String password, List<String> dataBlock) {
        this.name=name;
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
}
