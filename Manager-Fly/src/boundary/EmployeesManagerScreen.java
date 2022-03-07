package boundary;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeesManagerScreen {
	private Stage stage;
	private Scene scene;
	private Parent root;


    
    @FXML
    void switchTo_ShibuzFlightEmployees(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ShibuzFlightEmployees.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("ShibutzFlightEmployees - Employee Manager");
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

    
    @FXML
    void switchTo_ManageEmployees(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ManageEmployees.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Employees Management");
		 stage.show();
    }
    
    @FXML
    void switchTo_LoginScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/LoginScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Manager Fly - Login");
		 stage.show();
    }


}
