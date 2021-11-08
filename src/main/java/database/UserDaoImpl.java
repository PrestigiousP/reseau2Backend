package database;


import main.Request;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoImpl implements UserDao<User> {
    Statement statement;

    public UserDaoImpl(){
        try{
            //todo: finir la fonctionalité
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
                            "VALUES ('"+user.getName()+"', '"+user.getAddress()+"');"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        //todo: finir la fonctionalité
        return null;
    }

    @Override
    public User getUser(int id) {
        //todo: finir la fonctionalité
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
}
