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

    /**
     * Removes the movie from the movies data
     * Adds the movie to the user movies
     * @param movie
     * @param user
     * @return
     */
    public Movie rentMovie(String movie, User user){
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
        synchronized (usersLock){
            if (rented != null) {
                ((MovieUser)user).addMovie(rented);
                ((MovieUser)user).decBalance(rented.getPrice());
                updateUser(user);
            }
        }

        return rented;
    }

    /**
     * Adds the movie to the movies data
     * Removes the movie from the user movies
     * @param movie
     * @param user
     * @return
     */
    public void returnMovie(Movie movie, MovieUser user){
        synchronized (moviesLock){
            removeMovie(movie.getName());
            movie.setAvailableAmount(movie.getAvailableAmount()+1);
            addMovie(movie);
        }
        synchronized (usersLock){
            ((MovieUser)user).removeMovie(movie.getName());
            updateUser(user);
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

    /**
     * Gets json element of user
     * @param user
     * @param gson
     * @return
     */
    @Override
    protected JsonElement getUserJson(User user, Gson gson) {

        return gson.toJsonTree(
                new JsonUser(
                        user.getUsername(),
                        user.getIsAdmin(),
                        user.getPassword(),
                        ((MovieUser)user).getCountry(),
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
        private String type;
        private String password;
        private String country;
        private List<JsonMovieOfClient> movies;
        private int balance;

        public JsonUser(String username,String type , String password, String country, List<Movie> movies, int balance) {
            this.username = username;
            this.type=type;
            this.password = password;
            this.country = country;
            this.movies = new ArrayList<>();
            for (Movie currmovie: movies
                 ) {
                this.movies.add(
                        new JsonMovieOfClient(
                                currmovie.getId(),
                                currmovie.getName()
                        )
                );
            }
            this.balance = balance;
        }
    }
    /**
     * Designated only tp parse correctly the json
     */
    private class JsonMovieOfClient{
        private String id;
        private String name;

        public JsonMovieOfClient(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}


