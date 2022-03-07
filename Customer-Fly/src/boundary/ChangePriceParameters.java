package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ChangePriceParametersControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangePriceParameters implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private Label updateDateLabel;

    @FXML
    private TextField durationTxt;

    @FXML
    private TextField mealTxt;

    @FXML
    private TextField classTxt;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Integer> oldParams = ChangePriceParametersControl.getInstance().getOldParamsDetails();
		updateDateLabel.setText("Last update date: " + ChangePriceParametersControl.maxDate + 
				", Duration: " + oldParams.get(0) + ", Meal: " + oldParams.get(1) + ", Class: " + oldParams.get(2));
		
		durationTxt.setText(oldParams.get(0).toString());
		mealTxt.setText(oldParams.get(1).toString());
		classTxt.setText(oldParams.get(2).toString());
	}


    @FXML
    void changeParameters(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	Integer duration=null, meal=null, clas=null;
    	
    	try {
    		duration = Integer.parseInt(durationTxt.getText());
    		meal = Integer.parseInt(mealTxt.getText());
    		clas =  Integer.parseInt(classTxt.getText());
    		if (duration==null || meal == null || clas==null || duration<0 || meal<0 || clas<0)
    			throw new NumberFormatException();
    	} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input error");
			alert.setHeaderText("Please enter positive number of all the parameters");
			alert.show();
    		return;
    	}
    	if (ChangePriceParametersControl.maxDate.equals(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Date error");
			alert.setHeaderText("It's impossible to update twice in day, please try tomorrow");
			alert.show();
    		return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update parameters");
		alert.setHeaderText("Are you sure you want to update params? ");
		if (alert.showAndWait().get() == ButtonType.OK) {
			ChangePriceParametersControl.getInstance().addParamsToDB(duration, meal, clas);
			switchTo_ChangePriceParameters(event);;
		}

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
    void switchTo_ChangePriceParameters(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ChangePriceParameters.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Customer Fly - Change Price Parameters");
		 stage.show();
    }


}
