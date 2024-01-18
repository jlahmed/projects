import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;


public class Database  
{
    public Connection con;
    static private Database DatabaseInstance = null;
   
    private void createTables() 
    {   
		try {
			PreparedStatement stmt;
			
			// Creating registered user table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS REGISTERED_USER" +
			        "(Username VARCHAR(20) NOT NULL, " +
			        "Password VARCHAR(20) NOT NULL, " +
			        "FName VARCHAR(20) NOT NULL, " +
			        "LName VARCHAR(20) NOT NULL, " +
			        "Address VARCHAR(100), " +
			        "Email VARCHAR(50), " +
			        "MemberN INT NOT NULL AUTO_INCREMENT, " +
			        "PRIMARY KEY(Username), "+
				    "UNIQUE (MemberN))");
	        stmt.executeUpdate();
	        System.out.println("REGISTERED_USER table created in the database");
	        
	        // Creating Admin user table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS ADMIN_USER" +
			        "(Username VARCHAR(20) NOT NULL, " +
			        "Password VARCHAR(20) NOT NULL, " +
			        "FName VARCHAR(20) NOT NULL, " +
			        "LName VARCHAR(20) NOT NULL, " +
			        "PRIMARY KEY(Username))");
	        stmt.executeUpdate();
	        System.out.println("ADMIN_USER table created in the database");
	        
