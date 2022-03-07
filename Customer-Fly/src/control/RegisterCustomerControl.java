package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Customer;
import util.Consts;

public class RegisterCustomerControl {

	private static RegisterCustomerControl instance;
	public static RegisterCustomerControl getInstance() 
	{
		if (instance == null)
			instance = new RegisterCustomerControl();
		return instance;
	}
	
	
    public ArrayList<String> getAllPassports() {
		ArrayList<String> passportsList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_customer.*\r\n"+ "FROM tbl_customer");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					passportsList.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return passportsList;
	}
    
    
    public void addCustomerToDB(Customer customer) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_CUSTOMER)){
			int i = 1;
			stmt.setString(i++, customer.getPassportNumber());
			stmt.setString(i++, customer.getFirstName());
			stmt.setString(i++, customer.getLastName());
			stmt.setString(i++, customer.getEmail());
			stmt.setString(i++, customer.getPrimaryCitizenship());
			stmt.setDate(i++, Date.valueOf(customer.getDateOfBirth()));
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addLoginToDB(String passport, String password) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_LOGIN)){
			int i = 1;
			stmt.setString(i++, passport);
			stmt.setString(i++, password);
			stmt.executeUpdate();
		}
		return;	
    }



}
