package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    ArrayList<ClientHandler> connections = new ArrayList<>();
    private static boolean run = true;
    private static ServerSocket serverSocket;
    private static DataInputStream din;
    private static DataOutputStream dout;


    public static void main(String[] args) throws SQLException {
        new Server();
    }

    public Server() {
        //Try connect to the server on an unused port eg 9991. A successful connection will return a socket
        try{
            serverSocket = new ServerSocket(9991);
            serverSocket.setReuseAddress(true);
            while(run){

                Socket client = serverSocket.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected: "
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
                connections.add(clientSock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
