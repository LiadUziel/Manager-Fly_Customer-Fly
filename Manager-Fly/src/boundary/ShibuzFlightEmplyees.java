package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ShibuzFlightEmployeesControl;
import entity.FlightAttendant;
import entity.FlightAttendantInFlight;
import entity.Pilot;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShibuzFlightEmplyees implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;

	// Start flightAttendantsTable
    @FXML
    private TableView<FlightAttendant> flightAttendantsTable;

    @FXML
    private TableColumn<FlightAttendant, String> attendantIdCol;

    @FXML
    private TableColumn<FlightAttendant, String> attendantFirstNameCol;

    @FXML
    private TableColumn<FlightAttendant, String> attendantLastNameCol;

    @FXML
    private TableColumn<FlightAttendant, LocalDate> attendantStartWorkDateCol;

    @FXML
    private TableColumn<FlightAttendant, LocalDate> attendantEndWorkDateCol;
    // end flightAttendantsTable
    
    // start pilotsTable
    @FXML
    private TableView<Pilot> pilotsTable;

    @FXML
    private TableColumn<Pilot, String> pilotIdCol;

    @FXML
    private TableColumn<Pilot, String> pilotFirstNameCol;

    @FXML
    private TableColumn<Pilot, String> pilotLastNameCol;

    @FXML
    private TableColumn<Pilot, LocalDate> pilotStartWorkDateCol;

    @FXML
    private TableColumn<Pilot, LocalDate> pilotEndWorkDateCol;

    @FXML
    private TableColumn<Pilot, String> pilotLicenceNumberCol;

    @FXML
    private TableColumn<Pilot, LocalDate> pilotLicenceIssueDateCol;
    // end pilotsTable
    

    @FXML
    private ComboBox<String> chooseFlight;

    @FXML
    private ListView<String> faInFlightList;

    @FXML
    private Label chiefPilotLabel;

    @FXML
    private Label coPilotLabel;


    @FXML
    private ComboBox<String> chooseFA4Adding;
    
    @FXML
    private ComboBox<String> chooseChiefPilot;

    @FXML
    private ComboBox<String> chooseCoPilot;
	
	ArrayList<FlightAttendant> allFlightAttendants;
	ArrayList<Pilot> allPilots;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Flight Attendants table
		attendantIdCol.setCellValueFactory(new PropertyValueFactory<FlightAttendant, String>("exployeeID"));
		attendantFirstNameCol.setCellValueFactory(new PropertyValueFactory<FlightAttendant, String>("firstName"));
		attendantLastNameCol.setCellValueFactory(new PropertyValueFactory<FlightAttendant, String>("lastName"));
		attendantStartWorkDateCol.setCellValueFactory(new PropertyValueFactory<FlightAttendant, LocalDate>("startWorkDate"));
		attendantEndWorkDateCol.setCellValueFactory(new PropertyValueFactory<FlightAttendant, LocalDate>("endWorkDate"));
		
		allFlightAttendants = ShibuzFlightEmployeesControl.getInstance().getAllFlightAttendants();
		ObservableList<FlightAttendant> flightAttendantList = FXCollections.observableArrayList(allFlightAttendants);
		flightAttendantsTable.setItems(flightAttendantList);
		
		
		// Pilots Table
		pilotIdCol.setCellValueFactory(new PropertyValueFactory<Pilot, String>("exployeeID"));
		pilotFirstNameCol.setCellValueFactory(new PropertyValueFactory<Pilot, String>("firstName"));
		pilotLastNameCol.setCellValueFactory(new PropertyValueFactory<Pilot, String>("lastName"));
		pilotStartWorkDateCol.setCellValueFactory(new PropertyValueFactory<Pilot, LocalDate>("startWorkDate"));
		pilotEndWorkDateCol.setCellValueFactory(new PropertyValueFactory<Pilot, LocalDate>("endWorkDate"));
		pilotLicenceNumberCol.setCellValueFactory(new PropertyValueFactory<Pilot, String>("licenceNumber"));
		pilotLicenceIssueDateCol.setCellValueFactory(new PropertyValueFactory<Pilot, LocalDate>("licenceIssueDate"));

		allPilots = ShibuzFlightEmployeesControl.getInstance().getAllPilots();
		ObservableList<Pilot> pilotList = FXCollections.observableArrayList(allPilots);
		pilotsTable.setItems(pilotList);

		// Choose Flight
		chooseFlight.getItems().addAll(ShibuzFlightEmployeesControl.getInstance().getAllFlightIDs());
		
		// flightAttendants combo box
		chooseFA4Adding.getItems().clear();
		for (FlightAttendant fa : allFlightAttendants) {
			chooseFA4Adding.getItems().add(fa.getExployeeID());
		}
		
		// pilots comboBox
		chooseChiefPilot.getItems().clear();
		chooseCoPilot.getItems().clear();
		for (Pilot pilot : allPilots) {
			chooseChiefPilot.getItems().add(pilot.getExployeeID());
			chooseCoPilot.getItems().add(pilot.getExployeeID());
		}
		
	}
	
	

	ArrayList<String> faIDsInSpecFlight;
	ArrayList<String> pilotsIDsInSpecFlight; // Chief in index 0, coPilot in index 1

    @FXML
    void showCrewFlight(ActionEvent event) {
//    	ArrayList<FlightAttendant> flightAttendantInFlight = new ArrayList<>();
    	faInFlightList.getItems().clear();
    	faIDsInSpecFlight = ShibuzFlightEmployeesControl.getInstance().getFlightAttendantInFlightID(chooseFlight.getValue());
    	faInFlightList.getItems().addAll(faIDsInSpecFlight);
    	faInFlightList.refresh();

    	// Chief in index 0, coPilot in index 1
    	pilotsIDsInSpecFlight = ShibuzFlightEmployeesControl.getInstance().getPilotsInFlightID(chooseFlight.getValue());
    	chiefPilotLabel.setText("Chief pilot: " + pilotsIDsInSpecFlight.get(0));
    	coPilotLabel.setText("Co-pilot: " + pilotsIDsInSpecFlight.get(1));
    	
    	
    }
	
    @FXML
    void addFAtoFlight(ActionEvent event) throws ClassNotFoundException, SQLException {
    	if (chooseFlight.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please select flight before");
			alert.showAndWait();
			return;
    	}
    	if (faIDsInSpecFlight.contains(chooseFA4Adding.getValue())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("This flight attendant is already exist in flight " + chooseFlight.getValue());
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Flight Attendant to flight");
		alert.setHeaderText("Are you sure you want to add: " + chooseFA4Adding.getValue() + " for flight: " + chooseFlight.getValue());
		if (alert.showAndWait().get() == ButtonType.OK) {
			FlightAttendantInFlight faInFlight = new FlightAttendantInFlight(chooseFlight.getValue(), chooseFA4Adding.getValue());
			ShibuzFlightEmployeesControl.getInstance().addFAinFlightToDB(faInFlight);
			showCrewFlight(event);
		}
    }
    
    @FXML
    void removeFAfromFlight(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String chosenFA = faInFlightList.getSelectionModel().getSelectedItem();
    	if (chosenFA == null || chooseFlight.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please choose flight and flight attendant");
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Flight Attendant from flight");
		alert.setHeaderText("Are you sure you want to remove: " + chosenFA + " from flight: " + chooseFlight.getValue());
		if (alert.showAndWait().get() == ButtonType.OK) {
			ShibuzFlightEmployeesControl.getInstance().removeFAfromFlightIN_DB(chooseFlight.getValue(), chosenFA);
			showCrewFlight(event);
		}
    }
    
    @FXML
    void changeChiefPilot(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	if (chooseFlight.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please select flight before");
			alert.showAndWait();
			return;
    	}
    	
    	if (chooseChiefPilot.getValue().equals(pilotsIDsInSpecFlight.get(0))) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("You chose same chief pilot");
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Change Chief pilot in flight");
		alert.setHeaderText("Are you sure that chief pilot in flight: " + chooseFlight.getValue() + " will be:  " + chooseChiefPilot.getValue());
		if (alert.showAndWait().get() == ButtonType.OK) {
			ShibuzFlightEmployeesControl.getInstance().updateChiefPilotInFlight_InDB(chooseChiefPilot.getValue(), chooseFlight.getValue());
			
			// chief pilot is the current co pilot - then co pilot will be empty
			if (chooseChiefPilot.getValue().equals(pilotsIDsInSpecFlight.get(1)))
				ShibuzFlightEmployeesControl.getInstance().updateCoPilotInFlight_InDB(null, chooseFlight.getValue());
			showCrewFlight(event);
		}
    }

    @FXML
    void changeCoPilot(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	if (chooseFlight.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please select flight before");
			alert.showAndWait();
			return;
    	}
    	
    	if (chooseCoPilot.getValue().equals(pilotsIDsInSpecFlight.get(1))) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("You chose same co pilot");
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Change Chief pilot in flight");
		alert.setHeaderText("Are you sure that co pilot in flight: " + chooseFlight.getValue() + " will be:  " + chooseCoPilot.getValue());
		if (alert.showAndWait().get() == ButtonType.OK) {
			ShibuzFlightEmployeesControl.getInstance().updateCoPilotInFlight_InDB(chooseCoPilot.getValue(), chooseFlight.getValue());
			
			// co pilot is the current chief pilot - then chief pilot will be empty
			if (chooseCoPilot.getValue().equals(pilotsIDsInSpecFlight.get(0)))
				ShibuzFlightEmployeesControl.getInstance().updateChiefPilotInFlight_InDB(null, chooseFlight.getValue());
			showCrewFlight(event);
		}
    }

	@FXML
    void switchTo_EmployeesManagerScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/EmployeesManagerScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Employees Manager Screen");
		 stage.show();
    }
	
	@FXML
    void switchTo_ShibutzGroundShiftScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ShibutzGroundShift.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Ground Shift Screen");
		 stage.show();
    }



}
