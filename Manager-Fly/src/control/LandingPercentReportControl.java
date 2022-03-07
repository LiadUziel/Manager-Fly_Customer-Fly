package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import entity.Flight;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;

public class LandingPercentReportControl {
	
	private static LandingPercentReportControl instance;
	public static LandingPercentReportControl getInstance() 
	{
		if (instance == null)
			instance = new LandingPercentReportControl();
		return instance;
	}

	
	public HashMap<String, ArrayList<String>> countryAndAirports(){
		HashMap<String, ArrayList<String>> map = new HashMap<>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_airport.*\r\n"+ "FROM tbl_airport");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (map.get(rs.getString(2)) == null) {// country
						ArrayList<String> airports = new ArrayList<>();
						map.put(rs.getString(2), airports);
					}
					ArrayList<String> updatedAirports = map.get(rs.getString(2));
					updatedAirports.add(rs.getString(1));
					map.put(rs.getString(2), updatedAirports);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public HashMap<String, String> airportAndCountry(){
		HashMap<String, String> aAndC = new HashMap<>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_airport.*\r\n"+ "FROM tbl_airport");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					aAndC.put(rs.getString(1), rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return aAndC;
	}

	
	
    public ArrayList<Flight> getFlights_LastMonth() {
		ArrayList<Flight> flightsLastMonth = new ArrayList<Flight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
//					LocalDate.now().minusDays(31).isBefore(rs.getDate(7).toLocalDate())
//					if (LocalDate.now().toEpochDay() - rs.getDate(7).toLocalDate().toEpochDay() < 32)
//						flightsLastMonth.add(new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(7).toLocalDate(), rs.getTime(8).toLocalTime(), rs.getDate(9).toLocalDate(), rs.getTime(10).toLocalTime()));
					LocalDate departureDate = rs.getDate(7).toLocalDate();
					if (LocalDate.now().minusMonths(1).isBefore(departureDate)
							&& LocalDate.now().isAfter(departureDate))
						flightsLastMonth.add(new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(7).toLocalDate(), rs.getTime(8).toLocalTime(), rs.getDate(9).toLocalDate(), rs.getTime(10).toLocalTime()));


				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightsLastMonth;
	}
    
    
    public void resetLandingReport() throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_RESET_LANDING_REPORT)){
			
			stmt.executeUpdate();
		}
		return;	
    }
    
    
    public void addLandingReportToDB(String country, String percent) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_TO_LANDING_REPORT)){
			int i = 1;
			stmt.setString(i++, country);
			stmt.setString(i++, percent);

			stmt.executeUpdate();
		}
		return;	
    }
    
    
	public JFrame produceReport() 
	{

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();

//				params.put("agent_id", (Integer) agentNum.getId());
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/LandingCountriesReport.jasper"),
//						getClass().getResourceAsStream(Consts.BIG_FLIGHTS_REPORT_PATH),
						params, conn);
				JFrame frame = new JFrame("Big Flights Report");
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	
	}



    
    
    


}
