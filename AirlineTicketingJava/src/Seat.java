import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Seat {

	String SeatID;
	boolean EconomyFlag;
	boolean PremiumFlag;
	boolean BusinessFlag;
	boolean InsuranceFlag;
	double Price;
	String FlightID;
	Database db;
	
	Seat(String SeatID, boolean EconomyFlag, boolean PremiumFlag, boolean BusinessFlag, String FlightID, boolean InsuranceFlag){
		try {
			db = Database.getDatabaseInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.SeatID = SeatID;
		this.EconomyFlag = EconomyFlag;
		this.PremiumFlag = PremiumFlag;
		this.BusinessFlag = BusinessFlag;
		this.FlightID = FlightID;
		this.InsuranceFlag = InsuranceFlag;
		setPrice();
	}
	
    static public boolean isSeatTaken(String SeatID, String FlightID) {
		Database db;
		boolean taken = false;
    	try {
			db = Database.getDatabaseInstance();
			
	        try {
		        PreparedStatement stmt = null;
		        ResultSet rs = null;
	            String str = "SELECT COUNT(*) AS count FROM SEAT WHERE SeatID = ? AND FlightID = ?";
	            stmt = db.con.prepareStatement(str);
	            stmt.setString(1, SeatID);
	            stmt.setString(2, FlightID);
	            rs = stmt.executeQuery();

	            if (rs.next()) {
	                int count = rs.getInt("count");
	                taken = (count > 0);
	            }
	            rs.close();
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
		} catch (IOException e) {
			e.printStackTrace();
		}
        return taken;
    }
	
    public void insertSeat()
    {
    	if (!isSeatTaken(SeatID, FlightID)) {
	    	try {
	    	    String str = "INSERT INTO SEAT (SeatID, EconomyFlag, PremiumFlag, BusinessFlag, Price, FlightID, InsuranceFlag) " +
	    	            "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	    	    PreparedStatement stmt = db.con.prepareStatement(str);
	    	    stmt.setString(1, SeatID);
	    	    stmt.setBoolean(2, EconomyFlag);
	    	    stmt.setBoolean(3, PremiumFlag);
	    	    stmt.setBoolean(4, BusinessFlag);
	    	    stmt.setDouble(5, Price);
	    	    stmt.setString(6, FlightID);
	    	    stmt.setBoolean(7, InsuranceFlag);
	    	    stmt.executeUpdate();
	    	    stmt.close(); 
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
    	}else {
    		System.out.println("Seat " + SeatID + " is already booked. ");
    	}
    }
    
    public double getFlightPrice(String flightID) {
        double bp = 0.0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT BasePrice FROM FLIGHT WHERE FlightID = ?";
            stmt = db.con.prepareStatement(query);
            stmt.setString(1, flightID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                bp = rs.getDouble("BasePrice");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return bp;
    }
    
    private void setPrice() {
    	Price = getFlightPrice(FlightID);
    	
    	if (PremiumFlag) {
    		Price *= 1.4;
    	}else if(BusinessFlag) {
    		Price *= 3;
    	}
    	
    	if (InsuranceFlag) {
    		Price *= 1.1;
    	}
    }
    
    public static void cancelSeat(Seat seat, Connection con) {
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM SEAT WHERE SeatID = ?  AND FlightID = ?");
            stmt.setString(1, seat.SeatID);
            stmt.setString(2, seat.FlightID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Seat cancelled successfully.");
            } else {
                System.out.println("No matching seat found to cancel.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
