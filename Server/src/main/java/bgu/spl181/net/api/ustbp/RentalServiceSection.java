package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieDatabase;

import java.util.HashMap;
import java.util.List;

public class RentalServiceSection extends USTBP {
    private MovieDatabase movies;

    public RentalServiceSection(Database<Command> database, MovieDatabase movies) {
        super(database);
        this.movies=movies;
    }

    public boolean canRent(List<String> datablock, String movie){
        List<String> bannedCountries= movies.getMovie(movie).getBannedCountries();
        for(String country: datablock)
            if(bannedCountries.contains(country))
                return false;
        return true;
    }

    public boolean movieExist(String movie){
        return movies.movieExist(movie);
    }
}
