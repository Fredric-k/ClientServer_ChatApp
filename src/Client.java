import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader; // for reading data(messages)
    private BufferedWriter bufferedWriter; // for writing data(messages)
    private String userName; // name for each client.



    public  Client(Socket socket, String userName){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // this for client to write message.
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // this for client to read / receive message.
            this.userName = userName;
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**This method is responsible for sending message to ClientHandler class*/
    public void sendMessage(){
        try{
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner input = new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend = input.nextLine();
                bufferedWriter.write(userName + ": "+ messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
    * This method is responsible for creating a new thread.
    * And also listen from the server any message that will broadcast
    */
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGpChat;

                while (socket.isConnected()){
                    try{
                        messageFromGpChat = bufferedReader.readLine();
                        System.out.println(messageFromGpChat);
                    }
                    catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    /**This method is responsible for closing all connection*/

    public void  closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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

    /** This method will start the client program.*/
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String userName = input.nextLine();
        Socket socket = new Socket("localhost",1234);
        Client client = new Client(socket,userName);
        client.listenForMessage();
        client.sendMessage();
    }
}
