package boundary;

import java.io.IOException;
import java.sql.Date;

import javax.swing.JFrame;

import control.BigFlightsReportControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FrmBigFlightsReport {
	
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;
    
    @FXML
    private TextField bigFlightsParameter;
    
    @FXML
    void switchTo_FlightManagerScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/FlightManagerScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager Screen");
		 stage.show();
    }
    
    //-- Here is good
	@FXML
	public void doReport(ActionEvent event)
	{
		try {
		Date from = Date.valueOf(fromDate.getValue());
		Date to = Date.valueOf(toDate.getValue());
		
		int numFlightParameter = 0; 

		try {
			numFlightParameter = Integer.parseInt(bigFlightsParameter.getText());
			if (numFlightParameter<=0)
				throw new NumberFormatException();
		}catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR,"Fields are missing");
			alert.setTitle("Invalid input");
			alert.setHeaderText("Please select positive number for big flight parameter");
			alert.showAndWait();
			return;
		}
		
		if (from == null || to == null)
			throw new NullPointerException();
		
		JFrame reportFrame = BigFlightsReportControl.getInstance().produceReport(from, to, numFlightParameter);
		if(reportFrame!=null)
				reportFrame.setVisible(true);
		
		}catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR,"Fields are missing");
			alert.setTitle("Fields are missing");
			alert.setHeaderText("Please select date from and date to");
			alert.showAndWait();
		}

	}

    
/*
PARAMETERS date1 DateTime, date2 DateTime;
SELECT tbl_flight.flightID, tbl_flight.departureAirportID, tbl_flight.departureDate, tbl_flight.departureHour, tbl_flight.landingAirportID, tbl_flight.landingDate, tbl_flight.landingHour, tbl_flight.flightStatus, tbl_airport.country, tbl_airport.city, Airport_1.country, Airport_1.city
FROM tbl_system, tbl_plane INNER JOIN (tbl_airport INNER JOIN (tbl_flight INNER JOIN tbl_airport AS Airport_1 ON tbl_flight.landingAirportID = Airport_1.airportID) ON tbl_airport.airportID = tbl_flight.departureAirportID) ON tbl_plane.taleID = tbl_flight.taleID
WHERE (((tbl_flight.landingDate) Between [date1] And [date2]) AND ((tbl_plane.seatsInEconomy)>=200))
ORDER BY tbl_flight.landingDate DESC;

SELECT tbl_flight.flightID, tbl_flight.departureAirportID, tbl_flight.departureDate, tbl_flight.departureHour, tbl_flight.landingAirportID, tbl_flight.landingDate, tbl_flight.landingHour, tbl_flight.flightStatus, tbl_airport.country, tbl_airport.city, Airport_1.country, Airport_1.city
FROM tbl_system, tbl_plane INNER JOIN (tbl_airport INNER JOIN (tbl_flight INNER JOIN tbl_airport AS Airport_1 ON tbl_flight.landingAirportID = Airport_1.airportID) ON tbl_airport.airportID = tbl_flight.departureAirportID) ON tbl_plane.taleID = tbl_flight.taleID
WHERE tbl_flight.landingDate>=$P{start} AND  tbl_flight.landingDate<=$P{end}
ORDER BY tbl_flight.landingDate DESC;

PARAMETERS start DateTime, [end] DateTime;
SELECT tbl_flight.flightID, tbl_flight.departureAirportID, tbl_flight.departureDate, tbl_flight.departureHour, tbl_flight.landingAirportID, tbl_flight.landingDate, tbl_flight.landingHour, tbl_flight.flightStatus, tbl_airport.country, tbl_airport.city, tbl_airport_1.country, tbl_airport_1.city
FROM tbl_system, tbl_plane INNER JOIN ((tbl_airport INNER JOIN tbl_flight ON tbl_airport.airportID = tbl_flight.departureAirportID) INNER JOIN tbl_airport AS tbl_airport_1 ON tbl_flight.landingAirportID = tbl_airport_1.airportID) ON tbl_plane.taleID = tbl_flight.taleID
WHERE (((tbl_plane.seatsInEconomy)>200) AND tbl_flight.landingDate>=$P{start} AND tbl_flight.landingDate<=$P{end})
ORDER BY tbl_flight.landingDate DESC;

 */
	
	@FXML
	public void doCountriesReport(ActionEvent event)
	{
		
		
		JFrame reportFrame = BigFlightsReportControl.getInstance().produceCountriesReport();
		if(reportFrame!=null)
				reportFrame.setVisible(true);
		
	}

	


}
