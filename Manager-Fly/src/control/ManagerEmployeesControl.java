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

import entity.FlightAttendant;
import entity.GroundAttendant;
import entity.Pilot;
import util.Consts;

public class ManagerEmployeesControl {
	private static ManagerEmployeesControl instance;
	public static ManagerEmployeesControl getInstance() 
	{
		if (instance == null)
			instance = new ManagerEmployeesControl();
		return instance;
	}
	
	
    public ArrayList<FlightAttendant> getAllFlightAttendants() {
		ArrayList<FlightAttendant> faList = new ArrayList<FlightAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight_attendant.*\r\n"+ "FROM tbl_flight_attendant");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					if (rs.getDate(5) == null) // endWorkDate
						faList.add(new FlightAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), null));
					else
						faList.add(new FlightAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getDate(i++).toLocalDate()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return faList;
	}
    
    public ArrayList<String> getAllFA_IDs(){
    	ArrayList<String> fa_ids = new ArrayList<>();
    	
    	try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight_attendant.*\r\n"+ "FROM tbl_flight_attendant");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					fa_ids.add(rs.getString(1));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return fa_ids;
    }

	
    public void addFlightAttendantToDB(FlightAttendant flightAttendant) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_FLIGHT_ATTENDANT)){
			int i = 1;
			stmt.setString(i++, flightAttendant.getExployeeID());
			stmt.setString(i++, flightAttendant.getFirstName());
			stmt.setString(i++, flightAttendant.getLastName());
			stmt.setDate(i++, Date.valueOf(flightAttendant.getStartWorkDate()));
			if (flightAttendant.getEndWorkDate() != null)
				stmt.setDate(i++, Date.valueOf(flightAttendant.getEndWorkDate()));
			else
				stmt.setDate(i++, null);

			stmt.executeUpdate();
		}
		return;	
    }
    
    public void editFlightAttendantInDB(FlightAttendant flightAttendant) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_EDIT_FLIGHT_ATTENDANT)){
			int i = 1;
			stmt.setString(i++, flightAttendant.getExployeeID());
			stmt.setString(i++, flightAttendant.getFirstName());
			stmt.setString(i++, flightAttendant.getLastName());
			stmt.setDate(i++, Date.valueOf(flightAttendant.getStartWorkDate()));
			if (flightAttendant.getEndWorkDate() != null)
				stmt.setDate(i++, Date.valueOf(flightAttendant.getEndWorkDate()));
			else
				stmt.setDate(i++, null);
			stmt.setString(i++, flightAttendant.getExployeeID());
			
			stmt.executeUpdate();
		}
		return;	
    }
    
    
    // Ground Attendants
    public ArrayList<GroundAttendant> getAllGroundAttendants() {
		ArrayList<GroundAttendant> gaList = new ArrayList<GroundAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_ground_attendant.*\r\n"+ "FROM tbl_ground_attendant");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					if (rs.getDate(5) == null) // endWorkDate
						gaList.add(new GroundAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), null));
					else
						gaList.add(new GroundAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getDate(i++).toLocalDate()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return gaList;
	}
    
    public ArrayList<String> getAllGA_IDs(){
    	ArrayList<String> fa_ids = new ArrayList<>();
    	
    	try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_ground_attendant.*\r\n"+ "FROM tbl_ground_attendant");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					fa_ids.add(rs.getString(1));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return fa_ids;
    }


    public void addGroundAttendantToDB(GroundAttendant groundAttendant) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_GROUND_ATTENDANT)){
			int i = 1;
			stmt.setString(i++, groundAttendant.getExployeeID());
			stmt.setString(i++, groundAttendant.getFirstName());
			stmt.setString(i++, groundAttendant.getLastName());
			stmt.setDate(i++, Date.valueOf(groundAttendant.getStartWorkDate()));
			if (groundAttendant.getEndWorkDate() != null)
				stmt.setDate(i++, Date.valueOf(groundAttendant.getEndWorkDate()));
			else
				stmt.setDate(i++, null);

			stmt.executeUpdate();
		}
		return;	
    }
    
    public void editGroundAttendantInDB(GroundAttendant groundAttendant) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_EDIT_GROUND_ATTENDANT)){
			int i = 1;
			stmt.setString(i++, groundAttendant.getExployeeID());
			stmt.setString(i++, groundAttendant.getFirstName());
			stmt.setString(i++, groundAttendant.getLastName());
			stmt.setDate(i++, Date.valueOf(groundAttendant.getStartWorkDate()));
			if (groundAttendant.getEndWorkDate() != null)
				stmt.setDate(i++, Date.valueOf(groundAttendant.getEndWorkDate()));
			else
				stmt.setDate(i++, null);
			stmt.setString(i++, groundAttendant.getExployeeID());
			
			stmt.executeUpdate();
		}
		return;	
    }
    
    
    // Pilots
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
						pilotList.add(new Pilot(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), null, rs.getString(6), rs.getDate(7).toLocalDate()));
					else
						pilotList.add(new Pilot(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getDate(i++).toLocalDate(), rs.getString(6), rs.getDate(7).toLocalDate()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pilotList;
	}
    
    public ArrayList<String> getAllPilot_IDs(){
    	ArrayList<String> pilot_ids = new ArrayList<>();
    	
    	try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_pilot.*\r\n"+ "FROM tbl_pilot");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					pilot_ids.add(rs.getString(1));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return pilot_ids;
    }


    public void addPilotToDB(Pilot pilot) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_PILOT)){
			int i = 1;
			stmt.setString(i++, pilot.getExployeeID());
			stmt.setString(i++, pilot.getFirstName());
			stmt.setString(i++, pilot.getLastName());
			stmt.setDate(i++, Date.valueOf(pilot.getStartWorkDate()));
			if (pilot.getEndWorkDate() != null)
				stmt.setDate(i++, Date.valueOf(pilot.getEndWorkDate()));
			else
				stmt.setDate(i++, null);
			stmt.setString(i++, pilot.getLicenceNumber());
			stmt.setDate(i++, Date.valueOf(pilot.getEndWorkDate()));

			stmt.executeUpdate();
		}
		return;	
    }
    
    public void editPilotInDB(Pilot pilot) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_EDIT_PILOT)){
			int i = 1;
			stmt.setString(i++, pilot.getExployeeID());
			stmt.setString(i++, pilot.getFirstName());
			stmt.setString(i++, pilot.getLastName());
			stmt.setDate(i++, Date.valueOf(pilot.getStartWorkDate()));
			if (pilot.getEndWorkDate() != null)
				stmt.setDate(i++, Date.valueOf(pilot.getEndWorkDate()));
			else
				stmt.setDate(i++, null);
			stmt.setString(i++, pilot.getLicenceNumber());
			stmt.setDate(i++, Date.valueOf(pilot.getEndWorkDate()));
			
			stmt.setString(i++, pilot.getExployeeID());
			
			stmt.executeUpdate();
		}
		return;	
    }



}
