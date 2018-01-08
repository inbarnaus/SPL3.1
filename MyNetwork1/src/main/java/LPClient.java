import java.io.*;
import java.net.*;
 
public class LPClient {
    
    public static void main(String[] args) throws IOException
    {
        Socket lpSocket = null; // the connection socket
        PrintWriter out = null;
 
        // Get host and port
        String host = "localhost";
        int port = 3000;
        
        System.out.println("Connecting to " + host + ":" + port);
        
        // Trying to connect to a socket and initialize an output stream
        try {
            lpSocket = new Socket(host, port); // host and port
              out = new PrintWriter(lpSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
              System.out.println("Unknown host: " + host);
              System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O to " + host + " connection");
            System.exit(1);
        }
        
        System.out.println("Connected to server!");

        String msg;
        String msg1;
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(lpSocket.getInputStream()));
        while ((msg = userIn.readLine())!= null)
        {
            out.println(msg);
            if(msg.indexOf("bye") >= 0){
                  break;
            }


            while((msg1 = in.readLine())!= null){
                System.out.println("Received from server: " + msg1);
                break;
            }
        }
        
        System.out.println("Exiting...");
        
        // Close all I/O
        out.close();
        userIn.close();
        lpSocket.close();
    }
}