	        // Creating flight attendant user table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS FATT_USER" +
			        "(Username VARCHAR(20) NOT NULL, " +
			        "Password VARCHAR(20) NOT NULL, " +
			        "FName VARCHAR(20) NOT NULL, " +
			        "LName VARCHAR(20) NOT NULL, " +
			        "PRIMARY KEY(Username))");
	        stmt.executeUpdate();
	        System.out.println("FATT_USER table created in the database");
	        
	        // Creating agent user table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS AGENT_USER" +
			        "(Username VARCHAR(20) NOT NULL, " +
			        "Password VARCHAR(20) NOT NULL, " +
			        "FName VARCHAR(20) NOT NULL, " +
			        "LName VARCHAR(20) NOT NULL, " +
			        "PRIMARY KEY(Username))");
	        stmt.executeUpdate();
	        System.out.println("AGENT_USER table created in the database");
	        
	        // Creating credit card table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS CREDIT_CARD" +
			        "(CreditN VARCHAR(20) NOT NULL, " +
			        "MemberN INT NOT NULL, " +
			        "PRIMARY KEY(CreditN), " +
			        "FOREIGN KEY (MemberN) REFERENCES REGISTERED_USER(MemberN) ON UPDATE CASCADE ON DELETE CASCADE)");
	        stmt.executeUpdate();
	        System.out.println("CREDIT_CARD table created in the database");

	        // Creating Aircraft Table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS AIRCRAFT" +
			        "(AircraftID VARCHAR(20) NOT NULL, " +
			        "Type VARCHAR(20), " +
			        "Company VARCHAR(20) NOT NULL, " +
			        "PRIMARY KEY(AircraftID))");
	        stmt.executeUpdate();
	        System.out.println("AIRCRAFT table created in the database");
			
	        // Creating Flight Table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS FLIGHT" +
			        "(FlightID VARCHAR(20) NOT NULL, " +
			        "Origin VARCHAR(20) NOT NULL, " +
			        "Destination VARCHAR(20) NOT NULL, " +
			        "DepartureDate DATE NOT NULL, " +
			        "DepartureTime TIME NOT NULL, " +
			        "ArrivalDate DATE NOT NULL, " +
			        "ArrivalTime TIME NOT NULL, " +
			        "AircraftID VARCHAR(20) NOT NULL, " +
			        "BasePrice DOUBLE NOT NULL, " +
			        "PRIMARY KEY(FlightID), " +
			        "FOREIGN KEY (AircraftID) REFERENCES AIRCRAFT(AircraftID) ON UPDATE CASCADE ON DELETE CASCADE)");
	        stmt.executeUpdate();
	        System.out.println("FLIGHT table created in the database");
	        
	        // Creating Crew Table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS CREW" +
			        "(FlightID VARCHAR(20) NOT NULL, " +
			        "FName VARCHAR(20) NOT NULL, " +
			        "LName VARCHAR(20) NOT NULL, " +
			        "Position VARCHAR(20) NOT NULL DEFAULT 'Flight Attendant', " +
			        "FOREIGN KEY (FlightID) REFERENCES FLIGHT(FlightID) ON UPDATE CASCADE ON DELETE CASCADE)");
	        stmt.executeUpdate();
	        System.out.println("CREW table created in the database");
	        
	        // Creating Ticket Table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS TICKET" +
			        "(BookingID VARCHAR(20) NOT NULL, " +
			        "FlightID VARCHAR(20) NOT NULL, " +
			        "FName VARCHAR(20) NOT NULL, " +
			        "LName VARCHAR(20) NOT NULL, " +
			        "PassengerID VARCHAR(20) NOT NULL, " +
			        "ReceiptN INT NOT NULL AUTO_INCREMENT, " +
			        "CardN VARCHAR(20) NOT NULL, " +
			        "Email VARCHAR(50), " +
			        "SeatID CHAR(3) NOT NULL, " +
			        "MemberN INT NOT NULL DEFAULT -1, " +
			        "PRIMARY KEY(BookingID), " +
			        "UNIQUE (ReceiptN), " +
			        "FOREIGN KEY (FlightID) REFERENCES FLIGHT(FlightID) ON UPDATE CASCADE ON DELETE CASCADE)");
	        stmt.executeUpdate();
	        System.out.println("TICKET table created in the database");
	        
	        // Creating SEAT Table
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS SEAT" +
			        "(SeatID CHAR(3) NOT NULL, " +
			        "EconomyFlag BOOLEAN NOT NULL, " +
			        "PremiumFlag BOOLEAN NOT NULL, " +
			        "BusinessFlag BOOLEAN NOT NULL, " +
			        "Price DOUBLE NOT NULL, " +
			        "FlightID VARCHAR(20) NOT NULL, " +
			        "InsuranceFlag BOOLEAN, " +
			        "FOREIGN KEY (FlightID) REFERENCES FLIGHT(FlightID) ON UPDATE CASCADE ON DELETE CASCADE)");
	        stmt.executeUpdate();
	        System.out.println("SEAT table created in the database");
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private LocalDateTime getRandomDate(int months) {
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();

        // Calculate LocalDateTime within the specified months from now
        LocalDateTime futureDate = now.plus(months, ChronoUnit.MONTHS);

        long startSeconds = now.toEpochSecond(ZoneOffset.UTC);
        long endSeconds = futureDate.toEpochSecond(ZoneOffset.UTC);
        long randomSeconds = startSeconds + (long) (random.nextDouble() * (endSeconds - startSeconds));

        return LocalDateTime.ofEpochSecond(randomSeconds, 0, ZoneOffset.UTC);
    }
    
    public void populateDB() throws IOException {
        
    	System.out.println("Populating database");
        
    	int monthsToSchedule = 3;
    	int fNum = 150;
    	double price = 500;
    	double priceDelta = 500;
    	int maxFlightHour = 6;
    	int crewOnBoard = 4;
    	
    	Random random = new Random();
    	String[] companies = {"Air Canada", "Westjet"};
    	String[] cmp = {"AC", "WJ"};
    	String[][] aircrafts = new String[2][3]; 
    	aircrafts[0][0] = "AIC987";
    	aircrafts[0][1] = "AIC876";
    	aircrafts[0][2] = "AIC765";
    	aircrafts[1][0] = "WSJ987";
    	aircrafts[1][1] = "WSJ876";
    	aircrafts[1][2] = "WSJ765";
    	String[] aircraftType = {"Boeing 787-2", "Boeing 456-3", "AirBus370Z"};
    	String[] cities = {"Calgary", "Edmonton", "Vancouver", "Toronto", "Ottawa", "Toronto", "Montreal", "Halifax"};
    	String[] names = {"James", "Alan", "Dave", "Tyler", "John", "Suzie", "Melanie", "Bruce", "Michael", "Sarah", "Alex", "Shelly", "Carly", "Amanda", "Julie"};
    	
    	for (int c = 0; c < companies.length; c++) {
    		for (int i = 0; i < aircrafts[c].length; i++) {
    			Aircraft air = new Aircraft(aircrafts[c][i], aircraftType[i], companies[c]);
    			air.insertAircraft();
    		}
    		
    		
    		for(int i = 1; i < fNum; i++) {
    			String flightID = cmp[c] + i;
    			String airID = aircrafts[c][random.nextInt(aircrafts[c].length)];
    			
    			//Set departure and arrival times
    			String dep = cities[random.nextInt(cities.length)];
    			String arr = cities[random.nextInt(cities.length)];
    			while(dep.equals(arr)) {
    				arr = cities[random.nextInt(cities.length)];
    			}
    			LocalDateTime depT = getRandomDate(monthsToSchedule);
    	        int randomHours = random.nextInt(maxFlightHour) + 1; 
    	        int randomMinutes = random.nextInt(60); 
    	        LocalDateTime arrT = depT.plusHours(randomHours).plusMinutes(randomMinutes);
    	        LocalDate depDate = depT.toLocalDate();
    	        LocalTime depTime = depT.toLocalTime();
    	        LocalDate arrDate = arrT.toLocalDate();
    	        LocalTime arrTime = arrT.toLocalTime();
    			
    	        //Set price
    	        double new_price = price + random.nextDouble() * priceDelta;
    	        new_price = Math.round(new_price * 100.0) / 100.0;
    	        
    	        //Create and insert flight
    	        Flight flight = new Flight(flightID, dep, arr, depDate, depTime, arrDate, arrTime, airID, new_price);
    	        flight.insertFlight();
    	        
    	        //Insert crew members for the flight
    	        for (int j = 0; j < crewOnBoard; j++) {
    	        	String FName = names[random.nextInt(names.length)];
    	        	String LName = names[random.nextInt(names.length)];
    	        	
    	        	while (FName.equals(LName)) {
    	        		LName = names[random.nextInt(names.length)];
    	        	}
    	        	
    	        	Crew crew;
    	        	if (j == 0) {
    	        		crew = new Crew(flightID, FName, LName, "Pilot");
    	        	}else {
    	        		crew = new Crew(flightID, FName, LName, "Flight Attendant");
    	        	}
    	        	
    	        	crew.insertCrew();
    	        }
    		}
    	}
    	
    	//Insert users
        RegUser user1 = new RegUser("john.doe", "pass", "John", "Doe", "123 University Drive NW", "john.doe@ucalgary.ca");
        RegUser user2 = new RegUser("herb.deen", "pass", "Herb", "Dean", "411 Cranston Blv SW", "herb.deen@ucalgary.ca", "1234567890123456");
        AdminUser ad_user = new AdminUser("betty.collins", "pass", "Betty", "Collins");
        AdminUser ad_user2 = new AdminUser("james.parker", "pass", "James", "Parker");
        AgentUser ag_user = new AgentUser("clark.kent", "pass", "Clark", "Kent");
        AgentUser ag_user2 = new AgentUser("bruce.wayne", "pass", "Bruce", "Wayne");
        FUser f_user = new FUser("carl.daniels", "pass", "Carl", "Daniels");
        FUser f_user2 = new FUser("dana.white", "pass", "Dana", "White");
        
        user1.insertUser();
        user2.insertUser();
        ad_user.insertUser();
        ad_user2.insertUser();
        f_user.insertUser();
        f_user2.insertUser();
        ag_user.insertUser();
        ag_user2.insertUser();
        
        System.out.println("Database Populated. ");
    }
    
    static public Database getDatabaseInstance() throws IOException {
        if (DatabaseInstance == null) {
        	Server server = new Server();
            DatabaseInstance = new Database(server);
            if (!server.databaseExists) {
	            DatabaseInstance.populateDB();
	    		server.databaseExists = true;
            }
        }
    	return DatabaseInstance;
    }
    
    private Database(Server server)
    {
		this.con = server.con;
        if (!server.databaseExists) {
			System.out.println("Creating Database");
	    	createTables();
	    	System.out.println("Database " + server.db + " was created.");
        }
    }
}
