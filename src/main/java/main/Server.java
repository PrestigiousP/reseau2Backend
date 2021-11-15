package main;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static HashMap<ClientHandler, Integer> clientThreadList;
    public static ArrayList<ClientHandler> connections;
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
            connections = new ArrayList<>();
            clientThreadList = new HashMap<>();
            serverSocket = new ServerSocket(9991);
            serverSocket.setReuseAddress(true);
            while(run){
                Socket client = serverSocket.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected: "
                        + client.getInetAddress());


                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void userConvCreation(Request response, Socket socket){
        try{
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            for(Integer i: response.getListUserConvId()){
                clientThreadList.forEach((clientHandler, integer) -> {
                    if(i.equals(integer)){
                        Gson gson = new Gson();
                        // System.out.println("envoie une réponse à: "+ i);
                        String json = gson.toJson(response);
                        out.println(json);
                    }
                });
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void msgResponse(Request response, Socket socket){
            for(Integer i: response.getListUserConvId()){
                clientThreadList.forEach((clientHandler, integer) -> {
                    System.out.println("valeur de i: "+ i);
                    System.out.println("valeur de integer: " +integer);
                    if(i.equals(integer)){
                        PrintWriter out = null;
                        try {
                            out = new PrintWriter(
                                    clientHandler.getAcceptedSocket().getOutputStream(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("on envoie le message: "+ response.getListMessages().get(0).getBody());
                        Gson gson = new Gson();
                        String json = gson.toJson(response);
                        out.println(json);
                    }
                });
            }
    }

    public static void broadCastMsg(long id, String usrMsg) throws IOException {
        for(ClientHandler client: connections){
            // todo: checker

        }
    }

    public static void removeConnectedService(ClientHandler clientHandler) {
        // todo : tester
        clientThreadList.forEach((clientThread, integer) -> {
            if(clientHandler == clientThread){
                System.out.println("Le client est déconnecté: ");
                clientThreadList.remove(clientHandler);
            }
        });

    }
}
