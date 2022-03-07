package control;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.EntertainmentProduct;
import entity.Flight;
import entity.FlightStatus;
import entity.ProductInFlight;
import entity.Supplier;
import util.Consts;

public class ProductsAndSupplierControl {
	
	private static ProductsAndSupplierControl instance;
	public static ProductsAndSupplierControl getInstance() 
	{
		if (instance == null)
			instance = new ProductsAndSupplierControl();
		return instance;
	}
	
	
    public ArrayList<EntertainmentProduct> getAllProducts() {
		ArrayList<EntertainmentProduct> productsList = new ArrayList<EntertainmentProduct>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_entertainment_product.*\r\n"+ "FROM tbl_entertainment_product");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					EntertainmentProduct product = new EntertainmentProduct(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
					productsList.add(product);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return productsList;
	}
    
    public ArrayList<Supplier> getAllSuppliers() {
		ArrayList<Supplier> supplierList = new ArrayList<Supplier>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_supplier.*\r\n"+ "FROM tbl_supplier");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Supplier product = new Supplier(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
					supplierList.add(product);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return supplierList;
	}
    
    
    public void addProductToDB(EntertainmentProduct product) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_ENTERTAINMENT_PRODUCT)){
			int i = 1;
			stmt.setString(i++, product.getProductID());
			stmt.setString(i++, product.getName());
			stmt.setString(i++, product.getDescription());
			stmt.setString(i++, product.getType());
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addSupplierToDB(Supplier supplier) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_SUPPLIER)){
			int i = 1;
			stmt.setString(i++, supplier.getSupplierID());
			stmt.setString(i++, supplier.getName());
			stmt.setString(i++, supplier.getPhone());
			stmt.setString(i++, supplier.getMail());
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addSupplierProductToDB(String supplierID, String ProductID) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_SUPPLIER_PRODUCT)){
			int i = 1;
			stmt.setString(i++, supplierID);
			stmt.setString(i++, ProductID);
			stmt.executeUpdate();
		}
		return;	
    }
    
    public void addProductInFlightToDB(String flightNumber, String ProductID, String supplierID, String feedback) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_ADD_PRODUCT_IN_FLIGHT)){
			int i = 1;
			stmt.setString(i++, flightNumber);
			stmt.setString(i++, ProductID);
			stmt.setString(i++, supplierID);
			stmt.setString(i++, feedback);
			stmt.executeUpdate();
		}
		return;	
    }


    // Product in flight screen
    
    public ArrayList<Flight> getAllFlights() {
		ArrayList<Flight> flightsList = new ArrayList<Flight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					
					FlightStatus status;
					if (rs.getString(6).toLowerCase().equals(FlightStatus.Cancelled.toString().toLowerCase()) )
						status = FlightStatus.Cancelled;
					else if (rs.getString(6).toLowerCase().equals(FlightStatus.Delayed.toString().toLowerCase()))
						status = FlightStatus.Delayed;
					else
						status = FlightStatus.OnTime;
					
					flightsList.add(new Flight(rs.getString(1), rs.getTimestamp(2).toLocalDateTime(), rs.getString(3), rs.getTimestamp(4).toLocalDateTime(), rs.getString(5), status, rs.getString(7)));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightsList;
	}
    
    
    public ArrayList<ProductInFlight> getProductsOfSpecFlight(String flightID) {
		ArrayList<ProductInFlight> productInFlightList = new ArrayList<ProductInFlight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_entertainment_product_in_flight.*\r\n"+ "FROM tbl_entertainment_product_in_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (flightID.equals(rs.getString(1))) {
						ProductInFlight pif = new ProductInFlight(rs.getString(2), rs.getString(3), rs.getString(4));
						productInFlightList.add(pif);
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return productInFlightList;
	}

    
    public String getSupplierIdOfSpecProduct(String productID) {

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_supplier_entertainment_product.*\r\n"+ "FROM tbl_supplier_entertainment_product");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (productID.equals(rs.getString(2))) {
						return rs.getString(1);
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


}
