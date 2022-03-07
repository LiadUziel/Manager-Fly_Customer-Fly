package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Flight;
import entity.FlightStatus;
import entity.Seat;
import entity.SeatClass;
import util.Consts;

public class UpdateFlightControl {
	
	private static UpdateFlightControl instance;
	public static UpdateFlightControl getInstance() 
	{
		if (instance == null)
			instance = new UpdateFlightControl();
		return instance;
	}
	
	
    public ArrayList<Flight> getAllFlights() {
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
    
    
    public ArrayList<Seat> getOLDSeatsByPlane(String taleID) {
		ArrayList<Seat> seatsList = new ArrayList<Seat>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_seat.*\r\n"+ "FROM tbl_seat");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					
					if (rs.getString(3).equals(taleID)) {
						SeatClass seatClass;
						if (rs.getString(4).toLowerCase().equals(SeatClass.Business.toString().toLowerCase()) )
							seatClass = SeatClass.Business;
						else if (rs.getString(4).toLowerCase().equals(SeatClass.Economy.toString().toLowerCase()))
							seatClass = SeatClass.Economy;
						else
							seatClass = SeatClass.FirstClass;
						
						seatsList.add(new Seat(rs.getInt(i++),rs.getInt(i++), seatClass, taleID));
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return seatsList;
	}
    
    public ArrayList<Seat> getSeatsByPlane(ArrayList<Seat> seatsFromJson, String taleID){
		ArrayList<Seat> seatsList = new ArrayList<Seat>();
		
		for (Seat seat : seatsFromJson)
			if (seat.getPlaneID().equals(taleID))
				seatsList.add(seat);

		return seatsList;
    }




}
