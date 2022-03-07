package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import entity.Flight;
import entity.FlightStatus;
import util.Consts;

public class OrderFlightControl {
	
	private static OrderFlightControl instance;
	public static OrderFlightControl getInstance() 
	{
		if (instance == null)
			instance = new OrderFlightControl();
		return instance;
	}
	
	
    public ArrayList<Flight> getRelevantFlights() {
		ArrayList<Flight> flightsList = new ArrayList<Flight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					FlightStatus status;
					if (rs.getString(6).toLowerCase().equals(FlightStatus.Cancelled.toString().toLowerCase()) )
						status = FlightStatus.Cancelled;
					else if (rs.getString(6).toLowerCase().equals(FlightStatus.Delayed.toString().toLowerCase()))
						status = FlightStatus.Delayed;
					else
						status = FlightStatus.OnTime;
					
					if (rs.getTimestamp(2).toLocalDateTime().isAfter(LocalDateTime.now()) && status==FlightStatus.OnTime)
						flightsList.add(new Flight(rs.getString(i++), rs.getTimestamp(i++).toLocalDateTime(), rs.getString(i++), rs.getTimestamp(i++).toLocalDateTime(), rs.getString(i+=2), status, rs.getString(i++)));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightsList;
	}
    
    
    public void addOrderToDB(String orderID, String pay) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_ORDER)){
			int i = 1;
			stmt.setString(i++, orderID);
			stmt.setString(i++, pay);
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addRegularTicketToDB(String ticketNumber, String mealType, String seatClass, Double price, String orderID, String flightNumber) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_REGULAR_TICKET)){
			int i = 1;
			stmt.setString(i++, ticketNumber);
			stmt.setString(i++, mealType);
			stmt.setString(i++, seatClass);
			stmt.setDouble(i++, price);
			stmt.setString(i++, orderID);
			stmt.setString(i++, flightNumber);
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addPremiumTicketToDB(String ticketNumber, String mealType, String seatClass,
    		Double price, String orderID, String flightNumber, Integer weight, String request1, String request2, String request3) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_PREMIUM_TICKET)){
			int i = 1;
			stmt.setString(i++, ticketNumber);
			stmt.setString(i++, mealType);
			stmt.setString(i++, seatClass);
			stmt.setDouble(i++, price);
			stmt.setString(i++, orderID);
			stmt.setString(i++, flightNumber);
			stmt.setInt(i++, weight);
			stmt.setString(i++, request1);
			stmt.setString(i++, request2);
			stmt.setString(i++, request3);
			stmt.executeUpdate();
		}
		return;	
    }



}
