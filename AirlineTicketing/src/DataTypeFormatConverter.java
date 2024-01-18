import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DataTypeFormatConverter {
	
	public static LocalDate stringToLocalDate (String input) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
        try {
            LocalDate localDate = LocalDate.parse(input, formatter);
            return localDate;
        } catch (Exception e) {
            System.out.println("Error parsing the date: " + e.getMessage());
            return null;
        }
	}
	
	public static LocalTime stringToLocalTime (String input) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		try {
            LocalTime localTime = LocalTime.parse(input, formatter);
            return localTime;
        } catch (Exception e) {
            System.out.println("Error parsing the time: " + e.getMessage());
            return null;
        }
	}
	
	public static LocalDateTime stringToLocalDateTime (String inputDate, String inputTime) {
		LocalDate outputDate = stringToLocalDate(inputDate);
		LocalTime outputTime = stringToLocalTime(inputTime);
		LocalDateTime output = LocalDateTime.of(outputDate, outputTime);
		return output;
	}
	
	
	public static double stringToDouble (String input) {
		try {
            double number = Double.parseDouble(input);
            return(number);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the number: " + e.getMessage());
            return -1;
        }
	}
	

}
