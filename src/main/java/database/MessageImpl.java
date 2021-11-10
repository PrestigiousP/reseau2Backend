package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            for(int i = 0; i < m.getReceiverId().size(); i++){
                receivers.append(m.getReceiverId().get(i));
                if(i != m.getReceiverId().size()){
                    receivers.append(",");
                }
            }
            receivers.append("}");
            System.out.println("envois de la liste de receivers: "+receivers);
            statement.executeUpdate(
                    "INSERT INTO messages (body, sender_id, receiver_id) " +
                            "VALUES ('"+m.getBody()+"',"+m.getSenderId()+",'"+receivers+"')"
            );
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Message> getAllMessages(int senderId) {
        return null;
    }
}
