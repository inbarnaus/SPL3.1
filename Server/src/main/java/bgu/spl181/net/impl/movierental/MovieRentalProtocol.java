package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.ustbp.Command;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.api.ustbp.USTBP;

public class MovieRentalProtocol extends USTBP{
    public MovieRentalProtocol(Database<Command> database) {
        super(database);
    }
}
