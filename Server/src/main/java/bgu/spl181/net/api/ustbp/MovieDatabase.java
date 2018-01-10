package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.User;
import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieUser;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import netscape.javascript.JSObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase extends Database<Serializable>{
    private Object moviesLock = new Object();
    private final String moviesPath;
    private Gson movieGson = new GsonBuilder().setPrettyPrinting().create();
    private int movieCounter;

    public MovieDatabase(String usersPath, String moviesPath) {
        super(usersPath);
        this.moviesPath = moviesPath;
        this.movieCounter=1;

    }


    public Movie rentMovie(String movie){
        Movie rented = null;
        synchronized (moviesLock){
            if(!((rented=getMovie(movie))==null)){
                if(!(rented.getAvailableAmount()==0)){
                    removeMovie(movie);
                    rented.setAvailableAmount(rented.getAvailableAmount()-1);
                    addMovie(rented);
                }else{
                    rented=null;
                }
            }
        }
        return rented;
    }

    public void returnMovie(Movie movie){
        synchronized (moviesLock){
            removeMovie(movie.getName());
            movie.setAvailableAmount(movie.getAvailableAmount()+1);
            addMovie(movie);
        }
    }

    public boolean movieExist(String movie){
        synchronized (moviesLock){
            return getMovie(movie)!=null;
        }
    }

    public String moviesInSystem(){
        String ans = "";
        synchronized (moviesLock){
            JsonArray jmovies = getJsonArray(moviesPath, "movies");
            for (JsonElement currj : jmovies) {
                JsonObject currjobject = currj.getAsJsonObject();
                ans+="\"";
                ans+=currjobject.get("name").getAsString();
                ans+="\" ";
            }
        }
        return ans;
    }

    public Movie getMovie(String movie){
        Movie  ans= null;
        synchronized (moviesLock){
            int i=0;
            JsonArray jmovies = getJsonArray(moviesPath, "movies");
            for (JsonElement currj : jmovies) {
                JsonObject currjobject = currj.getAsJsonObject();
                if (currjobject.get("name").getAsString().equals(movie)) {
                    ans =  movieGson.fromJson(currj, Movie.class);
                }
                i++;
            }
        }
        return ans;
    }

    public String movieInfo(String movie){
        Movie temp = getMovie(movie);
        if (temp == null) {
            return null;
        }
        return temp.toString();
    }

    public int getMovieCounter() {
        return movieCounter;
    }


    public boolean removeMovie(String movie) {
        boolean ans = false;
        synchronized (moviesLock) {
            JsonArray jmovies = getJsonArray(moviesPath, "movies");
            for (int i = 0; i < jmovies.size(); i++) {
                if (((JsonObject) jmovies.get(i)).get("name").getAsString().equals(movie)) {
                    jmovies.remove(i);
                    movieCounter--;//TODO ASK INBAR
                    updateJson(moviesPath, jmovies, "movies", movieGson);
                    ans =  true;
                }
            }
        }
        return ans;
    }

    public boolean addMovie(Movie movie){
        synchronized (usersLock) {
            JsonArray jmovies = getJsonArray(moviesPath, "movies");
            JsonElement jmovie = movieGson.toJsonTree(movie, Movie.class);
            jmovies.add(jmovie);
            movieCounter++;//TODO ASK INBAR
            updateJson(moviesPath, jmovies, "movies", movieGson);
            return true;
        }
    }


    @Override
    protected User getUserInstance(JsonObject juser) {
        MovieUser user = new MovieUser(
                juser.get("username").getAsString(),
                juser.get("password").getAsString(),
                juser.get("country").getAsString(),
                juser.get("type").getAsString(),
                juser.get("balance").getAsInt()
        );
        JsonArray jmovies =juser.get("movies").getAsJsonArray();
        List<Movie> movies = new ArrayList<>();
        for (JsonElement currmovie: jmovies
                ) {
            movies.add(usersGson.fromJson(currmovie, Movie.class));
        }
        user.setMovies(movies);
        return user;
    }

    @Override
    protected JsonElement getUserJson(User user, Gson gson) {

        return gson.toJsonTree(
                new JsonUser(
                        user.getUsername(),
                        user.getPassword(),
                        user.getIsAdmin(),
                        ((MovieUser)user).getMovies(),
                        ((MovieUser)user).getBalance()
                ),
                JsonUser.class);
    }

    /**
     * Designated only tp parse correctly the json
     */
    private class JsonUser{
        private String username;
        private String password;
        private String country;
        private List<Movie> movies;
        private int balance;

        public JsonUser(String username, String password, String country, List<Movie> movies, int balance) {
            this.username = username;
            this.password = password;
            this.country = country;
            this.movies = movies;
            this.balance = balance;
        }
    }
}


