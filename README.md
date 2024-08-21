# Java Client-Server Chat Application

## Overview
This is a simple Java-based console client-server chat application implemented in Java. It enables multiple clients to connect to a server and engage in real-time messaging through a group chat. The project utilizes Java's socket programming capabilities, threading, and I/O streams to facilitate communication between clients.

## Features
- Multi-client support using threads
- Real-time message broadcasting
- Graceful handling of client connections and disconnections
- Clean resource management for sockets and I/O streams

## Project Structure
- **Client.java**: Handles the client's connection to the server, sending and receiving messages.
- **Server.java**: Manages incoming client connections and broadcasts messages to all connected clients.
- **ClientHandler.java**: Manages communication for each connected client, ensuring that messages are broadcast to the entire chat group.

## How to Run
### Server
1. Run the `Server.java` file to start the server:
   ```bash
   javac Server.java
   java Server
   
### Client
2. Run the `Client.java file` to connect a client to the server:
   ```bash
   javac Client.java
   java Client
3. Enter a username when prompted to join the chat.

### Usage
- After starting the server, multiple clients can connect by running the Client.java program.
- Each client can send messages to the group, which will be broadcast to all other connected clients.
- To exit the chat, the client can close the application.

Server.java and Client.java classes as needed.

### Future Improvements
- Adding private messaging between clients
- Enhancing the user interface (e.g., switching to a GUI instead of console-based chat)

### License
This project is open-source and available under the MIT License.

### Author
Fredric-k
