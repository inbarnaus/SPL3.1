package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.Connections;

import java.io.Serializable;
import java.util.List;

public abstract class Command implements Serializable{
    protected String name;;
    public String getName() {
        return name;
    }

    public abstract void execute(Database database, Connections<Command> connections, int connectionId);
/*
 public static Command generate(List<String> line){
        if(line.get(0).equals("REGISTER")){
            if(line.size()<3){
                return null;
            }
            return new Register(line.get(0),line.get(1),line.get(2), optional(3, line));
        }
        return null;
    }

    public static List<String> optional(int optionalStartidx, List<String> line){
        if(line.size()>optionalStartidx)
            return line.subList(optionalStartidx, line.size());
        return null;
    }

*/
}
