package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;

public class ExportJsonFlightsControl {

	private static ExportJsonFlightsControl instance;
	public static ExportJsonFlightsControl getInstance() 
	{
		if (instance == null)
			instance = new ExportJsonFlightsControl();
		return instance;
	}
	
	
	public ArrayList<String> getFlightsIdFromLastDay(){
		ArrayList<String> flightsIdToday = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_update_flights_json.*\r\n"+ "FROM tbl_update_flights_json");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getTimestamp(1).toLocalDateTime().toLocalDate().equals(LocalDate.now())) {
						flightsIdToday.add(rs.getString(2));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightsIdToday;
	}
	
	public static void exportToJSON() //java.sql.Date today 
	{
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall("SELECT tbl_flights_for_json.*\r\n"+ "FROM tbl_flights_for_json")){
//					stmt.setDate(1, today);
					ResultSet rs = stmt.executeQuery(); {
				JsonArray updatedShows = new JsonArray();
				while (rs.next()) {
					JsonObject updatedShow = new JsonObject();

					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						updatedShow.put(rs.getMetaData().getColumnName(i), rs.getString(i));


					updatedShows.add(updatedShow);
				}

				JsonObject doc = new JsonObject();
				doc.put("flights", updatedShows);

				File file = new File("json/flights.json");
				file.getParentFile().mkdir();

				try (FileWriter writer = new FileWriter(file)) {
					writer.write(Jsoner.prettyPrint(doc.toJson()));
					writer.flush();
					 Alert alert = new Alert(AlertType.INFORMATION, "Flights data exported successfully!");
					 alert.setHeaderText("Success");
					 alert.setTitle("Success Data Export");
					 alert.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
					}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}

}
