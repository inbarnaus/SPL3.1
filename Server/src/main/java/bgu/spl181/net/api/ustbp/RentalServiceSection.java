package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.ustbp.commands.*;
import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RentalServiceSection extends USTBP {

    private String commandName;

    public RentalServiceSection(Database<Serializable> database) {
        super(database);
        this.commandName="REQUEST";
    }

    @Override
    public void requestCommands(List<String> commandParts) {
        String commandName = commandParts.get(0);
        boolean logedIn = connections.isLoggedIn(connectionId);
        switch (commandParts.get(1)) {
            case "rent":
                if (!logedIn)
                    connections.send(connectionId, new ERRORCommand("request rent failed"));
                else {
                    String movie = commandParts.get(2);
                    boolean canRent = userCanRent(user, movie, database);
                    if (canRent) {
                        Movie rentMovie=((MovieDatabase) database).rentMovie(movie);
                        if(rentMovie==null)
                            connections.send(connectionId, new ERRORCommand("request rent failed"));
                        else {
                            connections.send(connectionId, new ACKCommand("rent \""+movie+"\" success"));
                            connections.broadcast(new BROADCASTCommand("movie \""+movie+"\" "
                                    +rentMovie.getAvailableAmount()+" "+rentMovie.getPrice()));
                        }
                    }
                    else
                        connections.send(connectionId, new ERRORCommand("request rent failed"));
                }
                break;
            case "return":
                String movie=commandParts.get(2);
                if(!logedIn || !((MovieUser)user).isRent(movie) || !((MovieDatabase)database).movieExist(movie))
                    connections.send(connectionId, new ERRORCommand("request return failed"));
                else{
                    Movie movieInfo=((MovieDatabase)database).getMovie(movie);
                    ((MovieDatabase)database).returnMovie(movieInfo, (MovieUser)user);
                    connections.send(connectionId, new ACKCommand("request \""+ movie+"\" "+"success"));
                    connections.broadcast(new BROADCASTCommand("movie \""+ movie +"\" "+
                            movieInfo.getAvailableAmount()+" "+movieInfo.getPrice()));
                }
                break;
            case "info":
                if(!logedIn)
                    connections.send(connectionId, new ERRORCommand("request info failed"));
                if(commandParts.size()==2)
                    connections.send(connectionId, new ACKCommand("info "+ ((MovieDatabase)database).moviesInSystem()));
                else{
                    String movieName = commandParts.get(2);
                    if(!((MovieDatabase)database).movieExist(movieName))
                        connections.send(connectionId, new ERRORCommand("request info failed"));
                    else
                        connections.send(connectionId, new ACKCommand(((MovieDatabase)database).movieInfo(movieName)));
                }
                break;
            case "balance":
                if(commandParts.get(2).equals("info")){
                    if(!logedIn)
                        connections.send(connectionId, new ERRORCommand("request balance failed"));
                    else
                        connections.send(connectionId, new ACKCommand("balance "+ ((MovieUser)user).getBalance()));
                }
                else if(commandParts.get(2).equals("add")){
                    if(!logedIn || commandParts.size()<4)
                        connections.send(connectionId, new ERRORCommand("request balance add failed"));
                    else{
                        Integer amount=Integer.getInteger(commandParts.get(3));
                        if(amount<1)
                            connections.send(connectionId, new ERRORCommand("request balance add failed"));
                        else {
                            ((MovieUser)user).addBalance(amount);
                            connections.send(connectionId,
                                    new ACKCommand("balance "+ ((MovieUser)user).getBalance()+" added "+amount));
                        }
                    }
                }
                break;
            case "remmovie":
                if(!logedIn && !((MovieUser)user).isAdmin())
                    connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                else {
                    if (!((MovieDatabase) database).movieExist(commandParts.get(2)))
                        connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                    else {
                        Movie movie1 = ((MovieDatabase) database).getMovie(commandParts.get(2));
                        if(movie1.getAvailableAmount()!=movie1.getTotalAmount())
                            connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                        else {
                            if(((MovieDatabase) database).removeMovie(commandParts.get(2)))
                                connections.send(connectionId, new ACKCommand("removie "+commandParts.get(2)+ " success"));
                            else
                                connections.send(connectionId, new ERRORCommand("request remmovie failed"));
                        }
                    }
                }
                break;
            case "addmovie":
                Movie movie2=((MovieDatabase) database).getMovie(commandParts.get(2));
                if(!logedIn && !((MovieUser)user).isAdmin())
                    connections.send(connectionId, new ERRORCommand("request addmovie failed"));
                else if (!((MovieDatabase) database).movieExist(commandParts.get(2)))
                    connections.send(connectionId, new ERRORCommand("request addmovie failed"));
                else if(!((MovieUser)user).isAdmin() || movie2.getPrice()<1)
                    connections.send(connectionId, new ERRORCommand("request addmovie failed"));
                else{
                    List<String> bannedCountries=new ArrayList<>();
                    for(int i=5; i<commandParts.size();i++)
                        bannedCountries.add(commandParts.get(i));
                    int amount= Integer.getInteger(commandParts.get(3));
                    Movie newMovie=new Movie(((MovieDatabase)database).getMovieCounter(),
                            commandParts.get(2), commandParts.get(4),bannedCountries, amount, amount);
                    ((MovieDatabase)database).addMovie(newMovie);
                }
                break;

            case "changeprice":
                Movie movie1=((MovieDatabase) database).getMovie(commandParts.get(2));
                if(!logedIn || !((MovieDatabase)database).movieExist(commandParts.get(2)))
                    connections.send(connectionId, new ERRORCommand("request changeprice failed"));
                else if(!((MovieUser)user).isAdmin() || movie1.getPrice()<1)
                    connections.send(connectionId, new ERRORCommand("request changeprice failed"));
                else{
                    movie1.setPrice(commandParts.get(3));
                    connections.send(connectionId, new ACKCommand("changeprice "+commandParts.get(2)+" success"));
                    connections.broadcast(new BROADCASTCommand("movie "+ commandParts.get(2) +
                            movie1.getAvailableAmount()+" "+movie1.getPrice()));
                }
                break;
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
    public void registerCommand(List<String> commandParts) {
        User user1=database.checkIfExist(commandParts.get(1));
        if(commandParts.size()<4 || user1!=null || connections.isLoggedIn(connectionId))
            connections.send(connectionId, new ERRORCommand("registration failed"));
        else {
            String[] country=commandParts.get(3).split("\"");
            user=new MovieUser(commandParts.get(1), commandParts.get(2),country[1], "normal",0);
            database.addUser(user);
            connections.send(connectionId, new ACKCommand("registration succeeded"));
        }
    }

}
