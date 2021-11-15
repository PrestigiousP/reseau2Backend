package database;

import main.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MessageImpl implements MessageDao{
    Statement statement;

    public MessageImpl(){
        try{
            statement = Database.getInstance().getConnection().createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertMessage(Message m) {
        try{
            StringBuilder receivers = new StringBuilder("{");
            for(int i = 0; i < m.getReceiversId().size(); i++){
                receivers.append(m.getReceiversId().get(i));
                if(i != m.getReceiversId().size()-1){
                    receivers.append(",");
                }
            }
            receivers.append("}");
            System.out.println("envois de la liste de receivers: "+receivers);
            statement.executeUpdate(
                    "INSERT INTO message (body, sender_id, receiver_id) " +
                            "VALUES ('"+m.getBody()+"',"+m.getSenderId()+",'"+receivers+"')"
            );
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Message> getAllMessages(Request request) {
        try{
            ArrayList<Message> messageList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM message"
            );

            while (resultSet.next()) {
                // System.out.println("receiver_id list String : " + resultSet.getString("receiver_id"));
                String str = resultSet.getString("receiver_id");
                str = str.replaceAll("\\{","");
                str = str.replaceAll("}","");
                String[] arrOfStr = str.split(",", 0);
                ArrayList<Integer> idList = new ArrayList<>();
                for(String id: arrOfStr){
                    idList.add(Integer.parseInt(id));
                }
                int incr = 0;
                idList.forEach(System.out::println);
                // System.out.println("===============================");
                request.getListUserConvId().forEach(System.out::println);
                for(int i : request.getListUserConvId()){
                    for(int j: idList){
                        if(i == j){
                            incr++;
                        }
                    }
                }
                if(incr == idList.size() && incr == request.getListUserConvId().size()){
                    System.out.println("message appartient à la convo");
                    // Si on entre ici, cela veut dire que le message appartient à la conversation
                    Statement st = Database.getInstance().getConnection().createStatement();
                    ResultSet resultSet1 = st.executeQuery(
                            "SELECT username FROM users WHERE id = "+resultSet.getString("sender_id")+""
                    );
                    resultSet1.next();
                    Message mess = new Message(resultSet.getString("body"),
                            Integer.parseInt(resultSet.getString("sender_id")));
                    mess.setId(Integer.parseInt(resultSet.getString("id")));
                    mess.setSenderName(resultSet1.getString("username"));
                    // PAS BESOIN DE LA LISTE ?
                    messageList.add(mess);
                }
            }
            return messageList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
