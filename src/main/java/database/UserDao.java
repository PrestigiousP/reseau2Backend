package database;

import main.Request;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public interface UserDao<T> {
    void insertUser(User user);
    List<User> getAllUsers();
    User getUser(String username);
    void updateUser(User user);
    void deleteUser(User user);
    void createConv(ArrayList<Integer> idList);
}
