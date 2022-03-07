package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Consts;

public class LoginControl {
	private static LoginControl instance;
	public static LoginControl getInstance() 
	{
		if (instance == null)
			instance = new LoginControl();
		return instance;
	}
	
	// index 0 - userName, index 1 - password, index 2 - type
    public ArrayList<String> getUserDetails(String userName) {
		ArrayList<String> userDetails = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_login.*\r\n"+ "FROM tbl_login");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					if (userName.equals(rs.getString(1))) { // Check user name
						userDetails.add(rs.getString(i++)); // User name
						userDetails.add(rs.getString(i++)); // Password
						userDetails.add(rs.getString(i++)); // Type
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return userDetails;
	}


}
