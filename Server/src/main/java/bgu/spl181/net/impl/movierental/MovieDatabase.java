package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MovieDatabase extends Database<Command>{
    private final String moviesPath;

    public MovieDatabase(String usersPath, String moviesPath) {
        this.moviesPath = moviesPath;
    }

    @Override
    public Command handleRequest(Command massege) { throw new NotImplementedException(); }

    public boolean rentMovie(String movie){ throw new NotImplementedException(); }

    public boolean movieExist(String movie){ throw new NotImplementedException(); }

    public Movie getMovie(String movie){ throw new NotImplementedException(); }

    public String moviesInSystem(){ throw new NotImplementedException(); }

    public String movieInfo(){ throw new NotImplementedException(); }
}
