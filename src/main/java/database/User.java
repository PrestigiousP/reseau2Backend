package database;

public class User {
    private int id;
    private String name;
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
