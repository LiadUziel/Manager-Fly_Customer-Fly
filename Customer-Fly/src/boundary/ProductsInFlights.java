package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ProductsAndSupplierControl;
import entity.EntertainmentProduct;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ProductsInFlights implements Initializable{
	
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
    private ComboBox<String> chooseFlight;

    @FXML
    private TableView<ProductInFlight> productOfFlightTable;

    @FXML
    private TableColumn<ProductInFlight, String> supplierIdCol;

    @FXML
    private TableColumn<ProductInFlight, String> productIdCol;

    @FXML
    private TableColumn<ProductInFlight, String> feedbackCol;

    
    
    @FXML
    private AnchorPane addProductPane;
    

    @FXML
    private ComboBox<EntertainmentProduct> chooseProduct;

    ArrayList<Flight> allFlights;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Flights table
		flightNumberCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightNumber"));
		departureTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureDate"));
		departureAirportCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureAirportId"));
		landingTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingDate"));
		landingAirportCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("landingAirportId"));
		flightStatusCol.setCellValueFactory(new PropertyValueFactory<Flight, FlightStatus>("flightStatus"));

		allFlights = ProductsAndSupplierControl.getInstance().getAllFlights();
		
		ObservableList<Flight> flightsList = FXCollections.observableArrayList(allFlights);
		flightTable.setItems(flightsList);

		
		// Flight comboBox
		for (Flight flight : allFlights)
			chooseFlight.getItems().add(flight.getFlightNumber());
		
		// Add product pane
		addProductPane.setVisible(false);
		productOfFlightTable.setVisible(false);
		
	}
	
	ArrayList<ProductInFlight> pie;
    @FXML
    void showProductsOfFlight(ActionEvent event) {
    	// Sub table - products of specific flight
    	supplierIdCol.setCellValueFactory(new PropertyValueFactory<ProductInFlight, String>("supplierID"));
    	productIdCol.setCellValueFactory(new PropertyValueFactory<ProductInFlight, String>("productID"));
    	feedbackCol.setCellValueFactory(new PropertyValueFactory<ProductInFlight, String>("feedback"));
    	
    	pie = ProductsAndSupplierControl.getInstance().getProductsOfSpecFlight(chooseFlight.getValue());
		ObservableList<ProductInFlight> productInFlightList = FXCollections.observableArrayList(pie);
		productOfFlightTable.setItems(productInFlightList);
		
		productOfFlightTable.setVisible(true);
		
		addProductPane.setVisible(true);
		
		chooseProduct.getItems().addAll(ProductsAndSupplierControl.getInstance().getAllProducts());
    }
    
	
    @FXML
    void addProductToFlight(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String flightID = chooseFlight.getValue();
    	EntertainmentProduct chosenProduct = chooseProduct.getValue();
    	
    	if (chosenProduct == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please choose product");
			alert.show();
    		return;
    	}
    	
    	String productID = chosenProduct.getProductID();
    	
    	for (ProductInFlight pif : pie)
    		if (pif.getProductID().equals(productID)) {
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Product Error");
    			alert.setHeaderText("This product is already exist in this flight");
    			alert.show();
        		return;
    		}
    	
    	String supplierID = ProductsAndSupplierControl.getInstance().getSupplierIdOfSpecProduct(productID);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Product to flight");
		alert.setHeaderText("Are you sure you want to add: " + productID + " to flight " + flightID +"?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			
			ProductsAndSupplierControl.getInstance().addProductInFlightToDB(flightID, productID, supplierID, feedbackCol.getText());
			
			switchTo_ProductsInFlights(event);
		}

    }
    
    @FXML
    void switchTo_ProductsAndSupplierScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ProductsAndSupplierScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager - Products AndS upplier Screen");
		 stage.show();
    }

    @FXML
    void switchTo_ProductsInFlights(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ProductsInFlights.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager - Products In Flights");
		 stage.show();
    }




	

}
