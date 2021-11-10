package database;

import java.util.List;

public interface MessageDao {
    void insertMessage(Message m);
    List<Message> getAllMessages(int senderId);
    // User getMessage(String username);
//    void updateUser(User user);
//    void deleteUser(User user);
}
