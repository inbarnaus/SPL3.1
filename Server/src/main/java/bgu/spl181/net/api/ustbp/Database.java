package bgu.spl181.net.api.ustbp;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

public abstract class Database<T> {
    private final Object usersLock = new Object();
    private final String usersPath;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public Database(String usersPath){
        this.usersPath=usersPath;
    }

    /**
     * Adds user to the database
     * @param user
     */
    public void addUser(User user){
        synchronized (usersLock) {
            try (Writer writer = new FileWriter(usersPath)) {
                gson.toJson(gson.toJsonTree(user), writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the movie from the database
     * @param username the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(String username){
        synchronized (usersLock) {
            try(JsonReader reader = new JsonReader(new FileReader(usersPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jusers = parser.parse(reader).getAsJsonArray();
                for (JsonElement currj : jusers
                        ) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("username").getAsString().equals(username)) {
                        return gson.fromJson(currj, User.class);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get the movie from the database
     * @param user the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(User user) {
        synchronized (usersLock) {
            try(JsonReader reader = new JsonReader(new FileReader(usersPath))) {
                JsonParser parser = new JsonParser();
                JsonArray jusers = parser.parse(reader).getAsJsonArray();
                for (JsonElement currj : jusers
                        ) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("username").getAsString().equals(user.getUsername())) {
                        return gson.fromJson(currj, User.class);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public abstract T handleRequest(T massege);
}
