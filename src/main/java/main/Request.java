package main;

import database.Message;
import database.User;
import database.UserConv;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Request {
    public enum Type {
        CONN, SEND_TO_CLIENT,
        SEND_ID, CONN_COMPLETE, CREATE_CONV_PERSON, GET_USERS, SEND_USERS, GET_CONV, GET_MESS,
        SEND_MESS, RECEIVE_MESS
    }

    private int clientId;
    private int roomId;
    private String content;
    private Type type;
    private ArrayList<User> listUsers;
    private ArrayList<UserConv> listUserConv;
    private ArrayList<Message> listMessages;
    private ArrayList<Integer> listUserConvId;
    private ArrayList<String> usersNameList;

    public Request(Type type) {
        listMessages = new ArrayList<>();
        listUserConv = new ArrayList<>();
        listUserConvId = new ArrayList<>();
        this.type = type;
    }

    public ArrayList<String> getUsersNameList() {
        return usersNameList;
    }

    public void addUsersNameList(String s){
        this.usersNameList.add(s);
    }

    public ArrayList<Message> getListMessages() {
        return listMessages;
    }

    public void setListMessages(ArrayList<Message> listMessages) {
        this.listMessages = listMessages;
    }

    public ArrayList<UserConv> getListUserConv() {
        return listUserConv;
    }

    public ArrayList<Integer> getListUserConvId(){
        return this.listUserConvId;
    }

    public void setListUserConvId(ArrayList<Integer> listUserConvId) {
        this.listUserConvId = listUserConvId;
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
