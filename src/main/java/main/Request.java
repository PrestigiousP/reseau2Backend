package main;

public class Request {
    public enum Type {
        CONN, END_CONN, SEND_TO_CLIENT, SEND_TO_GROUP, SEND_TO_ALL, SEND_ID
    }

    private int clientId;
    private int roomId;
    private String content;
    private Type type;

    public Request(Type type, int clientId, int roomId, String content) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.content = content;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
