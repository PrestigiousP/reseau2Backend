package main;

import com.google.gson.Gson;
import database.Database;
import database.User;
import database.UserDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run()
    {
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            // get the outputstream of client
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the inputstream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {

                // Crée un object gson permettant de pouvoir convertir
                // le json en object main.Request
                Gson gson = new Gson();
                Request request = gson.fromJson(line, Request.class);
                // On gère la requête dans la fonction handleRequest
                Request response = handleRequest(request, clientSocket);
                // Le serveur peut renvoyer des réponses au client ici
                if (response != null){
                    String json = gson.toJson(response);
                    System.out.println("envoie de réponse au client: " + json);
                    out.println(json);
                }

                // writing the received message from
                // client
                System.out.printf(
                        " Sent from the client: %s\n",
                        line);
                // out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Request handleRequest(Request request, Socket socket){
        try{
            switch (request.getType()){
                case CONN -> {
                    // crée un objet user avec les infos de la request
                    //
                    User user = new User(request.getContent());
                    // prend l'adresse IP du client ? pas sûrrrrrr à voir
                    String ipAddress = socket.getRemoteSocketAddress().toString();
                    System.out.println("l'adresse ip est: "+ipAddress);
                    user.setAddress(ipAddress);

                    // crée un dao permettant de pouvoir envoyer à la base de données
                    UserDaoImpl userDao = new UserDaoImpl();
                    userDao.insertUser(user);

                    // Renvoyer l'id au client
                    ResultSet resultSet = getClientId(user.getName());

                    int id = 0;
                    String userName = "";

                    // on va chercher la valeur dans la colonne username dans la base de données
                    while(resultSet.next()){
                        userName = resultSet.getString("username");
                        id = Integer.parseInt(resultSet.getString("id"));
                    }

                    return new Request(Request.Type.SEND_ID, id, 0, userName);
                }
                case SEND_TO_CLIENT -> {
                    return null;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getClientId(String userName){
        try{
            Statement statement = Database.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users WHERE username = '"+userName+"';"
            );
            System.out.println("get client id retourne: "+resultSet);
            return resultSet;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
