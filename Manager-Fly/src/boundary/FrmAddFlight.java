package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;


import control.AddFlightControl;
import entity.Airport;
import entity.Flight;
import entity.FlightStatus;
import entity.Plane;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FrmAddFlight implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private DatePicker landingDate1;

    @FXML
    private TextField flightID;

    @FXML
    private ComboBox<Airport> departureAirport;

    @FXML
    private ComboBox<Airport> landingAirport;

    @FXML
    private DatePicker departureDate;

    @FXML
    private ComboBox<LocalTime> departureHour;

    @FXML
    private ComboBox<LocalTime> landingHour;

    @FXML
    private ComboBox<Plane> plane;
    
    @FXML
    private AnchorPane pane;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pane.setVisible(false);
		for (int i=0; i<24; i++) {
			departureHour.getItems().add(LocalTime.of(i, 0));
			departureHour.getItems().add(LocalTime.of(i, 30));
			landingHour.getItems().add(LocalTime.of(i, 0));
			landingHour.getItems().add(LocalTime.of(i, 30));
		}
		
	}
	
	@FXML
	public void showPane(ActionEvent event) {
		
		departureAirport.getItems().clear();
		landingAirport.getItems().clear();
		plane.getItems().clear();
		
		LocalDate departureDate1 = departureDate.getValue();
		LocalTime departureHour1 = departureHour.getValue();
		LocalDate landingDate = landingDate1.getValue();
		LocalTime landingHour1 = landingHour.getValue();
		
		if (departureDate1 == null || departureHour1==null || landingDate==null || landingHour1==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
			pane.setVisible(false);
    		return;
		}
		
		if (landingDate.equals(departureDate1) && !landingHour1.isAfter(departureHour1)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illgeal time");
			alert.setHeaderText("if the landing and the departure is in same day, the landing hour muse br after departure hour");
			alert.show();
			pane.setVisible(false);
    		return;
		}


		if (departureDate1.toEpochDay() - LocalDate.now().toEpochDay() < 60 || !(departureDate1.isBefore(landingDate) || landingDate.equals(departureDate1)) ) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal date");
			alert.setHeaderText("Difference must be 2 months at least, and landing date after departure");
			alert.show();
			pane.setVisible(false);
    		return;
		}
		
		// Filter for relevant
		ArrayList<Airport> allAirports = AddFlightControl.getInstance().getAllAirports();
		System.out.println(allAirports);
		departureAirport.getItems().addAll(AddFlightControl.getInstance().getRelevantAirports(departureDate1, departureHour1, "dep"));
		landingAirport.getItems().addAll(AddFlightControl.getInstance().getRelevantAirports(landingDate, landingHour1, "land"));
		
		//-- Change all planes to relevant
		plane.getItems().addAll(AddFlightControl.getInstance().getRelevantPlanes(departureDate1, landingDate));
		pane.setVisible(true);

	}

    
    @FXML
    public void addFlight(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String id1 = flightID.getText();
		LocalDate departureDate1 = departureDate.getValue();
		LocalTime departureHour1 = departureHour.getValue();
		LocalDate landingDate = landingDate1.getValue();
		LocalTime landingHour1 = landingHour.getValue();

    	Airport depAirport1 = departureAirport.getValue();
//    	Airport da = (Airport) departureAirport.getSelectionModel().getSelectedItem();
    	Airport landAirport1 = landingAirport.getValue();
    	Plane plane1 = plane.getValue();
    	
		
		
		if (id1==null || id1.equals("") || departureDate1 == null || departureHour1==null || landingDate==null || landingHour1==null || depAirport1==null || landAirport1==null || plane1==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
		}
		
		// Check validation input of dates
		if (landingDate.equals(departureDate1) && !landingHour1.isAfter(departureHour1)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illgeal time");
			alert.setHeaderText("if the landing and the departure is in same day, the landing hour muse br after departure hour");
			alert.show();
    		return;
		}
		if (departureDate1.toEpochDay() - LocalDate.now().toEpochDay() < 60 || !(departureDate1.isBefore(landingDate) || landingDate.equals(departureDate1)) ) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal date");
			alert.setHeaderText("Difference must be 2 months at least, and landing date after departure");
			alert.show();
    		return;
		}
		
		// Check not same airport
		if (depAirport1.getAirportID().equals(landAirport1.getAirportID())){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Illegal Input - Airport");
			alert.setHeaderText("It's not possible to choose same airport to destination and landing");
			alert.show();
    		return;

		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Flight");
		alert.setHeaderText("Are you sure you want to add: " + id1);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	Flight flight = new Flight(id1, depAirport1, landAirport1, plane1, departureDate1, departureHour1, landingDate, landingHour1, FlightStatus.onTime);
	    	System.out.println("added flight to java");

	    	AddFlightControl.getInstance().addFlightToDB(flight);
	    	System.out.println("Added flight to DB");
	    	switchTo_FlightManagerScreen(event);
		}

    }
    

	@FXML
    void switchTo_FlightManagerScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/FlightManagerScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager Screen");
		 stage.show();
    }
	
	@FXML
    void switchTo_AddAirport(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/AddAirport.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Add Airport");
		 stage.show();
    }
	
	@FXML
    void switchTo_AddPlane(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/AddPlane.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Add Plane");
		 stage.show();
    }


	
	@FXML
    void refresh(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/FrmAddFlight.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Add Flight");
		 stage.show();
    }

}
