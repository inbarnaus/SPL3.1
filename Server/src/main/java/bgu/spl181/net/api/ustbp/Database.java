package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.impl.movierental.MovieUser;
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
            JsonArray jusers = getJsonArray(usersPath, "users");
            JsonElement juser = getUserJson(user, usersGson);
            jusers.add(juser);
            updateJson(usersPath, jusers, "users", usersGson);
        }
    }

    protected void removeUser(User user){
        synchronized (usersLock){
            JsonArray jusers = getJsonArray(usersPath, "users");
            int index = 0;
            for (JsonElement currjuser:jusers
                 ) {

                if(((JsonObject)currjuser).get("username").equals(user.getUsername())){
                    jusers.remove(index);
                    break;
                }
                index++;
            }
            updateJson(usersPath,jusers, "users", usersGson);
        }
    }

    protected void updateUser(User user){
        synchronized (usersLock){
            JsonArray jusers = getJsonArray(usersPath, "users");
            int index = 0;
            for (JsonElement currjuser:jusers
                    ) {

                if(((JsonObject)currjuser).get("username").getAsString().equals(user.getUsername())){
                    jusers.remove(index);
                    break;
                }
                index++;
            }
            jusers.add(getUserJson(user,usersGson));
            updateJson(usersPath,jusers, "users", usersGson);
        }
    }
    protected abstract JsonElement getUserJson(User user, Gson gson);

    /**
     * Get the movie from the database
     * @param username the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(String username){
        User ans = null;
        synchronized (usersLock) {
            JsonArray jusers = getJsonArray(usersPath, "users");
            for (JsonElement currj : jusers) {
                JsonObject currjobject = currj.getAsJsonObject();
                if (currjobject.get("username").getAsString().equals(username)) {
                    ans =  getUserInstance(currjobject);
                }
            }
        }
        return ans;
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
    protected JsonArray getJsonArray(String path, String propname){
        JsonArray ans = null;
        try (JsonReader reader = new JsonReader(new FileReader(path));) {
            JsonParser parser = new JsonParser();
            JsonObject jobj  = parser.parse(reader).getAsJsonObject();
            ans = jobj.getAsJsonArray(propname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }
    protected void updateJson(String path, JsonArray jarray, String propname, Gson gson){
        try (Writer writer = new FileWriter(path)) {
            JsonObject jobj = new JsonObject();
            jobj.add(propname, jarray);
            gson.toJson(jobj, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}