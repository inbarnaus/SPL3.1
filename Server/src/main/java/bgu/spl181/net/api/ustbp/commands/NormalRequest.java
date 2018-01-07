package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.User;
import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieDatabase;
import bgu.spl181.net.srv.TPCConnections;

import java.util.List;

public class NormalRequest extends Request {
    private Database database;
    public NormalRequest(String reqName, List<String> parameters) {
        super(reqName, parameters);
    }

    @Override
    public void execute(Database database, Connections connections, int connectionId) {
        this.database=database;
        boolean logedIn=connections.isLoggedIn(connectionId);
        User user=((TPCConnections)connections).idToUser(connectionId);
        switch (parameters.get(1)){
            case "balance":
                if(parameters.get(2).equals("info")){
                    if(!logedIn)
                        connections.send(connectionId, new ERRORCommand("request balance info failed"));
                    else
                        ((TPCConnections) connections).send(connectionId, new ACKCommand("balance "+ user.getBalance()));
                }
                else if(parameters.get(2).equals("add")){
                    if(!logedIn || parameters.size()<4)
                        connections.send(connectionId, new ERRORCommand("request balance add failed"));
                    else{
                        Integer amount=Integer.getInteger(parameters.get(3));
                        if(amount<1)
                            connections.send(connectionId, new ERRORCommand("request balance add failed"));
                        else {
                            user.addBalance(amount);
                            ((TPCConnections) connections).send(connectionId,
                                    new ACKCommand("balance "+ user.getBalance()+" added "+amount));
                        }
                    }
                }
            case "rent":
                if(!logedIn || parameters.size()<3)
                    connections.send(connectionId, new ERRORCommand("request rent failed"));
                else{
                    boolean canRent= userCanRent(user, parameters.get(2));
                }

        }
    }

    private boolean userCanRent(User user, String movie){
        Movie movieRental=((MovieDatabase)database).getMovie(movie);
        if(movieRental==null)
            return false;
        if(user.getBalance()< movieRental.getPrice())
            return false;
        if()
    }
}
