import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FlightController {

    public static void insertFlight(Flight flight){
    	Database db;
		try {
			db = Database.getDatabaseInstance();
	    	try {
	    	    String str = "INSERT INTO FLIGHT (FlightID, Origin, Destination, DepartureDate, DepartureTime, ArrivalDate, ArrivalTime, AircraftID, BasePrice) " +
	    	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	    	    PreparedStatement stmt = db.con.prepareStatement(str);
	    	    stmt.setString(1, flight.FlightID);
	    	    stmt.setString(2, flight.Origin);
	    	    stmt.setString(3, flight.Destination);
	    	    stmt.setObject(4, flight.DepartureDate);
	    	    stmt.setObject(5, flight.DepartureTime);
	    	    stmt.setObject(6, flight.ArrivalDate);
	    	    stmt.setObject(7, flight.ArrivalTime);
	    	    stmt.setString(8, flight.AircraftID);
	    	    stmt.setDouble(9, flight.BasePrice);
	    	    stmt.executeUpdate();
	    	    stmt.close(); 
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static List<Flight> getFlights(String origin, String destination) throws IOException {
        List<Flight> flightList = new ArrayList<>();
        Database db = Database.getDatabaseInstance();
        
        try {
            PreparedStatement stmt = db.con.prepareStatement("SELECT * FROM FLIGHT WHERE Origin = ? AND Destination = ?");
            stmt.setString(1, origin);
            stmt.setString(2, destination);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Flight flight = new Flight(
                        rs.getString("FlightID"),
                        rs.getString("Origin"),
                        rs.getString("Destination"),
                        rs.getDate("DepartureDate").toLocalDate(),
                        rs.getTime("DepartureTime").toLocalTime(),
                        rs.getDate("ArrivalDate").toLocalDate(),
                        rs.getTime("ArrivalTime").toLocalTime(),
                        rs.getString("AircraftID"),
                        rs.getDouble("BasePrice")
                    );
                    flightList.add(flight);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightList;
    }
    
    public static List<Flight> getFlightsAdmin (String inputDate)throws IOException {
   	 List<Flight> flightList = new ArrayList<>();
        Database db = Database.getDatabaseInstance();
        
        try {
            PreparedStatement stmt = db.con.prepareStatement("SELECT * FROM FLIGHT WHERE DepartureDate = ?");
            stmt.setString(1, inputDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Flight flight = new Flight(
                        rs.getString("FlightID"),
                        rs.getString("Origin"),
                        rs.getString("Destination"),
                        rs.getDate("DepartureDate").toLocalDate(),
                        rs.getTime("DepartureTime").toLocalTime(),
                        rs.getDate("ArrivalDate").toLocalDate(),
                        rs.getTime("ArrivalTime").toLocalTime(),
                        rs.getString("AircraftID"),
                        rs.getDouble("BasePrice")
                    );
                    flightList.add(flight);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightList;
   }
    
    public static void deleteFlight(String flightID) throws IOException {
        Database db = Database.getDatabaseInstance();
        
        try {
            PreparedStatement stmt = db.con.prepareStatement("DELETE FROM FLIGHT WHERE FlightID = ?");
            stmt.setString(1, flightID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Flight with FlightID: " + flightID + " deleted successfully.");
            } else {
                System.out.println("No matching FlightID found to delete.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static List<String> getPassengers(String flightID) throws IOException {
        List<String> passengerList = new ArrayList<>();
        Database db = Database.getDatabaseInstance();
        
        try {
            PreparedStatement stmt = db.con.prepareStatement("SELECT FName, LName, BookingID FROM TICKET WHERE FlightID = ?");
            stmt.setString(1, flightID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String passengerInfo = "Full name: " + rs.getString("FName") + " " + rs.getString("LName") + "\tBookingID: " + rs.getString("BookingID");
                passengerList.add(passengerInfo);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengerList;
    }
    
    public static void changeFlightDate(LocalDateTime newDepartureDateTime, LocalDateTime newArrivalDateTime, String flightID) throws IOException {
        Database db = Database.getDatabaseInstance();
        
    	try {
            PreparedStatement stmt = db.con.prepareStatement("UPDATE FLIGHT SET DepartureDate = ?, DepartureTime = ?, ArrivalDate = ?, ArrivalTime = ? WHERE FlightID = ?");
            
	        LocalDate depDate = newDepartureDateTime.toLocalDate();
	        LocalTime depTime = newDepartureDateTime.toLocalTime();
	        LocalDate arrDate = newArrivalDateTime.toLocalDate();
	        LocalTime arrTime = newArrivalDateTime.toLocalTime();

            stmt.setObject(1, depDate);
            stmt.setObject(2, depTime);
            stmt.setObject(3, arrDate);
            stmt.setObject(4, arrTime);
            stmt.setString(5, flightID);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Flight date and time updated successfully!");
            } else {
                System.out.println("No matching flight found to update.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
