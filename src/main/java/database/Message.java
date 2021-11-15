package database;

import java.util.ArrayList;

public class Message {
    private int id;
    private String body;
    private int senderId;
    private ArrayList<Integer> receiverId;
    private String senderName;

    public Message(String message, int senderId){
        this.body = message;
        this.senderId = senderId;
        this.receiverId = new ArrayList<>();
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public ArrayList<Integer> getReceiversId() {
        return receiverId;
    }

    public void setReceiverId(ArrayList<Integer> receiverId) {
        this.receiverId = receiverId;
    }

    public void addReceiverId(int id){
        this.receiverId.add(id);
    }
}
