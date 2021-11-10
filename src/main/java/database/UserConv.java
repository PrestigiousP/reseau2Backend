package database;

import java.util.ArrayList;

public class UserConv {
    private int user_id;
    private ArrayList<String> listUsers;

    public UserConv(int id){
        this.user_id = id;
        listUsers = new ArrayList<>();
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

    public void setSingleUser(String userName){
        this.listUsers.add(userName);
    }

    public void setListUsers(ArrayList<String> listUsers) {
        this.listUsers = listUsers;
    }

    public void addUser(String userName){
        this.listUsers.add(userName);
    }
}
