import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class Crew {
	
	String FlightID;
	String FName;
	String LName;
	String Position;
	
	Crew(String FlightID, String FName, String LName, String Position){
		this.FlightID = FlightID;
		this.FName = FName;
		this.LName = LName;
		this.Position = Position;
	}
	
    public static List<Crew> getCrewList(String flightID) throws IOException {
    	Database db = Database.getDatabaseInstance();
    	List<Crew> crewList = new ArrayList<>();

        try {
            PreparedStatement stmt = db.con.prepareStatement("SELECT * FROM CREW WHERE FlightID = ?");
            stmt.setString(1, flightID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Crew crew = new Crew(rs.getString("FlightID"), rs.getString("FName"), rs.getString("LName"), rs.getString("Position"));
                crewList.add(crew);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crewList;
    }
    
    public static boolean doesCrewExist(String flightID, String fName, String lName, String position) throws IOException {
        boolean recordExists = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Database db = Database.getDatabaseInstance();

        try {
            String query = "SELECT COUNT(*) AS count FROM CREW WHERE FlightID = ? AND FName = ? AND LName = ? AND Position = ?";
            pstmt = db.con.prepareStatement(query);
            pstmt.setString(1, flightID);
            pstmt.setString(2, fName);
            pstmt.setString(3, lName);
            pstmt.setString(4, position);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    recordExists = true;
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return recordExists;
    }

    public void insertCrew(){
    	
    	Database db;
		try {
			db = Database.getDatabaseInstance();
	    	try {
	    	    String str = "INSERT INTO CREW (FlightID, FName, LName, Position) " +
	    	            "VALUES (?, ?, ?, ?)";
	
	    	    PreparedStatement stmt = db.con.prepareStatement(str);
	    	    stmt.setString(1, FlightID);
	    	    stmt.setString(2, FName);
	    	    stmt.setString(3, LName);
	    	    stmt.setString(4, Position);
	    	    stmt.executeUpdate();
	    	    stmt.close(); 
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void deleteCrew(String flightID, String fName, String lName, String position) throws IOException {
        Database db = Database.getDatabaseInstance();
    	try {
            PreparedStatement stmt = db.con.prepareStatement("DELETE FROM CREW WHERE FlightID = ? AND FName = ? AND LName = ? AND Position = ?");
            stmt.setString(1, flightID);
            stmt.setString(2, fName);
            stmt.setString(3, lName);
            stmt.setString(4, position);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Crew member deleted successfully!");
            } else {
                System.out.println("No matching crew member found to delete.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
