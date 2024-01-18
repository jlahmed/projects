import java.io.IOException;

public class Startup {

    public static void main(String[] args) {
        try {
			Database db = Database.getDatabaseInstance();
			start();
		} catch (IOException e) {
			System.out.println("Failed to connect to Database.");
			e.printStackTrace();
		}
    }

    private static void start() {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }
}
