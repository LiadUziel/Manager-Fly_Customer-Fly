package util;

import java.net.URLDecoder;

public class Consts {
	
	private Consts() {
		throw new AssertionError();
	}
	
	public static final String BIG_FLIGHTS_REPORT_PATH = getBigFlightsReportPath();
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
//	public static final String CONN_STR = "jdbc:ucanaccess://" + "C:/Users/liadu/Desktop/תואר - מערכות מידע/סמסטר ג/תכן ופיתוח של מערכות מידע/Design - Workspace - Eclipse/EX2_JavaFX/src/enitity/Database_ManagerFly.accdb" + ";COLUMNORDER=DISPLAY";
//	public static final String CONN_STR = "jdbc:ucanaccess://" + "src/enitity/Database_ManagerFly.accdb" + ";COLUMNORDER=DISPLAY";
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";

	
	// For flight
	
	// For airport
	public static final String SQL_INS_AIRPORT=" { call ins_airport(?,?,?,?) }";
//	public static final String SQL_INS_AIRPORT="INSERT INTO tbl_airport ( airportID, country, city, timeZone_GMT )\r\n"
//			+ "SELECT [1] AS Expr1, [2] AS Expr2, [3] AS Expr3, [4] AS Expr4;\r\n"
//			+ "";
	public static final String SQL_INS_PLANE=" { call ins_plane(?,?,?) }";
	public static final String SQL_INS_SEAT=" { call ins_seat(?,?,?,?,?) }";
	public static final String SQL_UPDATE_SEATS_ECONOMY=" { call update_seats_economy(?,?) }";
	
	public static final String SQL_INS_FLIGHT=" { call ins_flight(?,?,?,?,?,?,?,?,?,?,?) }";


	// For manage employees
	public static final String SQL_ADD_FLIGHT_ATTENDANT = " { call add_flight_attendant(?,?,?,?,?) }";
	public static final String SQL_EDIT_FLIGHT_ATTENDANT = " { call update_flight_attendant(?,?,?,?,?,?) }";
	public static final String SQL_ADD_GROUND_ATTENDANT = " { call add_ground_attendant(?,?,?,?,?) }";
	public static final String SQL_EDIT_GROUND_ATTENDANT = " { call update_ground_attendant(?,?,?,?,?,?) }";
	public static final String SQL_ADD_PILOT = " { call add_pilot(?,?,?,?,?,?,?) }";
	public static final String SQL_EDIT_PILOT = " { call update_pilot(?,?,?,?,?,?,?,?) }";
	
	// Shibutz
	public static final String SQL_ADD_FLIGHT_ATTENDANT_TO_FLIGHT = " { call ins_fa_in_flight(?,?) }";
	public static final String SQL_DEL_FLIGHT_ATTENDANT_FROM_FLIGHT = " { call del_from_fa_in_flight(?,?) }";
	public static final String SQL_UPDATE_CHIEF_PILOT_IN_FLIGHT = " { call update_chief_pilot_in_flight(?,?) }";
	public static final String SQL_UPDATE_CO_PILOT_IN_FLIGHT = " { call update_co_pilot_in_flight(?,?) }";

	public static final String SQL_ADD_GROUND_ATTENDANT_TO_SHIFT = " { call ins_ga_to_shift(?,?,?) }";
	public static final String SQL_DEL_GROUND_ATTENDANT_FROM_FLIGHT = " { call del_from_ga_in_shift(?,?) }";


	// update status from xml
	public static final String SQL_UPDATE_STATUS_IN_FLIGHT = " { call update_status_in_flight(?,?) }";

	// FOR JSON
	public static final String SQL_GET_FLIGHTS_FOR_JSON = "SELECT tbl_flight.flightID, tbl_flight.departureDate, tbl_flight.departureHour, tbl_flight.departureAirportID, tbl_flight.landingDate, tbl_flight.landingHour, tbl_flight.landingAirportID, tbl_flight.flightStatus, tbl_flight.taleID\r\n"
			+ "FROM tbl_flight INNER JOIN tbl_update_flights_json ON tbl_flight.flightID = tbl_update_flights_json.flightNumber\r\n"
			+ "WHERE (((Format(tbl_update_flights_json.updateTime,\"dd/mm/yyyy\"))=Date()));\r\n";
	
	// landing percents report
	public static final String SQL_RESET_LANDING_REPORT = "TRUNCATE TABLE tbl_landing_report;";
	public static final String SQL_ADD_TO_LANDING_REPORT = " { call ins_landing_report(?,?) }";
	



	
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				
//				System.out.println("********************* " + decoded + "/src/entity/Database_ManagerFly.accdb");
				
				return decoded + "/src/entity/Database_ManagerFly.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				return decoded + "src/entity/Database_ManagerFly.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String OLDgetBigFlightsReportPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));

				String pathToJasper = decoded + "/src/boundary/BigFlightsReport.jasper";
				System.out.println("********************* " + pathToJasper);
				return pathToJasper;
//				return "/src/boundary/BigFlightsReport.jasper";
				
			} else {
//				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
//				System.out.println("*************** " + decoded + "src/boundary/BigFlightsReport.jasper");
//				return decoded + "src/boundary/BigFlightsReport.jasper";

				return "/boundary/BigFlightsReport.jasper";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getBigFlightsReportPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));

				String pathToJasper = decoded + "/database/BigFlightsReport.jasper";
				System.out.println("********************* " + pathToJasper);
				return pathToJasper;
//				return "/src/boundary/BigFlightsReport.jasper";
				
			} else {
//				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
//				System.out.println("*************** " + decoded + "src/boundary/BigFlightsReport.jasper");
//				return decoded + "src/boundary/BigFlightsReport.jasper";

				return "/boundary/BigFlightsReport.jasper";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}




}
