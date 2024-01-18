import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FUser extends User{
	
	FUser(String Username, String Password, String FName, String LName){
		super(Username, Password, FName, LName);
	}
	
    public void insertUser(){
    	Database db;
		try {
			db = Database.getDatabaseInstance();
	    	try {
	    	    String str = "INSERT INTO FATT_USER (Username, Password, FName, LName) " +
	    	            "VALUES (?, ?, ?, ?)";
	
	    	    PreparedStatement stmt = db.con.prepareStatement(str);
	    	    stmt.setString(1, Username);
	    	    stmt.setString(2, Password);
	    	    stmt.setString(3, FName);
	    	    stmt.setString(4, LName);
	    	    stmt.executeUpdate();
	    	    stmt.close(); 
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static boolean checkValidUser(String username, String password) throws IOException {
        Database db = Database.getDatabaseInstance();
        String query = "SELECT COUNT(*) AS count FROM FATT_USER WHERE Username = ? AND Password = ?";
        boolean exists = false;
        
        try (PreparedStatement stmt = db.con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Assuming password is plain text for demonstration purposes
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    exists = (count > 0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}