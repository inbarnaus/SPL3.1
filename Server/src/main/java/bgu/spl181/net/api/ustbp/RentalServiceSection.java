package bgu.spl181.net.api.ustbp;

import bgu.spl181.net.api.ustbp.commands.Request;
import bgu.spl181.net.impl.movierental.Movie;
import bgu.spl181.net.impl.movierental.MovieDatabase;

import java.util.HashMap;
import java.util.List;

public class RentalServiceSection extends USTBP {

    public RentalServiceSection(Database<Command> database) {
        super(database);
    }

    @Override
    public void process(Request request) {
        request.execute(database, connections, connectionId);
    }
}
