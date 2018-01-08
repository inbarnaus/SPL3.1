import java.io.*;
import java.net.*;
 
class LPServer {
    
    public static void main(String[] args) throws IOException
    {
        ServerSocket lpServerSocket = null;
        PrintWriter out = null;
        // Get port
        int port = 3000;
        
        // Listen on port
        try {
            lpServerSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Couldn't listen on port " + port);
            System.exit(1);
        }
        
        System.out.println("Listening...");
        
        // Waiting for a client connection
        Socket lpClientSocket = null;
        try {
            lpClientSocket = lpServerSocket.accept();
            out = new PrintWriter(lpClientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Failed to accept...");
            System.exit(1);
        }
        
        System.out.println("Accepted connection from client!");
        System.out.println("The client is from: " + lpClientSocket.getInetAddress() + ":" + lpClientSocket.getPort());
        
        // Read messages from client
        BufferedReader in = new BufferedReader(new InputStreamReader(lpClientSocket.getInputStream()));
        String msg;
 
        while ((msg = in.readLine()) != null)
        {
            System.out.println("Received from client: " + msg);
            if (msg.equals("bye"))
            {
                System.out.println("Client sent a terminating message");
                break;
            }
            String msg1;
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            while ((msg1 = userIn.readLine())!= null)
            {
                out.println(msg1);
                break;
            }
        }
        
        System.out.println("Client disconnected - bye bye...");
        
        lpServerSocket.close();
        lpClientSocket.close();
        in.close();
        
    }
}