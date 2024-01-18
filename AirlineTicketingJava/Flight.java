import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class Flight {
	String FlightID;
	String Origin;
	String Destination;
	LocalDate DepartureDate;
	LocalTime DepartureTime;
	LocalDate ArrivalDate;
	LocalTime ArrivalTime;
	String AircraftID;
	double BasePrice;
	
	Flight(String FlightID, String Origin, String Destination, LocalDate DepartureDate, LocalTime DepartureTime, LocalDate ArrivalDate, LocalTime ArrivalTime, String AircraftID, double BasePrice){
		this.FlightID = FlightID;
		this.Origin = Origin;
		this.Destination = Destination;
		this.DepartureDate = DepartureDate;
		this.DepartureTime = DepartureTime;
		this.ArrivalDate = ArrivalDate;
		this.ArrivalTime = ArrivalTime;
		this.AircraftID = AircraftID;
		this.BasePrice = BasePrice;
	}
	
    public void insertFlight(){
    	FlightController.insertFlight(this);
    }
    
    public static List<Flight> getFlights(String origin, String destination) throws IOException {
        return FlightController.getFlights(origin, destination);
    }
    
    public static List<Flight> getFlightsAdmin (String inputDate)throws IOException {
         return FlightController.getFlightsAdmin(inputDate);
    }
    
    public static void deleteFlight(String flightID) throws IOException {
        FlightController.deleteFlight(flightID);
    }
    
    public static List<String> getPassengers(String flightID) throws IOException {
        return FlightController.getPassengers(flightID);
    }
    
    public static void changeFlightDate(LocalDateTime newDepartureDateTime, LocalDateTime newArrivalDateTime, String flightID) throws IOException {
        FlightController.changeFlightDate(newDepartureDateTime, newArrivalDateTime, flightID);
    }
}
