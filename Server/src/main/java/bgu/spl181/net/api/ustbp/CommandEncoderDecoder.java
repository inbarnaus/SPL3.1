package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.MessageEncoderDecoder;
import bgu.spl181.net.api.ustbp.commands.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEncoderDecoder implements MessageEncoderDecoder<Command> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    @Override
    public Command decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\n') {
            return popString();
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(Command message) {
        return (message + "\n").getBytes(); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private Command popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        String[] commandParts=result.split(" ");
        String commandName=commandParts[0];
        switch (commandName){
            case "REGISTER":
                if(commandParts.length>2){
                    List<String> datablock=new ArrayList<>();
                    for(int i=3; i<commandParts.length ;i++){
                        datablock.add(commandParts[i]);
                    }
                    return new Register(commandParts[1],commandParts[2],datablock);
                }
                return new Register(commandParts[1],commandParts[2],null);
            case "LOGIN":
                return new Login(commandParts[1],commandParts[2]);
            case "SIGNOUT":
                return new Signout();
            case "REQUEST":
                List<String> parameters=new ArrayList<>();
                for(int i=2; i<commandParts.length ;i++){
                    parameters.add(commandParts[i]);
                }
                switch (commandParts[1]){
                    case "rent":
                        return new NormalRequest(commandParts[1], parameters);
                    case "return":
                        return new NormalRequest(commandParts[1], parameters);
                    case "info":
                        return new NormalRequest(commandParts[1], parameters);
                    case "balance":
                        return new NormalRequest(commandParts[1], parameters);
                    case "remmovie":
                        return new AdminRequest(commandParts[1], parameters);
                    case "addmovie":
                        return new AdminRequest(commandParts[1], parameters);
                    case "changeprice":
                        return new AdminRequest(commandParts[1], parameters);
                }

        }
        return null;
    }
}
