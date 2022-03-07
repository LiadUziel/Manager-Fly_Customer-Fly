package boundary;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import control.ChangePriceParametersControl;
import control.OrderFlightControl;
import control.ProductsAndSupplierControl;
import entity.Flight;
import entity.FlightStatus;
import entity.ProductInFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OrderFlight implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TableView<Flight> flightTable;

    @FXML
    private TableColumn<Flight, String> flightNumberCol;

    @FXML
    private TableColumn<Flight, LocalDateTime> departureTimeCol;

    @FXML
    private TableColumn<Flight, String> departureAirportCol;

    @FXML
    private TableColumn<Flight, LocalDateTime> landingTimeCol;

    @FXML
    private TableColumn<Flight, String> landingAirportCol;

    @FXML
    private TableColumn<Flight, FlightStatus> flightStatusCol;

    @FXML
    private ComboBox<Flight> relevantFlightsChoose;

    @FXML
    private ComboBox<String> payMethodChoose;

    @FXML
    private ComboBox<String> mealTypeChoose;

    @FXML
    private ComboBox<String> seatClassChoose;

    @FXML
    private CheckBox isPremium;
    private boolean premiumFlag;

    @FXML
    private AnchorPane premiumPane;

    @FXML
    private ComboBox<Integer> weightSuitcaseChoose;

    @FXML
    private TextField firstRequest;

    @FXML
    private TextField secondRequest;

    @FXML
    private TextField thirdRequest;

    @FXML
    private ComboBox<Integer> quantityChoose;
    

    @FXML
    private AnchorPane productsPane;

    @FXML
    private ListView<String> productsOfFlightList;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Flights Table
		// Flights table
		flightNumberCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightNumber"));
		departureTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureDate"));
		departureAirportCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureAirportId"));
		landingTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingDate"));
		landingAirportCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("landingAirportId"));
		flightStatusCol.setCellValueFactory(new PropertyValueFactory<Flight, FlightStatus>("flightStatus"));


		ObservableList<Flight> relevantFlights = FXCollections.observableArrayList(OrderFlightControl.getInstance().getRelevantFlights());
		flightTable.setItems(relevantFlights);

		
		
		// Flights Combo Box
		relevantFlightsChoose.getItems().addAll(relevantFlights);
		
		// Payment Method ComboBox
		payMethodChoose.getItems().add("Credit");
		payMethodChoose.getItems().add("PayPal");
		payMethodChoose.getItems().add("BankTransfer");
		
		// Meal Type ComboBox
		mealTypeChoose.getItems().add("NoMeal");
		mealTypeChoose.getItems().add("Kosher");
		mealTypeChoose.getItems().add("Vegan");
		mealTypeChoose.getItems().add("Vegetarian");
		
		// SeatClass ComboBox
		seatClassChoose.getItems().add("Economy");
		seatClassChoose.getItems().add("Buisness");
		seatClassChoose.getItems().add("FirstClass");
		
		// premium panel
		premiumFlag = false;
		premiumPane.setVisible(false);
		weightSuitcaseChoose.getItems().add(5);
		weightSuitcaseChoose.getItems().add(10);
		weightSuitcaseChoose.getItems().add(15);
		weightSuitcaseChoose.getItems().add(20);
		weightSuitcaseChoose.getItems().add(25);
		weightSuitcaseChoose.setValue(5);
		
		// Quantity ComboBox
		quantityChoose.getItems().add(1);
		quantityChoose.getItems().add(2);
		quantityChoose.getItems().add(3);
		quantityChoose.getItems().add(4);
		quantityChoose.getItems().add(5);
		quantityChoose.setValue(1);
		
	}

	private String generateOrderID() {
		byte[] array = new byte[10]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));

	    return generatedString;
	}
	
	private Double calcPrice(long hoursDuration, String meal, String seatClass, Integer weight) {
		// index 0 - duration, index 1 - meal, index 2 - class
		ArrayList<Integer> params = ChangePriceParametersControl.getInstance().getOldParamsDetails();
		int constDuration = params.get(0), constMeal = params.get(1), constClass = params.get(2), ConstWeight = 20;
		double mealVal, classVal;
		
		if (meal.equals("NoMeal"))
			mealVal = 0;
		else
			mealVal = 1;
		
		if (seatClass.equals("Economy"))
			classVal = 0;
		else if (seatClass.equals("Buisness"))
			classVal = 1;
		else
			classVal = 2;
		
		
		return constDuration*hoursDuration + constMeal*mealVal + constClass*classVal + ConstWeight*weight;
	}

    @FXML
    void orderFlight(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	Flight flight = relevantFlightsChoose.getValue();
    	String payMethod = payMethodChoose.getValue();
    	String mealType = mealTypeChoose.getValue();
    	String seatClass = seatClassChoose.getValue();
    	if (flight==null || payMethod==null || mealType==null || seatClass==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	Double price = null;
    	String request1 = firstRequest.getText(), request2 = secondRequest.getText(), request3 = thirdRequest.getText();
    	long hoursDuration = ChronoUnit.HOURS.between(flight.getDepartureDate(), flight.getLandingDate());
    	
    	if (premiumFlag)
    		price = calcPrice(hoursDuration, mealType, seatClass, weightSuitcaseChoose.getValue());
    	else
    		price = calcPrice(hoursDuration, mealType, seatClass, 0);
    	System.out.println("price: " + price);
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Order Flight");
		alert.setHeaderText("The price is: " + price + " per ticket, are you sure?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			String orderID = generateOrderID();
			OrderFlightControl.getInstance().addOrderToDB(orderID, payMethod);
			for (Integer i=1; i<=quantityChoose.getValue(); i++) {
				String flightTicketID = "";
				flightTicketID += orderID + "_" +i;
				if (premiumFlag)
					OrderFlightControl.getInstance().addPremiumTicketToDB(flightTicketID, mealType, seatClass, price, orderID, flight.getFlightNumber(), weightSuitcaseChoose.getValue(), request1, request2, request3);
				else
					OrderFlightControl.getInstance().addRegularTicketToDB(flightTicketID, mealType, seatClass, price, orderID, flight.getFlightNumber());
				System.out.println(flightTicketID);
			}
			switchTo_CustomerScreen(event);
		}

    }

    @FXML
    void showPremiun(ActionEvent event) {
    	if (!premiumFlag) {
    		premiumPane.setVisible(true);
    		premiumFlag = true;
    	}
    	else {
    		premiumPane.setVisible(false);
    		premiumFlag = false;
    	}

    }

    @FXML
    void showProductsOfFlight(ActionEvent event) {
    	String flightID = relevantFlightsChoose.getValue().getFlightNumber();
    	
    	ArrayList<ProductInFlight> pifList = ProductsAndSupplierControl.getInstance().getProductsOfSpecFlight(flightID);
    	
    	productsOfFlightList.getItems().clear();
    	
    	for (ProductInFlight pif: pifList)
    		productsOfFlightList.getItems().add("Product: " + pif.getProductID() + ", Supplier: " + pif.getSupplierID());
    	
    	productsPane.setVisible(true);
    }
    
    
    @FXML
    void switchTo_CustomerScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/CustomerScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Customer Screen");
		 stage.show();
    }


}
