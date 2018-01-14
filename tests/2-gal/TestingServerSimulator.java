package bgu.spl181.net.srv;                                 // Change to the name of your package

/**
 *                          TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 *
 *                          REMEMBER TO DELETE THIS FILE BEFORE SUBMISSION!!!
 *
 *                          TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 */

import bgu.spl181.net.impl.Bidi.BidiMessageProtocol;        // Change to the name of your protocol
import bgu.spl181.net.impl.Blockbuster.TestingConnections;  // Change to the name of your modified Connections class

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class TestingServerSimulator<T> {
    private final Supplier<BidiMessageProtocol<T>> protocolFactory;
    private ArrayList<ArrayList<String>> clients;
    private static TestingConnections connections = new TestingConnections();

    public TestingServerSimulator(
            ArrayList<ArrayList<String>> clients,
            Supplier<BidiMessageProtocol<T>> protocolFactory) {

        this.protocolFactory = protocolFactory;
        this.clients = clients;
    }

    public void serve() {
        ExecutorService clientsThreads = Executors.newFixedThreadPool(clients.size());

        for(int i=0; i<clients.size(); i++) {
            final int connectionId = i;
            connections.addConnection(connectionId);
            clientsThreads.submit(() -> {
                BidiMessageProtocol protocol = protocolFactory.get();
                protocol.start(connectionId, connections);
                for (String input : clients.get(connectionId)) {
                    System.out.println("<< Received msg from client #" + connectionId + ": " + input);
                    protocol.process((T) input);
                    try { Thread.sleep(500); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            });
        }

        try {
            clientsThreads.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientsThreads.shutdown();
    }

}
