
abstract public class User {
	
	String Username, Password, FName, LName;
	abstract public void insertUser();
	
	protected User(String Username, String Password, String FName, String LName) {
		this.Username = Username;
		this.Password = Password;
		this.FName = FName;
		this.LName = LName;
	}
}
