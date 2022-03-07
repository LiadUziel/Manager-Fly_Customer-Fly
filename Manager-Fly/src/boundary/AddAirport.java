package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import control.AddFlightControl;
import entity.Airport;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddAirport implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TextField airportID_Txt;

    @FXML
    private TextField country_Txt;

    @FXML
    private TextField city_Txt;

    @FXML
    private ComboBox<Character> plusMinus;

    @FXML
    private ComboBox<String> timeValue;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		plusMinus.getItems().add('+');
		plusMinus.getItems().add('-');
		
		
		for (Double i=0.0; i<=13; i+=0.5)
			timeValue.getItems().add(i.toString());
	}


    @FXML
    void addAirport(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	String airportID = airportID_Txt.getText();
    	String country = country_Txt.getText();
    	String city = city_Txt.getText();
//    	String timeZoneStr = timeZone_Txt.getText();
    	Character plusminus = plusMinus.getValue();
    	String timeValueStr = timeValue.getValue();
    	Double timeValueD;
    	
    	if (plusminus == null || timeValueStr==null || timeValueStr.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	timeValueD = Double.parseDouble(timeValueStr);
    	if (plusminus.equals('-'))
    		timeValueD *= -1;
    	
    	if (airportID == null || airportID.equals("") || country==null || country.equals("") || city==null || city.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Airport");
		alert.setHeaderText("Are you sure you want to add: " + airportID);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	Airport airport = new Airport(airportID, country, city, timeValueD);
	    	System.out.println("added airport to java");
//	    	addAirportToDB(airport);
	    	AddFlightControl.getInstance().addAirportToDB(airport);

			switchTo_AddFlight(event);
		}
    }
    

    @FXML
    void switchTo_AddFlight(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/FrmAddFlight.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Add Flight");
		 stage.show();
    }

}
