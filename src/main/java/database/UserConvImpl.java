package database;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserConvImpl implements UserConvDao{
    private Statement statement;

    public UserConvImpl(){
        try{
            statement = Database.getInstance().getConnection().createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void createConv(UserConv userConv) {
        try{
            ArrayList<Integer> listId = new ArrayList<>();
            for(int i = 0; i < userConv.getListUsers().size(); i ++){
                String userName = userConv.getListUsers().get(i);
                ResultSet resultSet = statement.executeQuery(
                  "SELECT id FROM users WHERE username = '"+userName+"'"
                );
                resultSet.next();
                listId.add(Integer.parseInt(resultSet.getString("id")));
                //System.out.println("voici le resultset; "+resultSet.getString("id"));
                // listId.add(resultSet);
            }
            listId.add(userConv.getUser_id());

            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < userConv.getListUsers().size(); i++){
                stringBuilder.append(listId.get(i));
                stringBuilder.append(",");
            }
            stringBuilder.append(userConv.getUser_id());

            System.out.println("création conv, voici la listid: "+stringBuilder);

            statement.executeUpdate(
                    "INSERT INTO user_conv (user_id, list_id) " +
                            "VALUES ("+userConv.getUser_id()+", '{"+stringBuilder+"}')"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<UserConv> getUserConv(int id) {
        try{
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM user_conv WHERE "+id+" = ANY(list_id) "
            );
            ArrayList<UserConv> listConv = new ArrayList<>();
            while(resultSet.next()){
                UserConv userconv = new UserConv(Integer.parseInt(resultSet.getString("user_id")));
                String str = resultSet.getString("list_id");
                str = str.replaceAll("\\{","");
                str = str.replaceAll("}","");
                String[] arrOfStr = str.split(",", 0);
                for (String idStr : arrOfStr) {
                    // System.out.println(idStr);
                    Statement stmt = Database.getInstance().getConnection().createStatement();
                    ResultSet resultSet1 = stmt.executeQuery(
                            "SELECT * FROM users WHERE id="+Integer.parseInt(idStr)+""
                    );
                    while(resultSet1.next()){
                        userconv.getListUsers().add(resultSet1.getString("username"));
                        userconv.getListUsersId().add(Integer.parseInt(resultSet1.getString("id")));
                    }
                }
                listConv.add(userconv);
            }
            return listConv;
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
