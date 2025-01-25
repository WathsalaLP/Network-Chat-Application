import java.io.*;
import java.net.*;
import java.util.*;

public class NetworkChatServer {
    private static final int PORT = 9927;
    private static HashMap<String, PrintWriter> clients = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Network Chat Server Start...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Network Chat Server Error " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);

                // User Authentication
                output.println("Enter Your Username: ");
                username = input.readLine();
                output.println("Enter Your Secret Password: ");
                String password = input.readLine();

                synchronized (clients) {
                    if (clients.containsKey(username)) {
                        output.println("Already used this Username. Plz Enter New Username");
                        socket.close();
                        return;
                    }
                    clients.put(username, output);
                }

                output.println("Welcome to the Network Chat");
                broadcast( "Now"+ username + " joined to chat");

                // Handle Client Messages
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.startsWith("/exit")) {
                        break;
                    } else if (message.startsWith("/pvtm")) {
                        privateMessage(message);
                    } else {
                        broadcast(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println("User problem: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clients) {
                    clients.remove(username);
                    broadcast(username + " leave from the chat");
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clients) {
                for (PrintWriter writer : clients.values()) {
                    writer.println(message);
                }
            }
        }

        private void privateMessage(String message) {
            String[] tokens = message.split(" ", 3);
            if (tokens.length < 3) {
                output.println("Private message format is not define. Use /pvtm <username> <message>");
                return;
            }
            String targetUser = tokens[1];
            String privateMsg = tokens[2];

            synchronized (clients) {
                PrintWriter target = clients.get(targetUser);
                if (target != null) {
                    target.println("[Private] " + username + ": " + privateMsg);
                } else {
                    output.println("User is not found");
                }
            }
        }
    }
}
