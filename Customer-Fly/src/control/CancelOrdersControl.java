package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Flight;
import entity.OrderFlight;
import util.Consts;

public class CancelOrdersControl {
	
	private static CancelOrdersControl instance;
	public static CancelOrdersControl getInstance() 
	{
		if (instance == null)
			instance = new CancelOrdersControl();
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
	
	public ArrayList<OrderFlight> getOrderFlightsCanBeDeleted(){
		ArrayList<OrderFlight> allOrderFlights = getAllOrderFlights();
		ArrayList<OrderFlight> filteredOrderFlights = new ArrayList<>();
		ArrayList<Flight> flightsFromJson = ImportControl.getInstance().importFlightsFromJson();
		
		for (OrderFlight orderFlight : allOrderFlights)
			for (Flight flight : flightsFromJson)
				if (orderFlight.getFlightNumber().equals(flight.getFlightNumber()) && !orderFlight.isHasCancelled())
					filteredOrderFlights.add(orderFlight);
		
		return filteredOrderFlights;
	}
	
	
    public void updateCancelledOrderInDB(String flightNumber, String orderID) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_UPDATE_CANCELLED_ORDERS)){
			int i = 1;
			stmt.setString(i++, flightNumber);
			stmt.setString(i++, orderID);
			
			stmt.executeUpdate();
		}
		return;	
    }

	
	
	
	
	

}
