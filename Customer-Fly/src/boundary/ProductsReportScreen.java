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
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import control.ProductsAndSupplierControl;
import control.ReportProductControl;
import entity.ProductInFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ProductsReportScreen implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> chooseCity;
    
    @FXML
    private PieChart pieChart;
    
    @FXML
    private ListView<String> quantityList;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chooseCity.getItems().addAll(ReportProductControl.getInstance().getAllAirports());
		
		pieChart.setVisible(false);
		quantityList.setVisible(false);
	}


    @FXML
    void generateReport(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	if (chooseCity.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Please Choose City");
			alert.show();
    		return;
    	}
    	
    	ArrayList<String> flightsIDSpecAirportLastMonth = ReportProductControl.getInstance().getFlightsWithSpecAirportFromLastMonth(chooseCity.getValue());
    	System.out.println(flightsIDSpecAirportLastMonth);
    	
    	ArrayList<String> productsInTheseFlights = new ArrayList<>();
    	for (String flightID: flightsIDSpecAirportLastMonth) 
    		for (ProductInFlight pif : ProductsAndSupplierControl.getInstance().getProductsOfSpecFlight(flightID))
    			productsInTheseFlights.add(pif.getProductID());
    	
    	System.out.println(productsInTheseFlights);
    	
    	HashMap<String, Integer> productsAndFrequency = new HashMap<>();
    	// init values
    	for (String product : productsInTheseFlights) {
    		if (productsAndFrequency.get(product) == null)
    			productsAndFrequency.put(product, 1);
    		else
    			productsAndFrequency.put(product, productsAndFrequency.get(product) + 1);
    	}

    	// true for ASC, false for DESC
    	HashMap<String, Integer> sortedProductsAndFrequency = (HashMap<String, Integer>) sortByValue(productsAndFrequency, true);
    	System.out.println(sortedProductsAndFrequency);
    	
    	ReportProductControl.getInstance().resetProductsReport();
    	
    	for (String country : sortedProductsAndFrequency.keySet())
    		ReportProductControl.getInstance().addLandingReportToDB(country, sortedProductsAndFrequency.get(country));
    	
    	JFrame reportFrame = ReportProductControl.getInstance().produceReport();
		if(reportFrame!=null)
				reportFrame.setVisible(true);

    	
    	// Start Pie chart - JAVA FX
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		ArrayList<String> contents = new ArrayList<>();
		String content = "";
		for (String product : sortedProductsAndFrequency.keySet()) {
			content += (product + ": ");
			content += (sortedProductsAndFrequency.get(product) + "\n");
			contents.add(content);
			content = "";
			pieChartData.add(new PieChart.Data(product, sortedProductsAndFrequency.get(product)));
		}
		quantityList.getItems().clear();
		quantityList.getItems().addAll(contents);
		quantityList.setVisible(true);
		pieChart.setData(pieChartData);
		pieChart.setVisible(true);
    	
    }
    
    // true for ASC, false for DESC
    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

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
