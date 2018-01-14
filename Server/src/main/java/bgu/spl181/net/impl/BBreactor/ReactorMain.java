package bgu.spl181.net.impl.BBreactor;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.impl.CommandEncoderDecoder;
import bgu.spl181.net.impl.MovieDatabase;
import bgu.spl181.net.impl.RentalServiceSection;
import bgu.spl181.net.srv.ConnectionsImpl;
import bgu.spl181.net.srv.Server;

import java.io.Serializable;

public class ReactorMain {
    public static void main(String[] args) {
        Database<Serializable> database = new MovieDatabase(
                "Database/Users.json",
                "Database/Movies.json"
        );

        Server.reactor(
                8,
                7777, //port
                ()-> new RentalServiceSection(database), //protocol factory
                CommandEncoderDecoder::new //message encoder decoder factory
        ).serve();
    }
}
