package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Order;
import entity.OrderFlight;
import util.Consts;

public class CustomerScreenControl {
	
	private static CustomerScreenControl instance;
	public static CustomerScreenControl getInstance() 
	{
		if (instance == null)
			instance = new CustomerScreenControl();
		return instance;
	}
	
	
	private ArrayList<OrderFlight> getAllOrderFlights(){

		ArrayList<OrderFlight> orderFlightsList = new ArrayList<OrderFlight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_order_flight.*\r\n"+ "FROM tbl_order_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					
					orderFlightsList.add(new OrderFlight(rs.getString(i++), rs.getString(i++), rs.getBoolean(i++)));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return orderFlightsList;
	
	}
	
	
	public ArrayList<OrderFlight> getOrderFlightsOfSCutomer(String passport){
		
//		ArrayList<OrderFlight> orderFlightsOfSCutomer = getAllOrderFlights();
		ArrayList<OrderFlight> orderFlightsOfSCutomer = new ArrayList<>();

		
		
		ArrayList<String> ordersOfCustomer = new ArrayList<>();
		
		// Get Orders of specific customers in strings
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_customer_order.*\r\n"+ "FROM tbl_customer_order");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(1).equals(passport)) // Find our customer
						ordersOfCustomer.add(rs.getString(2)); // OrdersID
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// get OrderFlights of specific customer with help from ordersOfCustomer
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_order_flight.*\r\n"+ "FROM tbl_order_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i=1;
					if (ordersOfCustomer.contains(rs.getString(2))) { // contains orderID
						orderFlightsOfSCutomer.add(new OrderFlight(rs.getString(i++), rs.getString(i++), rs.getBoolean(i++)));
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

//		System.out.println(orderFlightsOfSCutomer);
		
		return orderFlightsOfSCutomer;
	}
	
	
}




