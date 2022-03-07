package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ShibutzGroundShiftControl;
import entity.GroundAttendant;
import entity.GroundAttendantInShift;
import entity.Shift;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShibutzGroundShift implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TableView<GroundAttendant> groundAttendantsTable;

    @FXML
    private TableColumn<GroundAttendant, String> attendantIdCol;

    @FXML
    private TableColumn<GroundAttendant, String> attendantFirstNameCol;

    @FXML
    private TableColumn<GroundAttendant, String> attendantLastNameCol;

    @FXML
    private TableColumn<GroundAttendant, LocalDate> attendantStartWorkDateCol;

    @FXML
    private TableColumn<GroundAttendant, LocalDate> attendantEndWorkDateCol;

    @FXML
    private TableView<Shift> shiftTable;

    @FXML
    private TableColumn<Shift, String> shiftIdCol;

    @FXML
    private TableColumn<Shift, LocalDate> startDateCol;

    @FXML
    private TableColumn<Shift, LocalTime> startHourCol;

    @FXML
    private TableColumn<Shift, LocalDate> endDateCol;

    @FXML
    private TableColumn<Shift, LocalTime> endHourCol;

    
    @FXML
    private ComboBox<String> chooseShift;

    @FXML
    private ListView<String> gaInShiftList;

    @FXML
    private ComboBox<String> chooseGA4Adding;
    
    @FXML
    private ComboBox<String> chooseRole4Adding;

    
    ArrayList<GroundAttendant> allGroundAttendant;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Ground Attendants table
		attendantIdCol.setCellValueFactory(new PropertyValueFactory<GroundAttendant, String>("exployeeID"));
		attendantFirstNameCol.setCellValueFactory(new PropertyValueFactory<GroundAttendant, String>("firstName"));
		attendantLastNameCol.setCellValueFactory(new PropertyValueFactory<GroundAttendant, String>("lastName"));
		attendantStartWorkDateCol.setCellValueFactory(new PropertyValueFactory<GroundAttendant, LocalDate>("startWorkDate"));
		attendantEndWorkDateCol.setCellValueFactory(new PropertyValueFactory<GroundAttendant, LocalDate>("endWorkDate"));
		
		allGroundAttendant = ShibutzGroundShiftControl.getInstance().getAllGroundAttendants();
		ObservableList<GroundAttendant> flightAttendantList = FXCollections.observableArrayList(allGroundAttendant);
		groundAttendantsTable.setItems(flightAttendantList);
		
		
		// Shift table
		shiftIdCol.setCellValueFactory(new PropertyValueFactory<Shift, String>("shiftID"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<Shift, LocalDate>("startDate"));
		startHourCol.setCellValueFactory(new PropertyValueFactory<Shift, LocalTime>("startHour"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<Shift, LocalDate>("endDate"));
		endHourCol.setCellValueFactory(new PropertyValueFactory<Shift, LocalTime>("endHour"));
		
		ObservableList<Shift> shiftList = FXCollections.observableArrayList(ShibutzGroundShiftControl.getInstance().getAllShifts());
		shiftTable.setItems(shiftList);

		
		
		// Choose Flight
		chooseShift.getItems().addAll(ShibutzGroundShiftControl.getInstance().getAllShiftIDs());

		// flightAttendants combo box
		chooseGA4Adding.getItems().clear();
		for (GroundAttendant ga : allGroundAttendant) {
			chooseGA4Adding.getItems().add(ga.getExployeeID());
		}
		
		// Role ComboBox
		chooseRole4Adding.getItems().clear();
		chooseRole4Adding.getItems().add("checkFlightTickets");
		chooseRole4Adding.getItems().add("seatsAllocation");
		chooseRole4Adding.getItems().add("registerSendBelongings");
		
    	gaIDsInSpecShift = ShibutzGroundShiftControl.getInstance().getGroundAttendantInShiftID(chooseShift.getValue());

	}

	ArrayList<String> gaIdRoleInSpecShift;
	@FXML
    void showCrewShift(ActionEvent event) {
		gaInShiftList.getItems().clear();
		gaIdRoleInSpecShift = ShibutzGroundShiftControl.getInstance().getGroundAttendantInShiftIdRole(chooseShift.getValue());
    	gaInShiftList.getItems().addAll(gaIdRoleInSpecShift);
    	gaInShiftList.refresh();

    }

	ArrayList<String> gaIDsInSpecShift;
    @FXML
    void addGAtoShift(ActionEvent event)throws ClassNotFoundException, SQLException {
    	if (chooseShift.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please select Shift before");
			alert.showAndWait();
			return;
    	}
    	if (chooseGA4Adding.getValue() == null || chooseRole4Adding.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please choose ground attendant and role");
			alert.showAndWait();
			return;
    	}
    	if (gaIDsInSpecShift.contains(chooseGA4Adding.getValue())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("This ground attendant is already exist in shift " + chooseShift.getValue());
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Ground Attendant to shift");
		alert.setHeaderText("Are you sure you want to add: " + chooseGA4Adding.getValue() + " for shift: " + chooseShift.getValue());
		if (alert.showAndWait().get() == ButtonType.OK) {
			GroundAttendantInShift gaInShift = new GroundAttendantInShift(chooseShift.getValue(), chooseGA4Adding.getValue(), chooseRole4Adding.getValue());
			ShibutzGroundShiftControl.getInstance().addGAinShiftToDB(gaInShift);
			showCrewShift(event);
		}
    }
    

    @FXML
    void removeGAfromShift(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String chosenGA = gaInShiftList.getSelectionModel().getSelectedItem();
    	
    	if (chosenGA == null || chooseShift.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please choose flight and flight attendant");
			alert.showAndWait();
			return;
    	}
    	chosenGA = chosenGA.substring(14, chosenGA.indexOf(','));

    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Ground Attendant from shift");
		alert.setHeaderText("Are you sure you want to remove: " + chosenGA + " from shift: " + chooseShift.getValue());
		if (alert.showAndWait().get() == ButtonType.OK) {
			ShibutzGroundShiftControl.getInstance().removeGAfromShiftIN_DB(chooseShift.getValue(), chosenGA);
			showCrewShift(event);
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


    


}
