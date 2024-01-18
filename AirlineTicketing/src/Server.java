import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Server {

    Database database;
    String host = "localhost", db = "Airline", pass = "ENSF2023", user = "root";
    int port = 3306;
    Connection con;
    boolean databaseExists;
    
    public Connection connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/?autoReconnect=true&useSSL=false", user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + db + "'");
            databaseExists = rs.next();

            if (!databaseExists) {
                String createDBQuery = "CREATE DATABASE " + db;
                stmt.executeUpdate(createDBQuery);
                System.out.println("Database '" + db + "' created successfully.");
            } 
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + db + "?autoReconnect=true&useSSL=false", user, pass);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Server() throws IOException
    {
        con = connect();
        System.out.println("Connected to " + db + " database.");
    }
    
    
}
