import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public  Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }


    /**
    * At line 16, the parameter in the while loop (!serverSocket.isClosed()) ensures that the server runs till the server socket is closed.
    * this method is a blocking method, it halts, if a connection is made.
    */
    // Method for starting the server
    public  void startServer(){
        try{
            while (!serverSocket.isClosed()){
               Socket socket =  serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e){

        }
    }

    //This method will ensure that the server socket is closed if an error occurs.

    public void closeServerSocket(){
        try{
            if (serverSocket != null);
            serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // This method will start the server program.
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
