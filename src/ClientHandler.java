import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

/**
* This class will implement the runnable interface.
*/
public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // This is to keep track of every client.
    private Socket socket;
    private BufferedReader bufferedReader; // for reading data(messages)
    private BufferedWriter bufferedWriter; // for writing data(messages)
    private String userName; // name for each client.



    public  ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // this for client to write message.
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // this for client to read / receive message.
            this.userName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: "+ userName + " has joined the chat!");
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
    * This method is what was override from the Runnable Interface
    * Everything in this method is what is going to be run on a different thread.
    *
    *
    */
    @Override
    public void run() {
      String messageFromClient;

      while (socket.isConnected()){
          try{
              messageFromClient = bufferedReader.readLine(); // this the main reason for running the method on a new thread. so that this line of code doesn't halt the program
              broadcastMessage(messageFromClient);
          }
          catch (IOException e){
              closeEverything(socket, bufferedReader, bufferedWriter);
              break;
          }
      }
    }

    /**
    * This method will enable messages to be sent across
    */
    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler : clientHandlers){
            try{
                if (!clientHandler.userName.equals(userName)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();;
                    clientHandler.bufferedWriter.flush();
                }
            }
            catch (IOException e){
                closeEverything(socket,bufferedReader, bufferedWriter);
            }
        }
    }

    /**
    * This method is responsible for broadcasting a signal that a client has left.
    * And also remove client from the array list.
    */
    public void removeClientHandler(){
        clientHandlers.remove(this);
        System.out.println("SERVER: "+ userName +" has left the chat!" );
    }

    /**This method is responsible for closing all connection*/

    public void  closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if (socket != null){
                socket.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
