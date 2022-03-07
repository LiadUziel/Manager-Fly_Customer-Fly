package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.FlightAttendant;
import entity.FlightAttendantInFlight;
import entity.Pilot;
import util.Consts;

public class ShibuzFlightEmployeesControl {
	private static ShibuzFlightEmployeesControl instance;
	public static ShibuzFlightEmployeesControl getInstance() 
	{
		if (instance == null)
			instance = new ShibuzFlightEmployeesControl();
		return instance;
	}
	
    public ArrayList<FlightAttendant> getAllFlightAttendants() {
		ArrayList<FlightAttendant> flightAttendantList = new ArrayList<FlightAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight_attendant.*\r\n"+ "FROM tbl_flight_attendant");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					if (rs.getDate(5) == null) // endWorkDate
						flightAttendantList.add(new FlightAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), null));
					else
						flightAttendantList.add(new FlightAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getDate(i++).toLocalDate()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightAttendantList;
	}
    
    public ArrayList<String> getPilotsInFlightID(String flightID) {
		ArrayList<String> pilotList = new ArrayList<>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(1).equals(flightID)) {
						pilotList.add(0, rs.getString(5)); // Chief Pilot
						pilotList.add(1, rs.getString(6)); // Co Pilot
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pilotList;
	}
    
    public ArrayList<String> getFlightAttendantInFlightID(String flightID) {
		ArrayList<String> flightAttendantList = new ArrayList<>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight_attendant_in_fight.*\r\n"+ "FROM tbl_flight_attendant_in_fight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(1).equals(flightID))
						flightAttendantList.add(rs.getString(2));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightAttendantList;
	}

    
    
    public ArrayList<Pilot> getAllPilots() {
		ArrayList<Pilot> pilotList = new ArrayList<Pilot>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_pilot.*\r\n"+ "FROM tbl_pilot");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					if (rs.getDate(5) == null) // endWorkDate
						pilotList.add(new Pilot(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(), null, rs.getString(6), rs.getDate(7).toLocalDate()));
					else
						pilotList.add(new Pilot(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getDate(i++).toLocalDate(), rs.getString(i++), rs.getDate(i++).toLocalDate()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pilotList;
	}
    
    
    public ArrayList<String> getAllFlightIDs() {
		ArrayList<String> flightIDList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					flightIDList.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightIDList;
	}
    
    
    public void addFAinFlightToDB(FlightAttendantInFlight faInFlight) throws ClassNotFoundException, SQLException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_FLIGHT_ATTENDANT_TO_FLIGHT)){
			int i = 1;
			stmt.setString(i++, faInFlight.getFlightID());
			stmt.setString(i++, faInFlight.getFaID());
			stmt.executeUpdate();
		}
		return;	

    }
    public void removeFAfromFlightIN_DB(String flightID, String faID) throws ClassNotFoundException, SQLException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_DEL_FLIGHT_ATTENDANT_FROM_FLIGHT)){
			int i = 1;
			stmt.setString(i++, flightID);
			stmt.setString(i++, faID);
			stmt.executeUpdate();
		}
		return;	

    }
    
    public void updateChiefPilotInFlight_InDB(String chiefPilotID, String flightID) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_UPDATE_CHIEF_PILOT_IN_FLIGHT)){
			int i = 1;
			stmt.setString(i++, chiefPilotID);
			stmt.setString(i++, flightID);
			
			stmt.executeUpdate();
		}
		return;	
    }
    public void updateCoPilotInFlight_InDB(String coPilotID, String flightID) throws SQLException, ClassNotFoundException, IOException {
  		Class.forName(Consts.JDBC_STR);
  		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
  				CallableStatement stmt =  conn.prepareCall(Consts.SQL_UPDATE_CO_PILOT_IN_FLIGHT)){
  			int i = 1;
  			stmt.setString(i++, coPilotID);
  			stmt.setString(i++, flightID);
  			
  			stmt.executeUpdate();
  		}
  		return;	
      }




}
