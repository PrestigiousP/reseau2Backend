package database;

import java.util.ArrayList;

public class Message {
    private int id;
    private String body;
    private int senderId;
    private ArrayList<Integer> receiverId;

    public Message(String message, int senderId){
        this.body = message;
        this.senderId = senderId;
        this.receiverId = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public ArrayList<Integer> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(ArrayList<Integer> receiverId) {
        this.receiverId = receiverId;
    }

    public void addReceiverId(int id){
        this.receiverId.add(id);
    }
}
