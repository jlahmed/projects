import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Server {

    Database database;
    String host = "localhost", db = "Airline", pass = "ENSF607", user = "root";
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
    	try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter MySQL Username: ");
			user = scanner.next();
			System.out.println("Enter user password: ");
			pass = scanner.next();
			
			System.out.println("Would you like to use default 'Airline' for database name and default SQL Port 3306?\nEnter 1 for yes, 0 to define ");
			if(scanner.nextInt() == 0) {
				System.out.println("Enter desired name for database: ");
				db = scanner.next();
				System.out.println("Enter SQL Port: ");
				port = scanner.nextInt();
			}
		}
        con = connect();
        System.out.println("Connected to " + db + " database.");
    }
    
    
}
