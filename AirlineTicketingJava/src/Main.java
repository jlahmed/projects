import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Seat seat = new Seat("Z11", false, false, true, "AC1", true);
		Ticket ticket = new Ticket("AC1", "Jubayer", "Ahmed", "XYZ123", "1234567890123454", "jlahmed@ucalgary.ca", seat);
		ticket.insertTicket();
		
		List<Flight> list = Flight.getFlights("Calgary", "Toronto");
		for (Flight l : list) {
			System.out.println(l.FlightID + " from " + l.Origin + " to " + l.Destination);
		}
		
		List<String> passengers = Flight.getPassengers("AC1");
		for (String p : passengers) {
			System.out.println(p);
		}
		
	}      

}
