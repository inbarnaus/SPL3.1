package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.MovieDatabase;
import bgu.spl181.net.api.ustbp.RentalServiceSection;

import java.io.Serializable;

public class TESTMAIN {
    public static void main(String[] args) {
        Database<Serializable> database = new MovieDatabase(
                "Database/Users.json",
                "Database/Movies.json"
        );
        RentalServiceSection rss = new RentalServiceSection(database);
        rss.process("REGISTER steve mypass country=\"iran\"");
    }
}
