package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;

public class ReportProductControl {
	
	private static ReportProductControl instance;
	public static ReportProductControl getInstance() 
	{
		if (instance == null)
			instance = new ReportProductControl();
		return instance;
	}
	
	public HashSet<String> getAllAirports(){
		HashSet<String> allAirports = new HashSet<>();
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					
					allAirports.add(rs.getString(3));
					allAirports.add(rs.getString(5));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return allAirports;
	}
	
	
	public ArrayList<String> getFlightsWithSpecAirportFromLastMonth(String airport){
		ArrayList<String> relevantFlights = new ArrayList<>();
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					LocalDate dateDep = rs.getTimestamp(2).toLocalDateTime().toLocalDate();
					LocalDate dateDest = rs.getTimestamp(4).toLocalDateTime().toLocalDate();
					
					// flights from last month
					if (LocalDateTime.now().toLocalDate().toEpochDay() - dateDep.toEpochDay() >= -30 && 
							LocalDateTime.now().toLocalDate().toEpochDay() - dateDep.toEpochDay() <= 30
							|| LocalDateTime.now().toLocalDate().toEpochDay() - dateDest.toEpochDay() >= -30
							&& LocalDateTime.now().toLocalDate().toEpochDay() - dateDest.toEpochDay() <= 30) {
						
						if (airport.equals(rs.getString(3)) || airport.equals(rs.getString(5)))
							relevantFlights.add(rs.getString(1)); // flight Number
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return relevantFlights;
	}
	
	
    public void resetProductsReport() throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_RESET_PRODUCTS_REPORT)){
			
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addLandingReportToDB(String country, Integer freq) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_TO_PRODUCTS_REPORT)){
			int i = 1;
			stmt.setString(i++, country);
			stmt.setInt(i++, freq);

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
						getClass().getResourceAsStream("/boundary/ProductsReport.jasper"),
//						getClass().getResourceAsStream(Consts.BIG_FLIGHTS_REPORT_PATH),
						params, conn);
				JFrame frame = new JFrame("Products Frequency Report");
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
