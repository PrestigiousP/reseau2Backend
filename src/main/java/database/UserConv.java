package database;

import java.util.ArrayList;

public class UserConv {
    private int user_id;
    private ArrayList<String> listUsers;
    private ArrayList<Integer> listUsersId;

    public UserConv(int id){
        this.user_id = id;
        listUsers = new ArrayList<>();
        listUsersId = new ArrayList<>();
    }

    public ArrayList<Integer> getListUsersId() {
        return listUsersId;
    }

    public void setListUsersId(ArrayList<Integer> listUsersId) {
        this.listUsersId = listUsersId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ArrayList<String> getListUsers() {
        return listUsers;
    }

    public void addSingleUser(String userName){
        this.listUsers.add(userName);
    }

    public void setListUsers(ArrayList<String> listUsers) {
        this.listUsers = listUsers;
    }

    public void addUser(String userName){
        this.listUsers.add(userName);
    }
}
