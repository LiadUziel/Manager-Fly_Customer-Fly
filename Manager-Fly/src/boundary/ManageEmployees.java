package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ManagerEmployeesControl;
import entity.FlightAttendant;
import entity.GroundAttendant;
import entity.Pilot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManageEmployees implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private AnchorPane faPane;
    @FXML
    private AnchorPane addPane;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private TextField firstNameTxt;

    @FXML
    private DatePicker startWorkDateTxt;

    @FXML
    private DatePicker endWorkDateTxt;

    @FXML
    private Button submitAddFABtn;

    @FXML
    private ComboBox<FlightAttendant> chooseFA;
    
    // For Ground Attendants
    @FXML
    private Button submitEditFABtn;

    @FXML
    private Button submitAddGABtn;

    @FXML
    private Button submitEditGABtn;
    
    @FXML
    private AnchorPane gaPane;

    @FXML
    private ComboBox<GroundAttendant> chooseGA;

    
    // For pilots
    @FXML
    private Button submitAddPilotBtn;

    @FXML
    private Button submitEditPilotBtn;
    
    @FXML
    private AnchorPane pilotPane;
    
    @FXML
    private ComboBox<Pilot> choosePilot;
    
    @FXML
    private Label licenceNumLabel;
    @FXML
    private TextField licenceNumTxt;
    
    @FXML
    private Label licenceDateLabel;
    @FXML
    private DatePicker licenceDateTxt;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		faPane.setVisible(false);
		addPane.setVisible(false);
		chooseFA.setVisible(false);
		
		chooseFA.getItems().addAll(ManagerEmployeesControl.getInstance().getAllFlightAttendants());
		
		gaPane.setVisible(false);
		chooseGA.setVisible(false);
		chooseGA.getItems().addAll(ManagerEmployeesControl.getInstance().getAllGroundAttendants());

		pilotPane.setVisible(false);
		choosePilot.setVisible(false);
		choosePilot.getItems().addAll(ManagerEmployeesControl.getInstance().getAllPilots());
		
	}


    @FXML
    void addFlightAttendant(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String id = idTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	LocalDate startWorkDate = startWorkDateTxt.getValue();
    	LocalDate endWorkDate = endWorkDateTxt.getValue();
    	
    	if (id == null || id.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || startWorkDate == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
    	ArrayList<String> flightAttendants_ids = ManagerEmployeesControl.getInstance().getAllFA_IDs();
    	if (flightAttendants_ids.contains(id)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ID error");
			alert.setHeaderText("This id already exists");
			alert.show();
    		return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Flight attendant");
		alert.setHeaderText("Are you sure you want to add: " + firstName + " " + lastName);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	FlightAttendant fa = new FlightAttendant(id, firstName, lastName, startWorkDate, endWorkDate);

	    	ManagerEmployeesControl.getInstance().addFlightAttendantToDB(fa);

	    	switchTo_ManageEmployees(event);
		}
    }
    
    @FXML
    void addGroundAttendant(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

    	String id = idTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	LocalDate startWorkDate = startWorkDateTxt.getValue();
    	LocalDate endWorkDate = endWorkDateTxt.getValue();
    	
    	if (id == null || id.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || startWorkDate == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
    	ArrayList<String> groundAttendants_ids = ManagerEmployeesControl.getInstance().getAllGA_IDs();
    	if (groundAttendants_ids.contains(id)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ID error");
			alert.setHeaderText("This id already exists");
			alert.show();
    		return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Ground attendant");
		alert.setHeaderText("Are you sure you want to add: " + firstName + " " + lastName);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	GroundAttendant ga = new GroundAttendant(id, firstName, lastName, startWorkDate, endWorkDate);

	    	ManagerEmployeesControl.getInstance().addGroundAttendantToDB(ga);

	    	switchTo_ManageEmployees(event);
		}
    
    }
    
    @FXML
    void addPilot(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

    	String id = idTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	LocalDate startWorkDate = startWorkDateTxt.getValue();
    	LocalDate endWorkDate = endWorkDateTxt.getValue();
    	String licenceNumber = licenceNumTxt.getText();
    	LocalDate licenceDate = licenceDateTxt.getValue();
    	
    	if (id == null || id.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || startWorkDate == null || licenceNumber == null || licenceNumber.equals("") || licenceDate == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
    	ArrayList<String> pilots_ids = ManagerEmployeesControl.getInstance().getAllPilot_IDs();
    	if (pilots_ids.contains(id)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ID error");
			alert.setHeaderText("This id already exists");
			alert.show();
    		return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Pilot");
		alert.setHeaderText("Are you sure you want to add: " + firstName + " " + lastName);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	Pilot pilot = new Pilot(id, firstName, lastName, startWorkDate, endWorkDate, licenceNumber, licenceDate);

	    	ManagerEmployeesControl.getInstance().addPilotToDB(pilot);

	    	switchTo_ManageEmployees(event);
		}
    
    }
    

    @FXML
    void editFlightAttendant(ActionEvent event) {
    	FlightAttendant tempFA = chooseFA.getValue();
    	idTxt.setText(tempFA.getExployeeID());
    	idTxt.setDisable(true);
    	firstNameTxt.setText(tempFA.getFirstName());
    	lastNameTxt.setText(tempFA.getLastName());
    	startWorkDateTxt.setValue(tempFA.getStartWorkDate());
    	endWorkDateTxt.setValue(tempFA.getEndWorkDate());
    	
    	addPane.setVisible(true);
    	// For pilots
    	licenceNumLabel.setVisible(false);
    	licenceNumTxt.setVisible(false);
    	licenceDateTxt.setVisible(false);
    	licenceDateLabel.setVisible(false);
    	
    	submitAddFABtn.setVisible(false);
    	submitEditFABtn.setVisible(true);
    	submitAddGABtn.setVisible(false);
    	submitEditGABtn.setVisible(false);
    	submitAddPilotBtn.setVisible(false);
    	submitEditPilotBtn.setVisible(false);

    }
    @FXML
    void editGroundAttendant(ActionEvent event) {

    	GroundAttendant tempGA = chooseGA.getValue();
    	idTxt.setText(tempGA.getExployeeID());
    	idTxt.setDisable(true);
    	firstNameTxt.setText(tempGA.getFirstName());
    	lastNameTxt.setText(tempGA.getLastName());
    	startWorkDateTxt.setValue(tempGA.getStartWorkDate());
    	endWorkDateTxt.setValue(tempGA.getEndWorkDate());
    	
    	addPane.setVisible(true);
    	// For pilots
    	licenceNumLabel.setVisible(false);
    	licenceNumTxt.setVisible(false);
    	licenceDateTxt.setVisible(false);
    	licenceDateLabel.setVisible(false);
    	
    	submitAddFABtn.setVisible(false);
    	submitEditFABtn.setVisible(false);
    	submitAddGABtn.setVisible(false);
    	submitEditGABtn.setVisible(true);
    	submitAddPilotBtn.setVisible(false);
    	submitEditPilotBtn.setVisible(false);

    }
    @FXML
    void editPilot(ActionEvent event) {
    	Pilot tempPilot = choosePilot.getValue();
    	idTxt.setText(tempPilot.getExployeeID());
    	idTxt.setDisable(true);
    	firstNameTxt.setText(tempPilot.getFirstName());
    	lastNameTxt.setText(tempPilot.getLastName());
    	startWorkDateTxt.setValue(tempPilot.getStartWorkDate());
    	endWorkDateTxt.setValue(tempPilot.getEndWorkDate());
    	licenceNumTxt.setText(tempPilot.getLicenceNumber());
    	licenceDateTxt.setValue(tempPilot.getLicenceIssueDate());
    	
    	addPane.setVisible(true);
    	// For pilots
//    	licenceNumLabel.setVisible(false);
//    	licenceNumTxt.setVisible(false);
//    	licenceDateTxt.setVisible(false);
//    	licenceDateLabel.setVisible(false);
    	
    	submitAddFABtn.setVisible(false);
    	submitEditFABtn.setVisible(false);
    	submitAddGABtn.setVisible(false);
    	submitEditGABtn.setVisible(false);
    	submitAddPilotBtn.setVisible(false);
    	submitEditPilotBtn.setVisible(true);

    }
    
    @FXML
    void submitEditFlightAttendant(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String id = idTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	LocalDate startWorkDate = startWorkDateTxt.getValue();
    	LocalDate endWorkDate = endWorkDateTxt.getValue();
    	
    	if (id == null || id.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || startWorkDate == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
//    	ArrayList<String> flightAttendants_ids = ManagerEmployeesControl.getInstance().getAllFA_IDs();
//    	if (flightAttendants_ids.contains(id)) {
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setTitle("ID error");
//			alert.setHeaderText("This id already exists");
//			alert.show();
//    		return;
//    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Edit Flight attendant");
		alert.setHeaderText("Are you sure you want to edit: " + firstName + " " + lastName);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	FlightAttendant fa = new FlightAttendant(id, firstName, lastName, startWorkDate, endWorkDate);

	    	ManagerEmployeesControl.getInstance().editFlightAttendantInDB(fa);

	    	switchTo_ManageEmployees(event);
		}
    
    }
    @FXML
    void submitEditGroundAttendant(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {

    	String id = idTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	LocalDate startWorkDate = startWorkDateTxt.getValue();
    	LocalDate endWorkDate = endWorkDateTxt.getValue();
    	
    	if (id == null || id.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || startWorkDate == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
//    	ArrayList<String> flightAttendants_ids = ManagerEmployeesControl.getInstance().getAllFA_IDs();
//    	if (flightAttendants_ids.contains(id)) {
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setTitle("ID error");
//			alert.setHeaderText("This id already exists");
//			alert.show();
//    		return;
//    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Edit Ground attendant");
		alert.setHeaderText("Are you sure you want to edit: " + firstName + " " + lastName);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	GroundAttendant ga = new GroundAttendant(id, firstName, lastName, startWorkDate, endWorkDate);

	    	ManagerEmployeesControl.getInstance().editGroundAttendantInDB(ga);

	    	switchTo_ManageEmployees(event);
		}

    }
    @FXML
    void submitEditPilot(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {


    	String id = idTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	LocalDate startWorkDate = startWorkDateTxt.getValue();
    	LocalDate endWorkDate = endWorkDateTxt.getValue();
    	String licenceNumber = licenceNumTxt.getText();
    	LocalDate licenceDate = licenceDateTxt.getValue();
    	
    	if (id == null || id.equals("") || firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || startWorkDate == null || licenceNumber == null || licenceNumber.equals("") || licenceDate == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
//    	ArrayList<String> pilots_ids = ManagerEmployeesControl.getInstance().getAllPilot_IDs();
//    	if (pilots_ids.contains(id)) {
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setTitle("ID error");
//			alert.setHeaderText("This id already exists");
//			alert.show();
//    		return;
//    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Pilot");
		alert.setHeaderText("Are you sure you want to add: " + firstName + " " + lastName);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	Pilot pilot = new Pilot(id, firstName, lastName, startWorkDate, endWorkDate, licenceNumber, licenceDate);

	    	ManagerEmployeesControl.getInstance().addPilotToDB(pilot);

	    	switchTo_ManageEmployees(event);
		}
    
    
    }

    @FXML
    void switchTo_ManageEmployees(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ManageEmployees.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Manage Employees");
		 stage.show();
    }
    


    @FXML
    void showFaPane(ActionEvent event) {
    	faPane.setVisible(true);
    	gaPane.setVisible(false);
    	pilotPane.setVisible(false);
    	
    	chooseFA.setVisible(false);
    	chooseGA.setVisible(false);
    	choosePilot.setVisible(false);
    	
    	addPane.setVisible(false);
    }
    @FXML
    void showGaPane(ActionEvent event) {
    	gaPane.setVisible(true);
    	faPane.setVisible(false);
    	pilotPane.setVisible(false);
    	
    	chooseFA.setVisible(false);
    	chooseGA.setVisible(false);
    	choosePilot.setVisible(false);
    	
    	addPane.setVisible(false);

    }
    @FXML
    void showPilotPane(ActionEvent event) {
    	faPane.setVisible(false);
    	gaPane.setVisible(false);
    	pilotPane.setVisible(true);
    	
    	chooseFA.setVisible(false);
    	chooseGA.setVisible(false);
    	choosePilot.setVisible(false);

    	addPane.setVisible(false);

    }
    
    @FXML
    void showAddPane(ActionEvent event) {
    	idTxt.clear();
    	idTxt.setDisable(false);
    	firstNameTxt.clear();
    	lastNameTxt.clear();
    	startWorkDateTxt.setValue(null);
    	endWorkDateTxt.setValue(null);
    	addPane.setVisible(true);
    	
    	chooseFA.setVisible(false);
    	chooseGA.setVisible(false);
    	
    	// For pilots
    	licenceNumLabel.setVisible(false);
    	licenceNumTxt.setVisible(false);
    	licenceDateTxt.setVisible(false);
    	licenceDateLabel.setVisible(false);
    }
    @FXML
    void showAddPaneFA(ActionEvent event) {
    	showAddPane(event);
    	submitAddGABtn.setVisible(false);
    	submitEditGABtn.setVisible(false);
    	submitAddFABtn.setVisible(true);
    	submitEditFABtn.setVisible(false);
    	submitAddPilotBtn.setVisible(false);
    	submitEditPilotBtn.setVisible(false);
    }
    @FXML
    void showAddPaneGA(ActionEvent event) {
    	showAddPane(event);
    	submitAddGABtn.setVisible(true);
    	submitEditGABtn.setVisible(false);
    	submitAddFABtn.setVisible(false);
    	submitEditFABtn.setVisible(false);
    	submitAddPilotBtn.setVisible(false);
    	submitEditPilotBtn.setVisible(false);

    }
    @FXML
    void showAddPanePilot(ActionEvent event) {
    	showAddPane(event);
    	submitAddGABtn.setVisible(false);
    	submitEditGABtn.setVisible(false);
    	submitAddFABtn.setVisible(false);
    	submitEditFABtn.setVisible(false);
    	submitAddPilotBtn.setVisible(true);
    	submitEditPilotBtn.setVisible(false);
    	
    	choosePilot.setVisible(false);
    	licenceNumLabel.setVisible(true);
    	licenceNumTxt.setVisible(true);
    	licenceNumTxt.clear();
    	licenceDateLabel.setVisible(true);
    	licenceDateTxt.setVisible(true);
    	licenceDateTxt.setValue(null);
    }
    
    @FXML
    void showFaList(ActionEvent event) {
    	addPane.setVisible(false);
    	chooseFA.setVisible(true);
    }
    @FXML
    void showGaList(ActionEvent event) {
    	addPane.setVisible(false);
    	chooseGA.setVisible(true);
    }
    @FXML
    void showPilotList(ActionEvent event) {
    	addPane.setVisible(false);
    	choosePilot.setVisible(true);
    	
    	licenceNumLabel.setVisible(true);
    	licenceNumTxt.setVisible(true);
    	licenceDateLabel.setVisible(true);
    	licenceDateTxt.setVisible(true);


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
