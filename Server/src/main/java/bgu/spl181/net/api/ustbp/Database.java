package bgu.spl181.net.api.ustbp;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

public abstract class Database<T> {
    protected final Object usersLock = new Object();
    protected final String usersPath;
    protected Gson usersGson = new GsonBuilder().setPrettyPrinting().create();
    public Database(String usersPath){
        this.usersPath=usersPath;
    }

    /**
     * Adds user to the database
     * @param user
     */
    public void addUser(User user){
        synchronized (usersLock) {
            try (JsonReader reader = new JsonReader(new FileReader(usersPath));
                    Writer writer = new FileWriter(usersPath)) {
                JsonParser parser = new JsonParser();
                JsonArray jusers = parser.parse(reader).getAsJsonArray();
                JsonElement juser = usersGson.toJsonTree(getUserInstance(user), getUserClass());
                jusers.add(juser);
                usersGson.toJson(juser, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected abstract Class getUserClass();
    protected abstract User getUserInstance(User user);

    /**
     * Get the movie from the database
     * @param username the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(String username){
        synchronized (usersLock) {
            try(JsonReader reader = new JsonReader(new FileReader(usersPath))) {
                JsonParser parser = new JsonParser();
                JsonObject jobj = parser.parse(reader).getAsJsonObject();
                JsonArray jusers = jobj.getAsJsonArray("users");
                for (JsonElement currj : jusers) {
                    JsonObject currjobject = currj.getAsJsonObject();
                    if (currjobject.get("username").getAsString().equals(username)) {
                        return getUserInstance(currjobject);
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
    protected abstract User getUserInstance(JsonObject juser);

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
                        return usersGson.fromJson(currj, User.class);
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
}