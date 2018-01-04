package bgu.spl181.net.movierental;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.ustbp.Database;
import bgu.spl181.net.srv.TPCConnections;


public class TPCMain {
    public static void main(String[] args) {
        Connections connections = new TPCConnections(); //one shared object
        Database database ;
        /*
        Server.threadPerClient(
                7777, //port
                () -> new <>(feed), //protocol factory
                ObjectEncoderDecoder::new //message encoder decoder factory
        ).serve();
        */
    }
}
