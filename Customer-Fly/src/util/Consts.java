package util;

import java.net.URLDecoder;

public class Consts {
	
	private Consts() {
		throw new AssertionError();
	}
	
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";

	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";


	
	public static final String SQL_UPDATE_CANCELLED_ORDERS=" { call update_cancelled_orders(?,?) }";

	// register customer
	public static final String SQL_ADD_CUSTOMER=" { call ins_customer(?,?,?,?,?,?) }";
	public static final String SQL_ADD_LOGIN=" { call ins_login(?,?) }";

	public static final String SQL_ADD_PARAMETERS=" { call ins_params(?,?,?,?) }";

	// Order Flight
	public static final String SQL_ADD_ORDER=" { call ins_order(?,?) }";
	public static final String SQL_ADD_REGULAR_TICKET=" { call ins_regular_ticket(?,?,?,?,?,?) }";
	public static final String SQL_ADD_PREMIUM_TICKET=" { call ins_premium_ticket(?,?,?,?,?,?,?,?,?,?) }";

	// Entertainment and products
	public static final String SQL_ADD_ENTERTAINMENT_PRODUCT=" { call ins_entertainment_product(?,?,?,?) }";
	public static final String SQL_ADD_SUPPLIER=" { call ins_supplier(?,?,?,?) }";
	public static final String SQL_ADD_SUPPLIER_PRODUCT=" { call ins_supplier_product(?,?) }";
	public static final String SQL_ADD_PRODUCT_IN_FLIGHT=" { call ins_product_in_flight(?,?,?,?) }";


	// landing percents report
	public static final String SQL_RESET_PRODUCTS_REPORT = "TRUNCATE TABLE tbl_products_report;";
	public static final String SQL_ADD_TO_PRODUCTS_REPORT = " { call ins_products_report(?,?) }";


	
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
				
				return decoded + "/src/entity/Database_CustomerFly.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				return decoded + "src/entity/Database_CustomerFly.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		


}
