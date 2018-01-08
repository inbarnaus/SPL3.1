package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieUser;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

public class MovieDatabase extends Database<Serializable> {
    private Object moviesLock = new Object();
    private final String moviesPath;
    private Gson moviesGson = new GsonBuilder().setPrettyPrinting().create();

    public MovieDatabase(String usersPath, String moviesPath) {
        super(usersPath);
        this.moviesPath = moviesPath;
    }
    public Movie rentMovie(String movie){
        synchronized (moviesLock){
            try(Writer writer = new FileWriter(moviesPath);
                    JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                int i=0;
                for (JsonElement currj : jmovies
                        ) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("id").getAsString().equals(movie)) {
                        if(currjobject.get("totalAmount").getAsInt()==1) {
                            jmovies.remove(i);
                            moviesGson.toJson(jmovies, writer);
                        }
                        else {
                            Movie updated = moviesGson.fromJson(currj, Movie.class);
                            updated.setTotalAmount(updated.getAvailableAmount()-1);
                            JsonElement jupdated = moviesGson.toJsonTree(updated);
                            jmovies.remove(i);
                            jmovies.add(jupdated);
                            moviesGson.toJson(jmovies, writer);

                        }
                        return moviesGson.fromJson(currj, Movie.class);
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
                        return moviesGson.fromJson(currj, Movie.class);
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

    public boolean removeMovie(String movie){
        synchronized (moviesLock) {
            try(JsonReader reader = new JsonReader(new FileReader(moviesPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jmovies = parser.parse(reader).getAsJsonArray();
                for (int i = 0; i <jmovies.size() ; i++) {
                    if (((JsonObject)jmovies.get(i)).get("name").getAsString().equals(movie)) {
                        Writer writer = new FileWriter(moviesPath);
                        jmovies.remove(i);
                        moviesGson.toJson(jmovies, writer);
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
                JsonElement jmovie = moviesGson.toJsonTree(movie, Movie.class);
                jmovies.add(jmovie);
                moviesGson.toJson(jmovies, writer);
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
}
