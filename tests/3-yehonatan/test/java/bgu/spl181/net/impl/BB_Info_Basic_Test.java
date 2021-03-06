package bgu.spl181.net.impl;

import org.junit.Test;

import java.io.IOException;

public class BB_Info_Basic_Test extends ReactorServerClientTestTemplate{
    @Test(timeout = 50000)
    public void BB_Info_Basic_Test() throws IOException {
        String testPath = "./src/test/resources/Basic_Info_Test/";

        A.prepareDatabase(testPath);

        server = A.initiateServer("Reactor",A.serverPort);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread[] clients = A.initiateClients(1,A.serverIp,A.serverPort,
                testPath,null,null);

        A.waitForClients(clients);

        A.compareDatabases(testPath);
    }
}
