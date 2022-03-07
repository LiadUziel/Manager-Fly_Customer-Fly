package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.GroundAttendant;
import entity.GroundAttendantInShift;
import entity.Shift;
import util.Consts;

public class ShibutzGroundShiftControl {
	private static ShibutzGroundShiftControl instance;
	public static ShibutzGroundShiftControl getInstance() 
	{
		if (instance == null)
			instance = new ShibutzGroundShiftControl();
		return instance;
	}
	
	
    public ArrayList<String> getAllShiftIDs() {
		ArrayList<String> shiftIDList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_shift.*\r\n"+ "FROM tbl_shift");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					shiftIDList.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return shiftIDList;
	}
    
    public ArrayList<GroundAttendant> getAllGroundAttendants() {
		ArrayList<GroundAttendant> groundAttendantList = new ArrayList<GroundAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_ground_attendant.*\r\n"+ "FROM tbl_ground_attendant");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					if (rs.getDate(5) == null) // endWorkDate
						groundAttendantList.add(new GroundAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), null));
					else
						groundAttendantList.add(new GroundAttendant(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getDate(i++).toLocalDate()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return groundAttendantList;
	}
    
    public ArrayList<Shift> getAllShifts() {
		ArrayList<Shift> shiftList = new ArrayList<Shift>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_shift.*\r\n"+ "FROM tbl_shift");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					shiftList.add(new Shift(rs.getString(i++), rs.getDate(i++).toLocalDate(), rs.getTime(i++).toLocalTime(), rs.getDate(i++).toLocalDate(), rs.getTime(i++).toLocalTime()));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return shiftList;
	}


    
    public ArrayList<String> getGroundAttendantInShiftIdRole(String shiftID) {
		ArrayList<String> groundAttendantList = new ArrayList<>();
		ArrayList<String> gaAndRole = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_ground_attendant_in_shift.*\r\n"+ "FROM tbl_ground_attendant_in_shift");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(1).equals(shiftID)) {
						groundAttendantList.add(rs.getString(2));
						
						gaAndRole.add("Attendant ID: "+ rs.getString(2) + ", Role: " + rs.getString(3));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		return groundAttendantList;
		return gaAndRole;
	}
    
    public ArrayList<String> getGroundAttendantInShiftID(String shiftID) {
		ArrayList<String> groundAttendantList = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_ground_attendant_in_shift.*\r\n"+ "FROM tbl_ground_attendant_in_shift");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(1).equals(shiftID)) {
						groundAttendantList.add(rs.getString(2));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		return groundAttendantList;
		return groundAttendantList;
	}
    
    
    public void addGAinShiftToDB(GroundAttendantInShift gaInShift) throws ClassNotFoundException, SQLException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_GROUND_ATTENDANT_TO_SHIFT)){
			int i = 1;
			stmt.setString(i++, gaInShift.getShiftID());
			stmt.setString(i++, gaInShift.getGaID());
			stmt.setString(i++, gaInShift.getRole());
			stmt.executeUpdate();
		}
		return;	

    }
    
    
    public void removeGAfromShiftIN_DB(String shiftID, String gaID) throws ClassNotFoundException, SQLException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_DEL_GROUND_ATTENDANT_FROM_FLIGHT)){
			int i = 1;
			stmt.setString(i++, shiftID);
			stmt.setString(i++, gaID);
			stmt.executeUpdate();
		}
		return;	

    }





}
