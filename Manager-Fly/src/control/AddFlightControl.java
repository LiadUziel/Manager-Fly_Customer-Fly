package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import entity.Airport;
import entity.Flight;
import entity.Plane;
import entity.Seat;
import util.Consts;

public class AddFlightControl {
	private static AddFlightControl instance;
	public static AddFlightControl getInstance() 
	{
		if (instance == null)
			instance = new AddFlightControl();
		return instance;
	}
	
	
    public ArrayList<Airport> getAllAirports() {
		ArrayList<Airport> airportList = new ArrayList<Airport>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_airport.*\r\n"+ "FROM tbl_airport");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					airportList.add(new Airport(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDouble(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return airportList;
	}
    
    public ArrayList<Airport> getRelevantAirports(LocalDate date, LocalTime hour, String str){
    	ArrayList<Airport> airportList = getAllAirports();
    	
    	ArrayList<String> airportBlackList = getAirportBlackList(date, hour, str);
    	
    	for(int i=0; i<airportList.size(); i++)
    		if (airportBlackList.contains(airportList.get(i).getAirportID()))
    			airportList.remove(i);
    	

    	
    	return airportList;
    }
    
    public ArrayList<String> getAirportBlackList(LocalDate dateFromUser, LocalTime hourFromUser, String str){
		ArrayList<String> blackListByAirport = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					LocalDate tempDate=null;
					LocalTime tempHour=null;
					if (str.equals("dep")) {
						tempDate = rs.getDate(7).toLocalDate(); 
						tempHour = rs.getTime(8).toLocalTime();
					}
					else if (str.equals("land")) {
						tempDate = rs.getDate(9).toLocalDate(); 
						tempHour = rs.getTime(10).toLocalTime();
					}
					long minutes = ChronoUnit.MINUTES.between(hourFromUser, tempHour);
					if (dateFromUser.equals(tempDate) && minutes>=-30 && minutes<=30) {
						if (str.equals("dep"))
							blackListByAirport.add(rs.getString(2));
						else if (str.equals("land"))
							blackListByAirport.add(rs.getString(3));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return blackListByAirport;
	}
    
    //--shpuld fix
    public void addAirportToDB(Airport airport) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_INS_AIRPORT)){
			int i = 1;
			stmt.setString(i++, airport.getAirportID());
			stmt.setString(i++, airport.getCountry());
			stmt.setString(i++, airport.getCity());
			stmt.setDouble(i++, airport.getTimeZone_GMT());
			stmt.executeUpdate();
		}
		return;	
    }

    
    
    public ArrayList<Plane> getAllPlanes() {
		ArrayList<Plane> planeList = new ArrayList<Plane>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_plane.*\r\n"+ "FROM tbl_plane");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					planeList.add(new Plane(rs.getString(i++), rs.getInt(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return planeList;
	}
    
    public ArrayList<Plane> getRelevantPlanes(LocalDate departureDate, LocalDate landingDate){
    	ArrayList<Plane> planeList = getAllPlanes();
    	
    	ArrayList<String> planeBlackList = getPlaneBlackList(departureDate, landingDate);
    	
    	for(int i=0; i<planeList.size(); i++)
    		if (planeBlackList.contains(planeList.get(i).getTaleID()))
    			planeList.remove(i);
    	
    	return planeList;
    }
    
    public ArrayList<String> getPlaneBlackList(LocalDate departureDate, LocalDate landingDate){
		ArrayList<String> blackListByTale = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					LocalDate tempDepDate = rs.getDate(7).toLocalDate(); 
					LocalDate tempLandDate = rs.getDate(9).toLocalDate();
					if (departureDate.equals(tempLandDate) || departureDate.equals(tempDepDate) || landingDate.equals(tempLandDate) || landingDate.equals(tempDepDate))
						blackListByTale.add(rs.getString(11));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return blackListByTale;
	}

    
    //-- Should complete
    public void addPlaneToDB(Plane plane) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_INS_PLANE)){
			int i = 1;
			stmt.setString(i++, plane.getTaleID());
			stmt.setInt(i++, plane.getRequiredFlightAttendants());
			stmt.setInt(i++, plane.getSeatsInEconomy());

			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addSeatToDB(Seat seat) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_INS_SEAT)){
			int i = 1;
			stmt.setInt(i++, seat.getSeatID());
			stmt.setInt(i++, seat.getRowNumber());
			stmt.setInt(i++, seat.getSeatNumber());
			stmt.setString(i++, seat.getSeatClass().toString());
			stmt.setString(i++, seat.getPlaneID());
			
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void updateSeatsEconomyInDB(int seatsEconomy, String planeID) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_UPDATE_SEATS_ECONOMY)){
			int i = 1;
			stmt.setInt(i++, seatsEconomy);
			stmt.setString(i++, planeID);
			
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addFlightToDB(Flight flight) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_INS_FLIGHT)){
			int i = 1;
			stmt.setString(i++, flight.getFlightID());
			stmt.setString(i++, flight.getDepartureAirport().getAirportID());
			stmt.setString(i++, flight.getLandingAirport().getAirportID());
			stmt.setString(i++, flight.getFlightStatus().toString());
			stmt.setString(i++, null); // For chiefPilot
			stmt.setString(i++, null); // For coPilot
			stmt.setDate(i++, Date.valueOf(flight.getDepartureDate()));
			stmt.setTime(i++, Time.valueOf(flight.getDepartureHour()));
			stmt.setDate(i++, Date.valueOf(flight.getLandingDate()));
			stmt.setTime(i++, Time.valueOf(flight.getLandingHour()));
			stmt.setString(i++, flight.getPlane().getTaleID());
			
			stmt.executeUpdate();
		}
		return;	
    }



}
