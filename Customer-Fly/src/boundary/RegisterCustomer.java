package boundary;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import control.RegisterCustomerControl;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegisterCustomer {
	
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TextField passportTxt;

    @FXML
    private TextField firstNameTxt;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private TextField mailTxt;

    @FXML
    private TextField citizenshipTxt;

    @FXML
    private DatePicker birthDateTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private PasswordField passwordAgainTxt;

    
    @FXML
    void register(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String passport = passportTxt.getText();
    	String firstName = firstNameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	String mail = mailTxt.getText();
    	String citizen = citizenshipTxt.getText();
    	LocalDate birthDate = birthDateTxt.getValue();
    	String password = passwordTxt.getText();
    	String passwordAgain = passwordAgainTxt.getText();
    	
    	if (passport==null || passport.equals("") || firstName==null || firstName.equals("") ||
    			lastName==null || lastName.equals("") || mail==null || mail.equals("") ||
    			citizen==null || citizen.equals("") || birthDate==null ||
    			password==null || password.equals("") || passwordAgain==null || passwordAgain.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
    	// check if passport exists
    	if (RegisterCustomerControl.getInstance().getAllPassports().contains(passport)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Passport error");
			alert.setHeaderText("This passport is already exist");
			alert.show();
    		return;
    	}
    	
    	// check if password again identify
    	if (!password.equals(passwordAgain)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Password error");
			alert.setHeaderText("Password isn't equal to password again");
			alert.show();
    		return;
    	}
    	
    	// Check legal date
    	if (birthDate.isAfter(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Birth Date error");
			alert.setHeaderText("Please choose birth date before today");
			alert.show();
    		return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Register");
		alert.setHeaderText("Are you sure?: ");
		if (alert.showAndWait().get() == ButtonType.OK) {
			Customer cust = new Customer(passport, firstName, lastName, mail, citizen, birthDate);
			RegisterCustomerControl.getInstance().addCustomerToDB(cust);
			RegisterCustomerControl.getInstance().addLoginToDB(passport, password);
			switchTo_Login(event);
		}

    }
    
    @FXML
    void switchTo_Login(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/LoginScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Customer Fly - Login");
		 stage.show();
    }


}
