package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.User;
import bgu.spl181.net.impl.movierental.MovieUser;
import bgu.spl181.net.srv.TPCConnections;

import java.util.List;

public class AdminRequest extends Request {

    public AdminRequest(String reqName, List<String> parameters) {
        super(reqName, parameters);
    }

    @Override
    public void execute(Database database, Connections<Command> connections, int connectionId) {
        User user=((TPCConnections)connections).idToUser(connectionId);
        boolean logedIn=connections.isLoggedIn(connectionId) && ((MovieUser)user).isAdmin();
        switch (parameters.get(1)){
            case "remmovie":
                if(!logedIn)
                    ((TPCConnections)connections).send(connectionId, new ERRORCommand("request remmovie failed"));
                else
                    ((TPCConnections)connections).send(connectionId,);
            case "addmovie":
            case "changeprice":
        }
    }
}
