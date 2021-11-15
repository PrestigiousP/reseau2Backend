package database;

import main.Request;

import java.util.ArrayList;

public interface MessageDao {
    void insertMessage(Message m);
    ArrayList<Message> getAllMessages(Request request);
}
