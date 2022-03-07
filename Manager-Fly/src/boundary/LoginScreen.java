package boundary;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import control.LoginControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreen {
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    public static String userTypeStatic;

    
    @FXML
    void login(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
//		HashMap<String, String> usersAndPass = LoginControl.getInstance().getAllCustomersForLogin();
		
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
		
		userTypeStatic = userDetails.get(2).toLowerCase();
		// FlightManager, PrimaryFlightManager
		if (userTypeStatic.equals("flight manager") || userTypeStatic.equals("primary flight manager")) { 
			switchTo_FlightManagerScreen(event);
			return;
		}
		
		// Employee Manager
		else if (userTypeStatic.equals("employees manager")) {
			switchTo_EmployeesManagerScreen(event);
			return;
		}
		
    }
    
    
    
    
    
    
    @FXML
    void switchTo_FlightManagerScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/FlightManagerScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 if (userTypeStatic.equals("flight manager")) 
			 stage.setTitle("Flight Manager Screen");
		 else
			 stage.setTitle("Primary Flight Manager Screen");
		 stage.show();
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
