package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.bidi.Connections;
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
    public void requestCommands(String[] commandParts) {
        String commandName = commandParts[0];
        boolean logedIn = connections.isLoggedIn(connectionId);
        User user = ((TPCConnections) connections).idToUser(connectionId);
        switch (commandParts[1]) {
            case "rent":
                if (!logedIn)
                    connections.send(connectionId, new ERRORCommand("request rent failed"));
                else {
                    String movie = commandParts[2];
                    boolean canRent = userCanRent(user, movie, database);
                    if (canRent) {
                        Movie rentMovie=((MovieDatabase) database).rentMovie(movie);
                        if(rentMovie==null)
                            connections.send(connectionId, new ERRORCommand("request rent failed"));
                        else connections.send(connectionId, new ACKCommand("rent "+movie+" success"));
                    }
                    else
                        connections.send(connectionId, new ERRORCommand("request rent failed"));
                }
            case "return":
                String movie=commandParts[1];
                if(!logedIn || !((MovieUser)user).isRent(movie) || ((MovieDatabase)database).movieExist(movie))
                    connections.send(connectionId, new ERRORCommand("request return failed"));
                else{
                    Movie movieInfo=((MovieDatabase)database).getMovie(movie);
                    connections.send(connectionId, new ACKCommand("request "+ movie+" "+"success"));
                    connections.broadcast(new BROADCASTCommand("movie "+ movie +
                            movieInfo.getAvailableAmount()+" "+movieInfo.getPrice()));
                }
            case "info":
                if(!logedIn)
                    connections.send(connectionId, new ERRORCommand("request info failed"));
                if(commandParts.length==2)
                    connections.send(connectionId, new ACKCommand(((MovieDatabase)database).moviesInSystem()));
                else{
                    if(!((MovieDatabase)database).movieExist(commandParts[2]))
                        connections.send(connectionId, new ERRORCommand("request info failed"));
                    else
                        connections.send(connectionId, new ACKCommand(((MovieDatabase)database).movieInfo()));
                }
            case "balance":
                if(commandParts[2].equals("info")){
                    if(!logedIn)
                        connections.send(connectionId, new ERRORCommand("request balance info failed"));
                    else
                        connections.send(connectionId, new ACKCommand("balance "+ ((MovieUser)user).getBalance()));
                }
                else if(commandParts[2].equals("add")){
                    if(!logedIn || commandParts.length<4)
                        connections.send(connectionId, new ERRORCommand("request balance add failed"));
                    else{
                        Integer amount=Integer.getInteger(commandParts[3]);
                        if(amount<1)
                            connections.send(connectionId, new ERRORCommand("request balance add failed"));
                        else {
                            ((MovieUser)user).addBalance(amount);
                            connections.send(connectionId,
                                    new ACKCommand("balance "+ ((MovieUser)user).getBalance()+" added "+amount));
                        }
                    }
                }
            case "remmovie":
                if(!logedIn && !((MovieUser)user).isAdmin())
                    connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                else {
                    if (!((MovieDatabase) database).movieExist(commandParts[2]))
                        connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                    else {
                        Movie movie1 = ((MovieDatabase) database).getMovie(commandParts[2]);
                        if(movie1.getAvailableAmount()!=movie1.getTotalAmount())
                            connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                        else {
                            if(((MovieDatabase) database).removeMovie(commandParts[2]))
                                connections.send(connectionId, new ACKCommand("removie "+commandParts[2]+ " success"));
                            else
                                connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                        }
                    }
                }
            case "addmovie":
                Movie movie2=((MovieDatabase) database).getMovie(commandParts[2]);
                if(!logedIn && !((MovieUser)user).isAdmin())
                    connections.send(connectionId, new ERRORCommand("request addmovie failed"));
                else if (!((MovieDatabase) database).movieExist(commandParts[2]))
                    connections.send(connectionId, new ERRORCommand("request addmovie failed"));
                else if(!((MovieUser)user).isAdmin() || movie2.getPrice()<1)
                    connections.send(connectionId, new ERRORCommand("request addmovie failed"));
                else{
                    Movie newMovie=new Movie();//TODO add movie
                }


            case "changeprice":
                Movie movie1=((MovieDatabase) database).getMovie(commandParts[2]);
                if(!logedIn || !((MovieDatabase)database).movieExist(commandParts[2]))
                    connections.send(connectionId, new ERRORCommand("request changeprice failed"));
                else if(!((MovieUser)user).isAdmin() || movie1.getPrice()<1)
                    connections.send(connectionId, new ERRORCommand("request changeprice failed"));
                else{
                    movie1.setPrice(commandParts[3]);
                    connections.send(connectionId, new ACKCommand("changeprice "+commandParts[2]+" success"));
                    connections.broadcast(new BROADCASTCommand("movie "+ commandParts[2] +
                            movie1.getAvailableAmount()+" "+movie1.getPrice()));
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

    @Override
    public void process(String message) {
        super.process(message);
    }

    @Override
    public void registerCommand(String[] commandParts) {
        User user1=database.checkIfExist(commandParts[1]);
        if(commandParts.length<4 || user1!=null || connections.isLoggedIn(connectionId))
            connections.send(connectionId, new ERRORCommand("registration failed"));
        else {
            String[] country=commandParts[3].split("\"\"");
            User newUser=new MovieUser(commandParts[1], commandParts[2],country[1], "normal");
            database.addUser(newUser);
            connections.send(connectionId, new ACKCommand("registration succeeded"));
        }
    }
}
