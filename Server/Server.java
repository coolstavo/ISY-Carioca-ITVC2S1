package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    private volatile static Server INSTANCE;

    private Socket echoSocket;
    private PrintWriter out;
    private BufferedReader in;
    public BufferedReader stdIn;

    //----------------------------------------------SINGLETON----------------------------------------------------------
    private Server() {
        startReceiving();
    }

    public static Server getInstance() {
        if (INSTANCE == null) {
            synchronized (Server.class){
                if (INSTANCE == null) {
                    INSTANCE = new Server();
                }
            }
        }
        return INSTANCE;
    }


    //----------------------------------------------CONNECTION---------------------------------------------------------

    public void connect(String hostName, int portNumber) throws UnknownHostException, IOException {
        echoSocket = new Socket(hostName, portNumber);
        out = new PrintWriter(echoSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    //------------------------------------------------SEND-------------------------------------------------------------

    public void sendCommand(String userInput) throws IOException {
        out.println(userInput);
    }

    //---------------------------------------------LISTENERS-----------------------------------------------------------

    public void startListening() {
        Thread receiverThread = new Thread(() -> {
            try {
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    System.out.println("Server: " + serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiverThread.start();
    }

    private void startReceiving() {
        String hostName = "localhost";
        int portNumber = 7789;

        try {
            connect(hostName, portNumber);
            startListening();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                sendCommand(userInput);
            }

            close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    //----------------------------------------------CLOSE--------------------------------------------------------------

    public void close() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (stdIn != null) stdIn.close();
        if (echoSocket != null) echoSocket.close();
    }

}