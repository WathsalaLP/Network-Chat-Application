# Network Chat Application

A multithreaded client-server chat application built using Java. This project allows multiple clients to connect to a server and communicate via real-time messaging, supporting both group and private chats.

---

## Features
- **Multithreading**: Handles multiple client connections simultaneously.
- **Broadcast Messaging**: Messages can be sent to all connected users.
- **Private Messaging**: Send messages directly to specific users.
- **User Authentication**: Ensures unique usernames for each user.
- **Error Handling**: Robust mechanisms to manage invalid inputs and disconnections.
- **Real-Time Interaction**: Instant message delivery.

---

## Requirements
- Java Development Kit (JDK)
- Any IDE or text editor for Java (e.g., IntelliJ IDEA, Eclipse)

---

## How to Run
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/NetworkChatApp.git
    cd NetworkChatApp
    ```
2. Compile the Java files:
    ```bash
    javac Server.java Client.java
    ```
3. Start the server:
    ```bash
    java Server
    ```
4. Start the client (multiple instances for multiple users):
    ```bash
    java Client
    ```

---

## Code Example: Server Initialization
```java
// Create a ServerSocket listening on port 9927
try (ServerSocket serverSocket = new ServerSocket(9927)) {
    System.out.println("Server is running...");
    while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress());
        new Thread(new ClientHandler(clientSocket)).start();
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

## Code Example: Client Login
```java
// Client login implementation
System.out.print("Enter username: ");
String username = scanner.nextLine();
out.println(username);
String response = in.readLine();
if ("ACCEPTED".equals(response)) {
    System.out.println("You are now connected to the chat server.");
} else {
    System.out.println("Username already taken. Please try again.");
}
```

---

## Future Enhancements
- Adding user accounts and password authentication.
- Improving UI/UX for the client application.
- Extending to a web-based or mobile chat application.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

Feel free to contribute to this project by submitting issues or pull requests!
