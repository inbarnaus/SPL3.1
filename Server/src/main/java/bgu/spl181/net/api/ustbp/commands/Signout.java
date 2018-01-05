package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;

public class Signout extends Command {

    @Override
    public void execute(Database database, Connections connections, int connectionId) {
        boolean ans = connections.isLoggedIn(connectionId);
        if(ans)
            connections.send(connectionId, new ERRORCommand("signout failed"));
        else
            connections.send(connectionId, new ACKCommand("signout succeeded"));
    }
}
