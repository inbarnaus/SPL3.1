package bgu.spl181.net.impl.Assignment3;                // Change to the name of your package

import bgu.spl181.net.impl.Bidi.BidiMessagingProtocol;  // Change to the name of your protocol
import bgu.spl181.net.srv.TestingServerSimulator;

/**
 *                          TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 *
 *                          REMEMBER TO DELETE THIS FILE BEFORE SUBMISSION!!!
 *
 *                          TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 */

import java.io.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class MainTester {

    private static MoviesUsersDataBase database = new MoviesUsersDataBase();  // Change the name of your DB's class

    public static void main(String[] args) {

        path = "C:\\Example\\path\\to\\test\\files\\";  // Fill your path here

        ArrayList<ArrayList<String>> allClients = new ArrayList<>();
        int fileIndex = 0;
        while(new File(path + "testClient" + fileIndex + ".txt").exists()) {
            ArrayList<String> singleClient = new ArrayList<>();
            try {
                FileReader reader = new FileReader(path + "testClient" + fileIndex + ".txt");
                BufferedReader in = new BufferedReader(reader);
                String currentLine;
                while ((currentLine = in.readLine()) != null) {
                    singleClient.add(currentLine);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            allClients.add(singleClient);
            fileIndex ++;
        }

        Supplier<BidiMessageProtocol<String>> protocolFactory = () -> new BidiMessageProtocol(database);
        TestingServerSimulator testServer = new TestingServerSimulator(allClients, protocolFactory);
        testServer.serve();

    }

}
