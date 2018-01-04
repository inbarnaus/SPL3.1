package bgu.spl181.net.movierental;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Command;

public class MovieRentalProtocol implements BidiMessagingProtocol<Command> {
    private T arg;
    @Override
    public void start(int connectionId, Connections<Command> connections) {
        this.connections=connections;
        this.connectionId=connectionId;
    }

    @Override
    public void process(Command message) {
        message.execute(connectionId, connections);
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
