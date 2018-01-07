package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.impl.movierental.Movie;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MovieDatabase extends Database<String>{
    private final String moviesPath;

    public MovieDatabase(String usersPath, String moviesPath) {
        this.moviesPath = moviesPath;
    }

    @Override
    public String handleRequest(String massege) { throw new NotImplementedException(); }

    public Movie rentMovie(String movie){ throw new NotImplementedException(); }

    public boolean movieExist(String movie){ throw new NotImplementedException(); }

    public Movie getMovie(String movie){ throw new NotImplementedException(); }

    public String moviesInSystem(){ throw new NotImplementedException(); }

    public String movieInfo(){ throw new NotImplementedException(); }
}
