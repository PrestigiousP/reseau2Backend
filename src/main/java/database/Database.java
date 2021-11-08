package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection instance;
    private static Database database;

    private Database(){
        try {
            String url = "jdbc:postgresql://localhost:5432/reseau2tp2";

            // System.out.println("Got it!");
            instance = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static Database getInstance(){
        if(database != null){
            return database;
        }
        synchronized (Database.class){
            if (database == null) {
                database = new Database();
            }
            return database;
        }
    }

    public Connection getConnection(){
        return instance;
    }

    // todo: changer void pour resultset ou le bon type
    public void query(String sql) throws SQLException {
        //todo: s'occuper des commandes sql, ensuite créer des dao et les appelers dans le clienthandler

        // Statement statement = instance.createStatement();

//        Statement st = DBConnection.createStatement();
//        // String sqlTest = "SELECT * FROM USER";
//        ResultSet rs = st.executeQuery("SELECT * FROM users");
//        int i = 1;
//        while (rs.next()) {
//            String test = rs.getString("username");
//            // String test = rs.getString(i);
//            i++;
//            System.out.println(test);
//
//        }

        // st.executeUpdate("INSERT INTO user " + "VALUES ('test', 'test', 'test', 'test')");
    }
}
