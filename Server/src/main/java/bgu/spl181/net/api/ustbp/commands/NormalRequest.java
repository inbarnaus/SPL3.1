package bgu.spl181.net.api.ustbp.commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.User;
import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieDatabase;
import bgu.spl181.net.impl.movierental.MovieUser;
import bgu.spl181.net.srv.TPCConnections;

import java.util.List;

public class NormalRequest extends Request {
    public NormalRequest(String reqName, List<String> parameters) {
        super(reqName, parameters);
    }

    @Override
    public void execute(Database database, Connections<Command> connections, int connectionId) {
        boolean logedIn=connections.isLoggedIn(connectionId);
        User user=((TPCConnections)connections).idToUser(connectionId);
        switch (parameters.get(1)){
            case "balance":
                if(parameters.get(2).equals("info")){
                    if(!logedIn)
                        connections.send(connectionId, new ERRORCommand("request balance info failed"));
                    else
                        ((TPCConnections) connections).send(connectionId, new ACKCommand("balance "+ ((MovieUser)user).getBalance()));
                }
                else if(parameters.get(2).equals("add")){
                    if(!logedIn || parameters.size()<4)
                        connections.send(connectionId, new ERRORCommand("request balance add failed"));
                    else{
                        Integer amount=Integer.getInteger(parameters.get(3));
                        if(amount<1)
                            connections.send(connectionId, new ERRORCommand("request balance add failed"));
                        else {
                            ((MovieUser)user).addBalance(amount);
                            ((TPCConnections) connections).send(connectionId,
                                    new ACKCommand("balance "+ ((MovieUser)user).getBalance()+" added "+amount));
                        }
                    }
                }
            case "rent":
                if(!logedIn || parameters.size()<3)
                    connections.send(connectionId, new ERRORCommand("request rent failed"));
                else{
                    String movie=parameters.get(2);
                    boolean canRent= userCanRent(user, movie, database);
                    if(canRent)
                        ((TPCConnections) connections).send(connectionId, ((MovieDatabase)database).rentMovie(movie));
                    else
                        connections.send(connectionId, new ERRORCommand("request rent failed"));
                }
            case "info":
                if(!logedIn)
                    connections.send(connectionId, new ERRORCommand("request info failed"));
                if(parameters.size()==2)
                    ((TPCConnections)connections).send(connectionId, new ACKCommand(((MovieDatabase)database).moviesInSystem()));
                else{
                    if(!((MovieDatabase)database).movieExist(parameters.get(2)))
                        connections.send(connectionId, new ERRORCommand("request info failed"));
                    else
                        ((TPCConnections) connections).send(connectionId, new ACKCommand(((MovieDatabase)database).movieInfo()));
                }
            case "return":
                String movie=parameters.get(2);
                if(!logedIn || !((MovieUser)user).isRent(movie) || ((MovieDatabase)database).movieExist(movie))
                    connections.send(connectionId, new ERRORCommand("request return failed"));
                else{
                    Movie movieInfo=((MovieDatabase)database).getMovie(movie);
                    ((TPCConnections) connections).send(connectionId, new ACKCommand("request "+ movie+" "+"success"));
                    ((TPCConnections) connections).broadcast(new BROADCASTCommand("movie "+ movie +
                            movieInfo.getAvailableAmount()+" "+movieInfo.getPrice()));
                }
        }
    }

    private boolean userCanRent(User user, String movie, Database database){
        Movie movieRental=((MovieDatabase)database).getMovie(movie);
        if(movieRental==null)//movie exist?
            return false;
        if(((MovieUser)user).getBalance() < movieRental.getPrice())//the client has enough balance?
            return false;
        if(movieRental.isBanned(((MovieUser)user).getCountry()))
            return false;
        if(((MovieUser)user).isRent(movie))
            return false;
        return true;
    }
}
