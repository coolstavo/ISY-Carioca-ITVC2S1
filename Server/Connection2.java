package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Connection2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String hostName = "localhost";

        int portNumber = 7789;

        try (
                Socket echoSocket = new Socket(hostName, portNumber);        // 1st statement

                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);       // 2nd statement

                BufferedReader in = new BufferedReader(                         // 3rd statement
                        new InputStreamReader(echoSocket.getInputStream())
                );

                BufferedReader stdIn = new BufferedReader(
                        new InputStreamReader(System.in)
                );                                                              // 4th statement
        ) {

            System.out.println("Server: " + in.readLine());
            System.out.println("Server: " + in.readLine());

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {             // 5th statement

                out.println(userInput);                                  // 6th statement

                System.out.println("Server: " + in.readLine());
                System.out.println("Server: " + in.readLine());          // 7th statement
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }


    }
}
