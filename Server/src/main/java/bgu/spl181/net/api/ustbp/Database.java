package bgu.spl181.net.api.ustbp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Database<T> {
    private JsonArray users;
    private Gson gson = new Gson();
    public Database(JsonArray users){
        this.users=users;
    }

    /**
     * Adds user to the database
     * @param user
     */
    public void addUser(User user){
        users.add(gson.toJsonTree(user));
    }
    /**
     * Get the movie from the database
     * @param username the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(String username){
        synchronized (users) {
            for (JsonElement currj : users
                    ) {
                JsonObject currjobject = currj.getAsJsonObject();
                if (currjobject.get("username").getAsString().equals(username)) {
                    return gson.fromJson(currj, User.class);
                }
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
        synchronized (users) {
            for (JsonElement currj : users
                    ) {
                JsonObject currjobject = currj.getAsJsonObject();
                if (currjobject.get("username").getAsString().equals(user.getUsername())) {
                    return gson.fromJson(currj, User.class);
                }
            }
        }
        return null;
    }
    public abstract T handleRequest(T massege);
}
