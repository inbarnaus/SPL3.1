package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.MovieDatabase;
import bgu.spl181.net.srv.Server;
import bgu.spl181.net.srv.TPCConnections;


public class TPCMain {
    public static void main(String[] args) {
        Connections connections = new TPCConnections();
        Database<Command> database = new MovieDatabase(
                "Database/Users.json",
                "Database/Movies.json"
        );

        Server.threadPerClient(
                7777, //port
                ()-> new MovieRentalProtocol(database), //protocol factory
                CommandEncoderDecoder::new, //message encoder decoder factory
                connections
        ).serve();
    }
}
