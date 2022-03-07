package boundary;

import java.io.IOException;
import java.util.ArrayList;

import control.LoginControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginScreen {
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    public static String customerPassport;
    
    @FXML
    void login(ActionEvent event) throws IOException {
    	
		String user = userName.getText();
		String pass = password.getText();
		
		// index 0 - userName, index 1 - password, index 2 - type
		ArrayList<String> userDetails = LoginControl.getInstance().getUserDetails(user);
		
		if (userDetails.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("User isn't exist");
			alert.setHeaderText("User isn't exist");
			alert.show();
    		return;
		}
		
		// Check password
		if (!pass.equals(userDetails.get(1))) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Password error");
			alert.setHeaderText("Password is wrong");
			alert.show();
    		return;
		}
		
		// CRM = Custmer Relation Manager
		if (user.equals("CRM")) { 
			switchTo_UpdateFlights(event);
			return;
		}
		
		// MT = Flights Manager
		if (user.equals("MT")) { 
			switchTo_ProductsAndSupplierScreen(event);
			return;
		}
		
		// DK = GroundAttendant
		if (user.equals("DK")) { 
			switchTo_GroundAttendantScreen(event);
			return;
		}

		
		// Customer Screen
		customerPassport = user;
		switchTo_CustomerScreen(event);
		return;
		
    }
    
    
    
    
    
    
    @FXML
    void switchTo_UpdateFlights(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/UpdateFlights.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Update Flights");
		 stage.show();
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
    
    @FXML
    void switchTo_GroundAttendantScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/GroundAttendantScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Ground Attendant Screen");
		 stage.show();
    }

    
    @FXML
    void switchTo_RegisterCustomer(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/RegisterCustomer.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Register Customer");
		 stage.show();
    }

    @FXML
    void switchTo_ProductsAndSupplierScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ProductsAndSupplierScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager - Products And Supplier Screen");
		 stage.show();
    }



}
