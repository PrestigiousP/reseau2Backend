package database;

import main.Request;

import java.net.Socket;
import java.util.List;

public interface UserDao<T> {
    void insertUser(User user);
    List<User> getAllUsers();
    User getUser(int id);
    void updateUser(User user);
    void deleteUser(User user);
}
