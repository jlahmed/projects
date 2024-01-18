import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Ticket {

	String BookingID;
	String FlightID;
	String FName;
	String LName;
	String PassengerID;
	String CardN;
	String Email;
	Seat seat;
	Database db;
	
	Ticket(String FlightID, String FName, String LName, String PassengerID, String CardN, String Email, Seat seat){
		try {
			db = Database.getDatabaseInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setBookingID();
		this.FlightID = FlightID;
		this.FName = FName;
		this.LName = LName;
		this.PassengerID = PassengerID;
		this.CardN = CardN;
		this.Email = Email;
		this.seat = seat;
	}
	
	private void setBookingID() {
        Random random = new Random();
        //Generate first letter of BookingID
        char letter = (char) (random.nextInt(26) + 'A');
        // Generating a 3-digit number
        int number = random.nextInt(900) + 100; 
        String temp = letter + String.valueOf(number);
        
        //Check if this randomly generated bookingID is not already taken
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT BookingID FROM TICKET WHERE BookingID = ?";
            stmt = db.con.prepareStatement(query);
            stmt.setString(1, temp);

            rs = stmt.executeQuery();

            // If the same booking ID is in the result set, restart method.
            if (rs.next()) {
                setBookingID();
            }else {
            	BookingID = temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	
    public void insertTicket() throws IOException{
    	BookingController.insertTicket(this);
    }
    
    public void insertTicket(RegUser user) throws IOException{
    	BookingController.insertTicket(this, user);
    }
    
    
    public static void cancelTicket(String bookingID) throws IOException {
    	BookingController.cancelTicket(bookingID);
    }
    
    public static Ticket getTicket(String bookingID) throws IOException {
        return BookingController.getTicket(bookingID);
    }

}
