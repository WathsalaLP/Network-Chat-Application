//client 2

import java.io.*;
import java.net.*;

public class User4 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 9927;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            // User Authentication
            //Enter user name
            System.out.println(input.readLine());
            output.println(console.readLine());
            //Enter Password
            System.out.println(input.readLine());
            output.println(console.readLine());
            //Results
            System.out.println(input.readLine());

            // Start Chat
            new Thread(new MessageReceiver(input)).start();

            // User Send messages to server
            String userInput;
            while ((userInput = console.readLine()) != null) {
                output.println(userInput);
                //End Chat
                if (userInput.equalsIgnoreCase("/quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("User Error " + e.getMessage());
        }
    }

    private static class MessageReceiver implements Runnable {
        private BufferedReader input;

        public MessageReceiver(BufferedReader input) {
            this.input = input;
        }

        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = input.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Server Connection LOST");
            }
        }
    }
}

