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
        synchronized (moviesLock){
            try(JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                int i=0;
                for (JsonElement currj : jmovies
                        ) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("id").getAsString().equals(movie)) {
                        if(currjobject.get("totalAmount").getAsInt()==1)
                            jmovies.remove(i);
                        else
                            currjobject.addProperty("totalAmount", currjobject.get("totalAmount").getAsInt()-1);
                        return movieGson.fromJson(currj, Movie.class);
                    }
                    i++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean movieExist(String movie){
        synchronized (moviesLock){
            try(JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonObject jmovies = parser.parse(reader).getAsJsonObject();
                JsonArray jusers = jmovies.getAsJsonArray("movies");
                for (JsonElement currj : jusers) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("name").getAsString().equals(movie))
                        return true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Movie getMovie(String movie){
        synchronized (moviesLock){
            try(JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                int i=0;
                for (JsonElement currj : jmovies) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("id").getAsString().equals(movie)) {
                        currjobject.addProperty("totalAmount", currjobject.get("totalAmount").getAsInt()-1);
                        return movieGson.fromJson(currj, Movie.class);
                    }
                    i++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String moviesInSystem(){
        String ans = "";
        synchronized (moviesLock){
            try(JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonObject jmovies = parser.parse(reader).getAsJsonObject();
                JsonArray jusers = jmovies.getAsJsonArray("movies");
                for (JsonElement currj : jusers) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    ans+="\"";
                    ans+=currjobject.get("name").getAsString();
                    ans+="\" ";
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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


    public boolean removeMovie(String movie){
        synchronized (moviesLock) {
            try(JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                for (int i = 0; i <jmovies.size() ; i++) {
                    if (((JsonObject)jmovies.get(i)).get("name").getAsString().equals(movie)) {
                        Writer writer = new FileWriter(moviesPath);
                        jmovies.remove(i);
                        movieGson.toJson(jmovies, writer);
                        return true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean addMovie(Movie movie){
        synchronized (usersLock) {
            try (JsonReader reader = new JsonReader(new FileReader(moviesPath));
                 Writer writer = new FileWriter(moviesPath)) {
                JsonParser parser = new JsonParser();
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                JsonElement jmovie = movieGson.toJsonTree(movie, Movie.class);
                jmovies.add(jmovie);
                movieGson.toJson(jmovies, writer);
                movieCounter++;
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected Class getUserClass() {
        return MovieUser.class;
    }

    @Override
    protected User getUserInstance(User user) {
        return (MovieUser)user;
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
}
