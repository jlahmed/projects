
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkDateFormat {
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    public static boolean isValidDateFormat(String input) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(input);
        
        if (!matcher.matches()) {
        	
            return false; // Date format doesn't match
        }
        String[] parts = input.split("/");
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        return month >= 1 && month <= 12 && day >= 1 && day <= 31;
    }
}
