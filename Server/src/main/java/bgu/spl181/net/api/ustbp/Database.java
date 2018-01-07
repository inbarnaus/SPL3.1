package bgu.spl181.net.api.ustbp;

import com.google.gson.JsonArray;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Database<T> {
    private JsonArray users;

    public void addUser(User user){
        throw new NotImplementedException();
    }
    /**
     * Get the movie from the database
     * @param username the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(String username){
        throw new NotImplementedException();
    }

    /**
     * Get the movie from the database
     * @param user the requested user
     * @return Movie object. null if not exists
     */
    public User checkIfExist(User user) {
        throw new NotImplementedException();
    }

    public abstract T handleRequest(T massege);
}
