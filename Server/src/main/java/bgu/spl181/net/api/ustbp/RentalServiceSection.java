package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.ustbp.commands.*;
import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieUser;
import bgu.spl181.net.srv.TPCConnections;

import java.util.ArrayList;
import java.util.List;

public class RentalServiceSection extends USTBP {

    private String commandName;

    public RentalServiceSection(Database<String> database) {
        super(database);
        this.commandName="REQUEST";
    }

    @Override
    public void process(String message) {
        String[] commandParts = message.split(" ");
        String commandName = commandParts[0];
        boolean logedIn = connections.isLoggedIn(connectionId);
        User user = ((TPCConnections) connections).idToUser(connectionId);
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        switch (commandParts[1]) {
            case "rent":
                if (!logedIn || parameters.size() < 2)
                    ((TPCConnections)connections).send(connectionId, new ERRORCommand("request rent failed"));
                else {
                    String movie = parameters.get(2);
                    boolean canRent = userCanRent(user, movie, database);
                    if (canRent) {
                        Movie rentMovie=((MovieDatabase) database).rentMovie(movie);
                        if(rentMovie==null)
                            ((TPCConnections)connections).send(connectionId, new ERRORCommand("request rent failed"));
                        else ((TPCConnections) connections).send(connectionId, new ACKCommand("rent "+movie+" success"));
                    }
                    else
                        ((TPCConnections)connections).send(connectionId, new ERRORCommand("request rent failed"));
                }
            case "return":
                String movie=parameters.get(1);
                if(!logedIn || !((MovieUser)user).isRent(movie) || ((MovieDatabase)database).movieExist(movie))
                    ((TPCConnections)connections).send(connectionId, new ERRORCommand("request return failed"));
                else{
                    Movie movieInfo=((MovieDatabase)database).getMovie(movie);
                    ((TPCConnections) connections).send(connectionId, new ACKCommand("request "+ movie+" "+"success"));
                    ((TPCConnections) connections).broadcast(new BROADCASTCommand("movie "+ movie +
                            movieInfo.getAvailableAmount()+" "+movieInfo.getPrice()));
                }
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


            User user = database.checkIfExist(username);
            if (user != null || connections.isLoggedIn(connectionId))
                connections.send(connectionId, new ERRORCommand("registration failed"));
            else
                connections.send(connectionId, new ACKCommand("registration succeeded"));
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

    @Override
    public void registerCommand(String[] commandParts) {
        super.registerCommand(commandParts);
    }
}
