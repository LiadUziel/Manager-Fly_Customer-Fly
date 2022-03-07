package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import control.CancelOrdersControl;
import control.ReportPastFlights;
import entity.Flight;
import entity.FlightStatus;
import entity.OrderFlight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import util.Consts;

public class CancelOrders  implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;


//    @FXML
//	private TextField selectedOrder;
    
    @FXML
    private ComboBox<OrderFlight> selectOrder;
	
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		selectOrder.getItems().clear();
		selectOrder.getItems().addAll(CancelOrdersControl.getInstance().getOrderFlightsCanBeDeleted());

	}

    
    
	@FXML
	void cancelOrder(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		
		if (selectOrder.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please choose order");
			alert.show();
    		return;
		}
				
		
		String flight = selectOrder.getValue().getFlightNumber();
		String order =  selectOrder.getValue().getOrderId();
		
		ArrayList<Flight> alternativeFlights = new ArrayList<>();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Cancel Order");
		alert.setHeaderText("Are you sure you want to cancel order: " + order + " in flight: " + flight);
		if (alert.showAndWait().get() == ButtonType.OK) {
			CancelOrdersControl.getInstance().updateCancelledOrderInDB(flight, order);
			
			alternativeFlights = getAlternativeFlights(flight);
			String passport = getPassportCustomerByOrderID(order);
			alternativeFlightsByCustomer.put(passport, alternativeFlights);
			System.out.println(alternativeFlightsByCustomer);
			
			if (!alternativeFlights.isEmpty()) {
				Alert alert1 = new Alert(AlertType.CONFIRMATION);
				alert1.setTitle("Alternative flights to this order");
				String str = "Alternative flights to this order:" + "\n";
				for (Flight flight1 : alternativeFlights)
					str += "Flight number: " + flight1.getFlightNumber() +", departure airport: " + flight1.getDepartureAirportId() +", landing airport: " + flight1.getLandingAirportId() + "\n";
				alert.setHeaderText(str);
				if (alert.showAndWait().get() == ButtonType.OK) 
					switchTo_UpdateFlights(event);
				return;
			}
			switchTo_UpdateFlights(event);
			return;
		
		}	
	}
	public static HashMap<String, ArrayList<Flight>> alternativeFlightsByCustomer;
	
	private String getPassportCustomerByOrderID(String order) {
		String customerPassport = null;
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_customer_order.*\r\n"+ "FROM tbl_customer_order");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getString(2).equals(order)) {
						customerPassport = rs.getString(1);
						if (alternativeFlightsByCustomer == null)
							alternativeFlightsByCustomer = new HashMap<>();
						if (alternativeFlightsByCustomer.get(customerPassport) == null) {
							ArrayList<Flight> tempArrayList = new ArrayList<>();
							alternativeFlightsByCustomer.put(customerPassport, tempArrayList);
						}
							
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



	private ArrayList<Flight> getAlternativeFlights(String flightNumber) {
		ArrayList<Flight> alternativeFlights = new ArrayList<>();
		
		Flight cancelledFlight = null;
		// find cancelledFlight
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					if(rs.getString(1).equals(flightNumber)) {
						FlightStatus status;
						if (rs.getString(6).toLowerCase().equals(FlightStatus.Cancelled.toString().toLowerCase()) )
							status = FlightStatus.Cancelled;
						else if (rs.getString(6).toLowerCase().equals(FlightStatus.Delayed.toString().toLowerCase()))
							status = FlightStatus.Delayed;
						else
							status = FlightStatus.OnTime;
					cancelledFlight = new Flight(rs.getString(i++), rs.getTimestamp(i++).toLocalDateTime(), rs.getString(i++), rs.getTimestamp(i++).toLocalDateTime(), rs.getString(5), status, rs.getString(7));
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement("SELECT tbl_flight.*\r\n"+ "FROM tbl_flight");
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int i = 1;
				
//				if (departureDate1.toEpochDay() - LocalDate.now().toEpochDay() < 60 || !(departureDate1.isBefore(landingDate) || landingDate.equals(departureDate1)) ) {
				
				String flightNumAlt = rs.getString(1);
				LocalDate depDateAlt = rs.getTimestamp(2).toLocalDateTime().toLocalDate();
				String depAirportAlt = rs.getString(3);
//				String landAirportAlt = rs.getString(5);
				
				FlightStatus status;
				if (rs.getString(6).toLowerCase().equals(FlightStatus.Cancelled.toString().toLowerCase()) )
					status = FlightStatus.Cancelled;
				else if (rs.getString(6).toLowerCase().equals(FlightStatus.Delayed.toString().toLowerCase()))
					status = FlightStatus.Delayed;
				else
					status = FlightStatus.OnTime;
				
//				if (!flightNumAlt.equals(cancelledFlight.getFlightNumber()) && days14(depDateAlt, cancelledFlight)
//						&& depAirportAlt.equals(cancelledFlight.getDepartureAirportId()) && landAirportAlt.equals(cancelledFlight.getLandingAirportId())
//						&& status.equals(FlightStatus.OnTime)) {
				
				if (!flightNumAlt.equals(cancelledFlight.getFlightNumber()) && days14(depDateAlt, cancelledFlight) && depAirportAlt.equals(cancelledFlight.getDepartureAirportId()) && status.equals(FlightStatus.OnTime))
						 {

					alternativeFlights.add(new Flight(rs.getString(i++), rs.getTimestamp(i++).toLocalDateTime(), rs.getString(i++), rs.getTimestamp(i++).toLocalDateTime(), rs.getString(i+=2), status, rs.getString(i++)));
					System.out.println("CheckAlt");
				}
					{
				}
				

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		

		return alternativeFlights;
	}



	private boolean days14(LocalDate depDateAlt, Flight cancelledFlight) {
		LocalDate dateCancelled = cancelledFlight.getDepartureDate().toLocalDate();
		
		if (dateCancelled.toEpochDay() - depDateAlt.toEpochDay() >= -14 && 
				dateCancelled.toEpochDay() - depDateAlt.toEpochDay() <= 14)
			return true;
		System.out.println("Days14 returned false");
		return false;
	}



	@FXML
	void showReportOrders(ActionEvent event) {

		try {
//		Date from = Date.valueOf(fromDate.getValue());
//		Date to = Date.valueOf(toDate.getValue());
//		
//		int numFlightParameter = 0; 
//
//		try {
//			numFlightParameter = Integer.parseInt(bigFlightsParameter.getText());
//			if (numFlightParameter<=0)
//				throw new NumberFormatException();
//		}catch(NumberFormatException e) {
//			Alert alert = new Alert(AlertType.ERROR,"Fields are missing");
//			alert.setTitle("Invalid input");
//			alert.setHeaderText("Please select positive number for big flight parameter");
//			alert.showAndWait();
//			return;
//		}
//		
//		if (from == null || to == null)
//			throw new NullPointerException();
//		
		JFrame reportFrame = ReportPastFlights.getInstance().produceReport(); // (from, to, numFlightParameter)
		if(reportFrame!=null)
				reportFrame.setVisible(true);
		
		}catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR,"Fields are missing");
			alert.setTitle("Fields are missing");
			alert.setHeaderText("Please select date from and date to");
			alert.showAndWait();
		}

	
	}

//	SELECT tbl_customer.passportNumber,Count(*) as Morning
//	FROM (tbl_order INNER JOIN (tbl_customer INNER JOIN tbl_customer_order ON tbl_customer.passportNumber = tbl_customer_order.passportNumber) ON tbl_order.orderId = tbl_customer_order.orderId) INNER JOIN (tbl_flight INNER JOIN tbl_order_flight ON tbl_flight.flightNumber = tbl_order_flight.flightNumber) ON tbl_order.orderId = tbl_order_flight.orderId
//	WHERE Hour([tbl_flight].[departureDate]) >= 5 And Hour([tbl_flight].[departureDate])<=11 Or Hour([tbl_flight].[departureDate]) >=11 And Hour([tbl_flight].[departureDate])<=16 Or Hour([tbl_flight].[departureDate]) >= 17 And Hour([tbl_flight].[departureDate])<=22 Or Hour([tbl_flight].[departureDate]) >= 23 Or Hour([tbl_flight].[departureDate]) <=4 
//	GROUP BY tbl_customer.passportNumber;

    @FXML
    void switchTo_UpdateFlights(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/UpdateFlights.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Customer Relation Manager Screen");
		 stage.show();
    }


}
