package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.User;
import bgu.spl181.net.impl.movierental.Movie;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class MovieDatabase extends Database<Serializable>{
    private Object moviesLock = new Object();
    private final String moviesPath;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private int movieCounter;

    public MovieDatabase(String usersPath, String moviesPath) {
        super(usersPath);
        this.moviesPath = moviesPath;
        movieCounter=1;
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
                        return gson.fromJson(currj, Movie.class);
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
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                int i=0;
                for (JsonElement currj : jmovies
                        ) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("id").getAsString().equals(movie)) {
                        return true;
                    }
                    i++;
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
                for (JsonElement currj : jmovies
                        ) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("id").getAsString().equals(movie)) {
                        currjobject.addProperty("totalAmount", currjobject.get("totalAmount").getAsInt()-1);
                        return gson.fromJson(currj, Movie.class);
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
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                for (int i = 0; i <jmovies.size() ; i++) {
                    ans+=((JsonObject)jmovies.get(i)).get("id").getAsString();
                    if(i+1!=jmovies.size()){
                        ans+=" ,";
                    }
                }
                return ans;
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

    public int getMovieCounter(){ return movieCounter; }

    public boolean removeMovie(String movie){}
    public boolean addMovie(Movie movie){}
}
