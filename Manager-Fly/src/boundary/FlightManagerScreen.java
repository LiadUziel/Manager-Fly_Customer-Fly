package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import control.ExportJsonFlightsControl;
import control.LandingPercentReportControl;
import control.XML_Control;
import entity.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FlightManagerScreen implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button countriesReportBtn;
	
    @FXML
    private Button manageAirportsBtn;
    @FXML
    private Label headerLabel;

    @FXML
    private ListView<String> updateFlightsList;

    @FXML
    private Label updateFlightsLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (LoginScreen.userTypeStatic.equals("primary flight manager")) {
			countriesReportBtn.setVisible(true);
			headerLabel.setText("Primary Flight Manager Screen");
			headerLabel.setLayoutX(230);
		}			
		else {
			countriesReportBtn.setVisible(false);
			manageAirportsBtn.setVisible(false);
			headerLabel.setText("Flight Manager Screen");
			headerLabel.setLayoutX(274);
		}
		
		// xml
		updateFlightsLabel.setText("Flights & thier updated status from XML:");
		updateFlightsLabel.setVisible(false);
		updateFlightsList.setVisible(false);
		
	}
	

    @FXML
    void importXML_flightStatus(ActionEvent event) throws ClassNotFoundException, SQLException {

    	updateFlightsLabel.setVisible(true);
		
    	ArrayList<String> flightAndStatus = XML_Control.getInstance().importFlightStatusFromXML("xml/flightsStatus.xml");
    	updateFlightsList.getItems().clear();
    	updateFlightsList.getItems().addAll(flightAndStatus);
		updateFlightsList.setVisible(true);


    }
    
    @FXML
    void exportJsonUpdatedFlights(ActionEvent event) {
    	ExportJsonFlightsControl.getInstance().exportToJSON();
    }
    
    @FXML
    void generateLandingReport(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	
    	System.out.println(LandingPercentReportControl.getInstance().airportAndCountry());
    	HashMap<String, String> airportAndCountry = LandingPercentReportControl.getInstance().airportAndCountry();
    	System.out.println(LandingPercentReportControl.getInstance().getFlights_LastMonth());
    	ArrayList<Flight> flightsLastMonth = LandingPercentReportControl.getInstance().getFlights_LastMonth();
    	HashMap<String, Integer> countriesAndFrequency = new HashMap<>();
    	
    	for (Flight flight : flightsLastMonth) {
    		String country = airportAndCountry.get(flight.getLandAirport());
    		if (countriesAndFrequency.get(country) == null)
    			countriesAndFrequency.put(country, 1);
    		else
    			countriesAndFrequency.put(country, countriesAndFrequency.get(country)+1);
    	}
//    	countriesAndFrequency = (HashMap<String, Integer>) sortByValue(countriesAndFrequency, false);
    	System.out.println(countriesAndFrequency);
    	HashMap<String, String> countriesAndPercent = new HashMap<>();
    	Double totalFlightsLastMonth = (double) flightsLastMonth.size();
    	
    	for (String country : countriesAndFrequency.keySet()) {
    		Double percent = countriesAndFrequency.get(country)/totalFlightsLastMonth * 100;
    		String perc = percent.toString() + "%";
    		countriesAndPercent.put(country, perc);
    	}
    	countriesAndPercent = (HashMap<String, String>) sortByValue(countriesAndPercent, false);
    	System.out.println(countriesAndPercent.keySet());
    	System.out.println(countriesAndPercent);
    	
    	LandingPercentReportControl.getInstance().resetLandingReport();
    	
    	for (String country : countriesAndPercent.keySet())
    		LandingPercentReportControl.getInstance().addLandingReportToDB(country, countriesAndPercent.get(country));
    	
    	JFrame reportFrame = LandingPercentReportControl.getInstance().produceReport();
		if(reportFrame!=null)
				reportFrame.setVisible(true);
    	
    	
    }
    
    
    // true for ASC, false for DESC
    private static Map<String, String> sortByValue(Map<String, String> unsortMap, final boolean order)
    {
        List<Entry<String, String>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

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
    
    @FXML
    void switchTo_BigFlightsReport(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/FrmBigFlightsReport.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Generate Big Flights Report");
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
    
    
	@FXML
    void switchTo_AddAirport(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/AddAirport.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Add Airport");
		 stage.show();
    }
	
	@FXML
    void switchTo_AddPlane(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/AddPlane.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Add Plane");
		 stage.show();
    }





}
