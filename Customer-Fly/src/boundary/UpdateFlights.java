package boundary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ImportControl;
import control.UpdateFlightControl;
import entity.Flight;
import entity.FlightStatus;
import entity.Seat;
import entity.SeatClass;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UpdateFlights implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;


	// Table
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

    // End Table
    
    @FXML
    private ComboBox<Flight> relevantFlights;
    
    // Seats Table
    @FXML
    private TableView<Seat> seatsTable;

    @FXML
    private TableColumn<Seat, Integer> rowNumCol;

    @FXML
    private TableColumn<Seat, Integer> searNumCol;

    @FXML
    private TableColumn<Seat, SeatClass> seatClassCol;

    // End Seats Table
    
    @FXML
    private Label planeLabel;
    
	ArrayList<Seat> seatsFromJson;
	ArrayList<Flight> updatedFlightsList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updatedFlightsList = ImportControl.getInstance().importFlightsFromJson();
		System.out.println(updatedFlightsList);
		
		// Flights table
		flightNumberCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightNumber"));
		departureTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureDate"));
		departureAirportCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureAirportId"));
		landingTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingDate"));
		landingAirportCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("landingAirportId"));
		flightStatusCol.setCellValueFactory(new PropertyValueFactory<Flight, FlightStatus>("flightStatus"));

		
		ObservableList<Flight> flightsList = FXCollections.observableArrayList(updatedFlightsList);
		flightTable.setItems(flightsList);
		
		// End Table
		
		relevantFlights.getItems().clear();
		relevantFlights.getItems().addAll(flightsList);
		
		// Seats
		seatsFromJson = ImportControl.getInstance().importSeatsFromJson();


		
	}
	
	
	@FXML
    void showSeats(ActionEvent event) {
		Flight tempFlight = relevantFlights.getValue();
		
		if (tempFlight == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please choose flight");
			alert.show();
    		return;
		}
		
		// Seats table
		rowNumCol.setCellValueFactory(new PropertyValueFactory<Seat, Integer>("rowNumber"));
		searNumCol.setCellValueFactory(new PropertyValueFactory<Seat, Integer>("seatNumber"));
		seatClassCol.setCellValueFactory(new PropertyValueFactory<Seat, SeatClass>("seatClass"));
		
		ObservableList<Seat> seatsList = FXCollections.observableArrayList(UpdateFlightControl.getInstance().getSeatsByPlane(seatsFromJson, tempFlight.getTailNumber()));
		seatsTable.setItems(seatsList);
		
		seatsTable.refresh();
		planeLabel.setText("Plane: " + tempFlight.getTailNumber());
		// End Table	

    }
	
	
	
//	@FXML
//    void switchTo_MainMenu(ActionEvent event) throws IOException {
//		 root = FXMLLoader.load(getClass().getResource("/boundary/MainMenu.fxml"));
//		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		 scene = new Scene(root);
//		 stage.setScene(scene);
//		 stage.setTitle("Customer Fly");
//		 stage.show();
//    }
	
    @FXML
    void switchTo_Login(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/LoginScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Login Screen");
		 stage.show();
    }

	
	@FXML
    void switchTo_CancelOrders(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/CancelOrders.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Customer Fly");
		 stage.show();
    }
	
	@FXML
    void switchTo_ChangePriceParameters(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ChangePriceParameters.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Customer Fly - Change Price Parameters");
		 stage.show();
    }



}
