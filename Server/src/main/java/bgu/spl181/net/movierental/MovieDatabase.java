package bgu.spl181.net.movierental;

import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import com.google.gson.JsonArray;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MovieDatabase extends Database<Command>{
    private JsonArray movies;

    public MovieDatabase(JsonArray users, JsonArray movies) {
        super(users);
        this.movies = movies;
    }

    @Override
    public Command handleRequest(Command massege) {
        throw new NotImplementedException();
    }
}
