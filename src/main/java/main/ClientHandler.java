package main;

import com.google.gson.Gson;
import database.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                    //todo: vérifier si le client existe déjà
                    UserDaoImpl userDaoImpl = new UserDaoImpl();
                    User userTest = userDaoImpl.getUser(request.getContent());
                    if(userTest != null){
                        System.out.println("Le user existe déjà");
                        return new Request(Request.Type.CONN_COMPLETE, userTest.getId(), 0, userTest.getName());
                    }

                    // crée un objet user avec les infos de la request
                    User user = new User(request.getContent());
                    // prend l'adresse IP du client ? pas sûrrrrrr à voir
                    //todo: vérifier que l'adresse prise est bien celle du client
                    String ipAddress = socket.getRemoteSocketAddress().toString();
                    System.out.println("l'adresse ip est: "+ipAddress);
                    user.setIpAddress(ipAddress);

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
                case GET_USERS -> {
                    UserDaoImpl userDao = new UserDaoImpl();
                    List<User> listUsers = userDao.getAllUsers();
                    Request response = new Request(Request.Type.SEND_USERS, request.getClientId(), 0, "");
                    response.setListUsers((ArrayList<User>) listUsers);
                    return response;
                }
                case CREATE_CONV_PERSON -> {
                    UserConv userConv = new UserConv(request.getClientId());
                    userConv.setSingleUser(request.getContent());
                    System.out.println("voici la liste id des users: " + request.getContent());
                    UserConvImpl userConvImpl = new UserConvImpl();
                    userConvImpl.createConv(userConv);
                }
                case GET_CONV -> {
                    UserConvImpl user = new UserConvImpl();
                    ArrayList<UserConv> userconvList = user.getUserConv(request.getClientId());
//                    for(UserConv userConv: listConv){
//                        System.out.println("userconv here" +userConv.getListUsers().size());
//                        userConv.getListUsers().forEach(u -> {
//                            System.out.println("USER IN CONV: "+ u);
//                        });
//                    }
                    Request response = new Request(Request.Type.GET_CONV, request.getClientId(), 0, "");
                    response.setListUserConv(userconvList);
                    return response;
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
