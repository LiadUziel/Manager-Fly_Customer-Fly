package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Consts;

public class GroundAttendantControl {

	
	private static GroundAttendantControl instance;
	public static GroundAttendantControl getInstance() 
	{
		if (instance == null)
			instance = new GroundAttendantControl();
		return instance;
	}
	
	
    public ArrayList<String> getOrdersOfFlight(String flightID) {
		ArrayList<String> ordersList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_order_flight.*\r\n"+ "FROM tbl_order_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					if (rs.getString(1).equals(flightID))
						ordersList.add(rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ordersList;
	}
    
    
    public ArrayList<String> getCustomersOfOrder(String orderID) {
		ArrayList<String> customerList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_customer_order.*\r\n"+ "FROM tbl_customer_order");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					if (rs.getString(2).equals(orderID))
						customerList.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return customerList;
	}
    
    
    public ArrayList<String> getFamilyMembersOfCustomer(String customerID) {
		ArrayList<String> customerList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_family_member.*\r\n"+ "FROM tbl_family_member");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(1).equals(customerID)) {
						customerList.add(rs.getString(2));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return customerList;
	}


}
