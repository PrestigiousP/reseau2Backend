package main;

import database.User;
import database.UserConv;

import java.util.ArrayList;

public class Request {
    public enum Type {
        CONN, END_CONN, SEND_TO_CLIENT, SEND_TO_GROUP, SEND_TO_ALL,
        SEND_ID, CONN_COMPLETE, CREATE_CONV_PERSON, GET_USERS, SEND_USERS, GET_CONV
    }

    private int clientId;
    private int roomId;
    private String content;
    private Type type;
    private ArrayList<User> listUsers;
    private ArrayList<UserConv> listUserConv;

    public Request(Type type, int clientId, int roomId, String content) {
        this.type = type;
        this.clientId = clientId;
        this.roomId = roomId;
        this.content = content;
    }

    public ArrayList<UserConv> getListUserConv() {
        return listUserConv;
    }

    public void setListUserConv(ArrayList<UserConv> listUserConv) {
        this.listUserConv = listUserConv;
    }

    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        this.listUsers = listUsers;
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
