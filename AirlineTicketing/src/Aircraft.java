import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class Aircraft {

	String AircraftID;
	String Type;
	String Company;
	
	Aircraft(String AircraftID, String Type, String Company){
		this.AircraftID = AircraftID;
		this.Type = Type;
		this.Company = Company;
	}
	
    public void insertAircraft(){
    	Database db;
		try {
			db = Database.getDatabaseInstance();
	    	try {
	    	    String str = "INSERT INTO AIRCRAFT (AircraftID, Type, Company) " +
	    	            "VALUES (?, ?, ?)";
	
	    	    PreparedStatement stmt = db.con.prepareStatement(str);
	    	    stmt.setString(1, AircraftID);
	    	    stmt.setString(2, Type);
	    	    stmt.setString(3, Company);
	    	    stmt.executeUpdate();
	    	    stmt.close(); 
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static List<Aircraft> getAircrafts() throws IOException {
        List<Aircraft> aircraftList = new ArrayList<>();
        Database db = Database.getDatabaseInstance();
        
        try {
            PreparedStatement stmt = db.con.prepareStatement("SELECT * FROM AIRCRAFT");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String aircraftID = rs.getString("AircraftID");
                String type = rs.getString("Type");
                String company = rs.getString("Company");

                Aircraft aircraft = new Aircraft(aircraftID, type, company);
                aircraftList.add(aircraft);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aircraftList;
    }
}
