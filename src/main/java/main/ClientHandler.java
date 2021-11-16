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
            // Ajoute le thread à la liste de threads
            Server.connections.add(this);

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
                    if(response.getType() == Request.Type.CONN_COMPLETE){
                        Server.clientThreadList.put(this, response.getClientId());
                    }
                    String json = gson.toJson(response);
                    System.out.println("envoie de réponse au client: " + json);
                    out.println(json);
                }

                // out.println(line);
            }
            Server.removeConnectedService(this);

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
                        Request request1 = new Request(Request.Type.CONN_COMPLETE);
                        request1.setClientId(userTest.getId());
                        request1.setContent(userTest.getName());
                        return request1;
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
                    Request request1 = new Request(Request.Type.SEND_ID);
                    request1.setClientId(id);
                    request1.setContent(userName);
                    return request1;
                }
                case SEND_TO_CLIENT -> {
                    return null;
                }
                case GET_USERS -> {
                    UserDaoImpl userDao = new UserDaoImpl();
                    List<User> listUsers = userDao.getAllUsers();
                    Request response = new Request(Request.Type.SEND_USERS);
                    response.setClientId(request.getClientId());
                    response.setListUsers((ArrayList<User>) listUsers);
                    return response;
                }
                case CREATE_CONV_PERSON -> {
                    UserConv userConv = new UserConv(request.getClientId());
                    request.getUsersNameList().forEach(userName -> {
                        userConv.addSingleUser(userName);
                    });
                    // System.out.println("voici la liste id des users: " + request.getContent());
                    UserConvImpl userConvImpl = new UserConvImpl();
                    ArrayList<Integer> userConvIdList = userConvImpl.createConv(userConv);

                    // Création d'une liste de conversation
                    // (ne va contenir qu'une seule conversation)
                    ArrayList<UserConv> userConvs = new ArrayList<>();
                    userConvs.add(userConv);

                    userConvIdList.forEach(userConvId -> {
                        System.out.println("testttt "+ userConvId);
                    });

                    Request response = new Request(Request.Type.CREATE_CONV_PERSON);
                    response.setClientId(request.getClientId());
                    response.setListUserConv(userConvs);
                    response.setListUserConvId(userConvIdList);
                    Server.userConvCreation(response);
                    // return response;
                }
                case GET_CONV -> {
                    UserConvImpl user = new UserConvImpl();
                    ArrayList<UserConv> userconvList = user.getUserConv(request.getClientId());
                    Request response = new Request(Request.Type.GET_CONV);
                    response.setClientId(request.getClientId());
                    response.setListUserConv(userconvList);
                    return response;
                }
                case GET_MESS -> {
                    MessageImpl message = new MessageImpl();
                    ArrayList<Message> messageList = message.getAllMessages(request);
                    Request request1 = new Request(Request.Type.GET_MESS);
                    request1.setListMessages(messageList);
                    return request1;
                }
                case SEND_MESS -> {
                    Message message = new Message(request.getContent(), request.getClientId());
                    message.setReceiverId(request.getListUserConvId());
                    MessageImpl messageImpl = new MessageImpl();
                    messageImpl.insertMessage(message);

                    // Renvoie le message à tous les personnes qui font partis de la conversation
                    Request response = new Request(Request.Type.RECEIVE_MESS);
                    response.setClientId(request.getClientId());
                    response.setListUserConvId(request.getListUserConvId());
                    String clientName = getClientName(request.getClientId());
                    message.setSenderName(clientName);
                    response.getListMessages().add(message);
//                    response.setContent(request.getContent());
                    Server.msgResponse(response);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getClientName(int id){
        try{
            Statement statement = Database.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
              "SELECT username FROM users WHERE id = "+id+""
            );
            resultSet.next();
            String clientName = resultSet.getString("username");
            System.out.println("nom de la personne qui envoit le message: " +clientName);
            return clientName;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getClientId(String userName){
        try{
            Statement statement = Database.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users WHERE username = '"+userName+"'"
            );
            System.out.println("get client id retourne: "+resultSet);
            return resultSet;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Socket getAcceptedSocket() {
        return clientSocket;
    }
}
