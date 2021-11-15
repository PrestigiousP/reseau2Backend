package database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao<User> {
    Statement statement;

    public UserDaoImpl(){
        try{
            statement = Database.getInstance().getConnection().createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void insertUser(User user) {
        try{
            statement.executeUpdate(
                    "INSERT INTO users (username, address) " +
                            "VALUES ('"+user.getName()+"', '"+user.getIpAddress()+"');"
            );
            ResultSet resultSet = statement.executeQuery(
                "SELECT id FROM users WHERE username='"+user.getName()+"'"
            );
            resultSet.next();
            statement.executeUpdate(
              "UPDATE user_conv " +
                      "SET list_id = array_append(list_id, "+resultSet.getString("id")+"::smallint) " +
                      "WHERE user_id = -1"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        //todo: finir la fonctionalité
        try{
            ArrayList<User> listUsers = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users"
            );
            while(resultSet.next()){
                String userName1 = resultSet.getString("username");
                int id = Integer.parseInt(resultSet.getString("id"));
                String addr = resultSet.getString("address");
                User user = new User("");
                user.setIpAddress(addr);
                user.setId(id);
                user.setName(userName1);
                listUsers.add(user);
            }
            System.out.println("Voici la liste de users: ");
            listUsers.forEach(user -> System.out.println("nom: " + user.getName()+ "id: "+ user.getId()));
            return listUsers;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        //todo: finir la fonctionalité
        try{
            ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM users WHERE username='"+username+"'"
            );
            // il y a des données de trouvé
            if(resultSet.next()){
                User user = new User("");
                System.out.println("on a trouvé quelque chose dans la base de données");
                String userName1 = null;
                int id = 0;
                String addr = null;

                userName1 = resultSet.getString("username");
                id = Integer.parseInt(resultSet.getString("id"));
                addr = resultSet.getString("address");

                user.setIpAddress(addr);
                user.setId(id);
                user.setName(userName1);
                System.out.println("user: "+user.getName()+" "+user.getId());
                return user;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User student) {
        //todo: finir la fonctionalité

    }

    @Override
    public void deleteUser(User student) {
        //todo: finir la fonctionalité

    }

    @Override
    public void createConv(ArrayList<Integer> idList) {
        try{
            statement.executeUpdate(
                    "INSERT INTO users (conversation)"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
