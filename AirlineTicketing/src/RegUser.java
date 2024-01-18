import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegUser extends User{

	String Address, Email;
	private int MemberN = -1;
	private String CardN = "-1";
	
	RegUser(String Username, String Password, String FName, String LName, String Address, String Email){
		super(Username, Password, FName, LName);
		this.Address = Address;
		this.Email = Email;
	}
	
	RegUser(String Username, String Password, String FName, String LName, String Address, String Email, String CardN){
		super(Username, Password, FName, LName);
		this.Address = Address;
		this.Email = Email;
		this.CardN = CardN;
	}
	
    public void insertUser(){
    	Database db;
		try {
			db = Database.getDatabaseInstance();
			
	    	try {
	    	    String str = "INSERT INTO REGISTERED_USER (Username, Password, FName, LName, Address, Email) " +
	    	            "VALUES (?, ?, ?, ?, ?, ?)";
	
	    	    PreparedStatement stmt = db.con.prepareStatement(str);
	    	    stmt.setString(1, Username);
	    	    stmt.setString(2, Password);
	    	    stmt.setString(3, FName);
	    	    stmt.setString(4, LName);
	    	    stmt.setString(5, Address);
	    	    stmt.setString(6, Email);
	    	    stmt.executeUpdate();
	    	    stmt.close(); 
	    	    
	    	    int MemberID = insertUserHelper(db.con);
	    	    MemberN = MemberID;
	    	    
	    	    if (Double.parseDouble(CardN) > 0) {
	    	    	insertCredit();
	    	    }
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void insertCredit() throws IOException 
    {
    	Database db = Database.getDatabaseInstance();
    	try {
    	    String str = "INSERT INTO CREDIT_CARD (CreditN, MemberN) " +
    	            "VALUES (?, ?)";

    	    PreparedStatement stmt = db.con.prepareStatement(str);
    	    stmt.setString(1, CardN);
    	    stmt.setInt(2, MemberN);
    	    stmt.executeUpdate();
    	    stmt.close(); 
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
    
    public static void insertCredit(int CardN, int MemberN) throws IOException 
    {
    	Database db = Database.getDatabaseInstance();
    	try {
    	    String str = "INSERT INTO CREDIT_CARD (CreditN, MemberN) " +
    	            "VALUES (?, ?)";

    	    PreparedStatement stmt = db.con.prepareStatement(str);
    	    stmt.setInt(1, CardN);
    	    stmt.setInt(2, MemberN);
    	    stmt.executeUpdate();
    	    stmt.close(); 
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
	
    private int insertUserHelper(Connection con) {
    	int member = -1;
    	try {
	        String selectSQL = "SELECT MemberN FROM REGISTERED_USER WHERE Username = ?";
	        PreparedStatement pstmt = con.prepareStatement(selectSQL);
	        
	        pstmt.setString(1, Username);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            member = rs.getInt("MemberN");
	        }

	        rs.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    	return member;
    }
	
	public Integer getMemberN() {
		if (MemberN >0) {
			return MemberN;
		}else {
			System.out.println("Add user to data first to get MemberN assigned.");
			return null;
		}
	}
	
	public void setMemberN(int MemberN) {
		if (MemberN >0) {
			this.MemberN = MemberN;
		}else {
			System.out.println("Must enter value greater than 0.");
		}
	}
	
    public static boolean checkValidUser(String username, String password) throws IOException {
        Database db = Database.getDatabaseInstance();
        String query = "SELECT COUNT(*) AS count FROM REGISTERED_USER WHERE Username = ? AND Password = ?";
        boolean exists = false;
        
        try (PreparedStatement stmt = db.con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password); 
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    exists = (count > 0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    public static boolean checkValidUser(String email) throws IOException {
        Database db = Database.getDatabaseInstance();
        String query = "SELECT COUNT(*) AS count FROM REGISTERED_USER WHERE Email = ?";
        boolean exists = false;
        
        try (PreparedStatement stmt = db.con.prepareStatement(query)) {
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    exists = (count > 0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    public static String getUserHelper(Connection con, int memberN) {
        String cardN = "-1"; 
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT CreditN FROM CREDIT_CARD WHERE MemberN = ?");
            stmt.setInt(1, memberN);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cardN = rs.getString("CreditN");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cardN;
    }
    
    public static RegUser getRegUser(String username, String password) throws IOException {
        RegUser regUser = null;
        Database db = Database.getDatabaseInstance();
        Connection con = db.con;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM REGISTERED_USER WHERE Username = ? AND Password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String address = rs.getString("Address");
                String email = rs.getString("Email");
                int MemberN = rs.getInt("MemberN");
                String cardN = getUserHelper(con, MemberN);

                regUser = new RegUser(username, password, fName, lName, address, email, cardN);
                regUser.setMemberN(MemberN);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regUser;
    }
    
    public static RegUser getRegUser(String email) throws IOException {
        RegUser regUser = null;
        Database db = Database.getDatabaseInstance();
        Connection con = db.con;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM REGISTERED_USER WHERE Email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String address = rs.getString("Address");
                int MemberN = rs.getInt("MemberN");
                String cardN = getUserHelper(con, MemberN);

                regUser = new RegUser(username, password, fName, lName, address, email, cardN);
                regUser.setMemberN(MemberN);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regUser;
    }
    
    public static ArrayList<RegUser> getAllRegUsers() throws IOException {
        ArrayList<RegUser> usersList = new ArrayList<>();
        Database db = Database.getDatabaseInstance();
        Connection con = db.con;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM REGISTERED_USER");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String address = rs.getString("Address");
                String email = rs.getString("Email");
                int memberN = rs.getInt("MemberN");
                String cardN = getUserHelper(con, memberN);

                RegUser regUser = new RegUser(username, password, fName, lName, address, email, cardN);
                regUser.setMemberN(memberN);
                
                usersList.add(regUser);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

}
