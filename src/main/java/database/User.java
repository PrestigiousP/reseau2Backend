package database;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String ipAddress;

   // ipAddress = SocketConnection.getSocketConn().getSocket().getLocalAddress().getHostAddress();


    public User(String name){
        this.name = name;
        // ipAddress = SocketConnection.getSocketConn().getSocket().getLocalAddress().getHostAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
