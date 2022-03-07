package boundary;

import java.io.IOException;
import java.sql.SQLException;

import control.AddFlightControl;
import entity.Plane;
import entity.Seat;
import entity.SeatClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddPlane {
	
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private TextField taleID_Input;

    @FXML
    private TextField numRows_Input;

    @FXML
    private TextField numCols_Input;
    
    @FXML
    private TextField requiredWorkers_Input;


    
    @FXML
    void addPlane(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	String taleID = taleID_Input.getText();
    	Integer numRows, numCols, requiredWorkers;
    	
    	try {
    		numRows = Integer.parseInt(numRows_Input.getText());
    		numCols = Integer.parseInt(numCols_Input.getText());
    		requiredWorkers = Integer.parseInt(requiredWorkers_Input.getText());
    		if (numRows<=0 || numCols<=0 || requiredWorkers<=0 || taleID == null || taleID.equals(""))
    			throw new NumberFormatException();
    	} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	//-- Add plane
    	Plane newPlane = null;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Plane");
		alert.setHeaderText("Are you sure you want to add: " + taleID);
		if (alert.showAndWait().get() == ButtonType.OK) {
	    	newPlane = new Plane(taleID, requiredWorkers, -1);
	    	System.out.println("Added plane");
	    	
	    	int counterEconomy = 0;
	    	
	    	AddFlightControl.getInstance().addPlaneToDB(newPlane);
	    	System.out.println("Added plane");

	    	// add Seats
	    	Seat newSeat;
	    	for (int i=0; i<numRows; i++) {
	    		for (int j=0; j<numCols; j++) {
//	    			System.out.println(i + "  0.1*numRows: " + 0.1*numRows);
		    		if (i < 0.1*numRows) {
		    			newSeat = new Seat(numRows, numCols, SeatClass.firstClass, taleID);
		    			//-- Add seat to DB 
		    		}
		    		else if (i>0.1*numRows && i<0.3*numRows) {
		    			newSeat = new Seat(numRows, numCols, SeatClass.buisnessClass, taleID);
		    		}
		    		else {
		    			newSeat = new Seat(numRows, numCols, SeatClass.economyClass, taleID);
		    			counterEconomy++;
		    		}
		    		newPlane.getSeats().add(newSeat);
		    		AddFlightControl.getInstance().addSeatToDB(newSeat); 
	    		}
	    	}
	    	newPlane.setSeatsInEconomy(counterEconomy);
	    	
	    	System.out.println("Added seats");
	    	
	    	AddFlightControl.getInstance().updateSeatsEconomyInDB(counterEconomy, taleID);
//----	    addPlaneToDB(plane);

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
