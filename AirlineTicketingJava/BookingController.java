import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BookingController {

    public static void insertTicket(Ticket ticket) throws IOException{
    	Database db = Database.getDatabaseInstance();
    	try {
    	    String str = "INSERT INTO TICKET(BookingID, FlightID, FName, LName, PassengerID, CardN, Email, SeatID) " +
    	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    	    PreparedStatement stmt = db.con.prepareStatement(str);
    	    stmt.setString(1, ticket.BookingID);
    	    stmt.setString(2, ticket.FlightID);
    	    stmt.setString(3, ticket.FName);
    	    stmt.setString(4, ticket.LName);
    	    stmt.setString(5, ticket.PassengerID);
    	    stmt.setString(6, ticket.CardN);
    	    stmt.setString(7, ticket.Email);
    	    stmt.setString(8, ticket.seat.SeatID);
    	    stmt.executeUpdate();
    	    stmt.close(); 
    	    ticket.seat.insertSeat();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
    
    public static void insertTicket(Ticket ticket, RegUser user) throws IOException{
    	Database db = Database.getDatabaseInstance();
    	try {
    		String str = "INSERT INTO TICKET(BookingID, FlightID, FName, LName, PassengerID, CardN, Email, SeatID, MemberN) " +
    	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    	    PreparedStatement stmt = db.con.prepareStatement(str);
    	    stmt.setString(1, ticket.BookingID);
    	    stmt.setString(2, ticket.FlightID);
    	    stmt.setString(3, ticket.FName);
    	    stmt.setString(4, ticket.LName);
    	    stmt.setString(5, ticket.PassengerID);
    	    stmt.setString(6, ticket.CardN);
    	    stmt.setString(7, ticket.Email);
    	    stmt.setString(8, ticket.seat.SeatID);
    	    stmt.setInt(9, user.getMemberN());
    	    stmt.executeUpdate();
    	    stmt.close(); 
    	    
    	    ticket.seat.insertSeat();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
    
    public static void cancelTicket(String bookingID) throws IOException {
    	Database db = Database.getDatabaseInstance();
    	Connection con = db.con;
    	Seat seat = Ticket.getTicket(bookingID).seat;
    	
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM TICKET WHERE BookingID = ?");
            stmt.setString(1, bookingID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Ticket with BookingID: " + bookingID + " cancelled successfully.");
            } else {
                System.out.println("No matching BookingID found to cancel.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Seat.cancelSeat(seat, con);
    }
    
    public static Ticket getTicket(String bookingID) throws IOException {
        Ticket ticket = null;
        Database db = Database.getDatabaseInstance();
        Connection con = db.con;
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM TICKET WHERE BookingID = ?");
            // PreparedStatement stmt = con.prepareStatement("SELECT T.*, S.SeatID FROM TICKET T JOIN SEAT S ON T.BookingID = S.BookingID WHERE T.BookingID = ?");
            stmt.setString(1, bookingID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String flightID = rs.getString("FlightID");
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String passengerID = rs.getString("PassengerID");
                String cardN = rs.getString("CardN");
                String email = rs.getString("Email");
                String seatID = rs.getString("SeatID");

                ticket = new Ticket(flightID, fName, lName, passengerID, cardN, email, getSeatDetails(con, seatID));
                ticket.BookingID = bookingID;
            }else {
            	System.out.println("BookingID is not valid, returning null ");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }
    
    private static Seat getSeatDetails(Connection con, String seatID) {
        Seat seat = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM SEAT WHERE SeatID = ?");
            stmt.setString(1, seatID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                seat = new Seat(
                    rs.getString("SeatID"),
                    rs.getBoolean("EconomyFlag"),
                    rs.getBoolean("PremiumFlag"),
                    rs.getBoolean("BusinessFlag"),
                    rs.getString("FlightID"),
                    rs.getBoolean("InsuranceFlag")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }
}
