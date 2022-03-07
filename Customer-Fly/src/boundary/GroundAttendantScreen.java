package boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import control.GroundAttendantControl;
import control.ProductsAndSupplierControl;
import entity.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GroundAttendantScreen implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;



    @FXML
    private ComboBox<Flight> chooseFlight;

    @FXML
    private AnchorPane orderPane;

    @FXML
    private ListView<String> orderList;

    @FXML
    private AnchorPane customerPane;

    @FXML
    private ListView<String> customerList;

    @FXML
    private AnchorPane fmPane;

    @FXML
    private ListView<String> familyMemberList;
    
    @FXML
    private Label premiumLabel;
    static int counter;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		orderPane.setVisible(false);
		customerPane.setVisible(false);
		fmPane.setVisible(false);
		premiumLabel.setVisible(false); counter = 0;
		
		chooseFlight.getItems().clear();
		chooseFlight.getItems().addAll(ProductsAndSupplierControl.getInstance().getAllFlights());
		
	}
	
	@FXML
    void showOrdersOfFlight(ActionEvent event) {
    	if (chooseFlight.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Please Choose Flight");
			alert.show();
    		return;
    	}
    	
    	orderList.getItems().clear();
    	orderList.getItems().addAll(GroundAttendantControl.getInstance().getOrdersOfFlight(chooseFlight.getValue().getFlightNumber()));
    	
    	orderPane.setVisible(true);
    	customerPane.setVisible(false);
    	fmPane.setVisible(false);
    	premiumLabel.setVisible(false);
    }

	
    @FXML
    void showCustomersOfOrder(ActionEvent event) {
    	String orderID = orderList.getSelectionModel().getSelectedItem();
    	
    	if (orderID == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Please Choose Order");
			alert.show();
    		return;
    	}
    	
    	if (counter % 3 == 0) {
    		premiumLabel.setText("Premium Request Of " + orderID + ": Mcdonlads, Window" );
    	}
    	else if (counter % 2 == 0) {
    		premiumLabel.setText("Premium Request Of " + orderID + ": Rolex, Window");
    	}
    	else
    		premiumLabel.setText("The Order: " + orderID + " isn't premium");
    	counter++;
    	premiumLabel.setVisible(true);
    	
    	customerList.getItems().clear();
    	customerList.getItems().addAll(GroundAttendantControl.getInstance().getCustomersOfOrder(orderID));
    	
    	customerPane.setVisible(true);
    	fmPane.setVisible(false);
    }

    @FXML
    void showFamilyMembers(ActionEvent event) {
    	String passport = customerList.getSelectionModel().getSelectedItem();
    	
    	if (passport == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Please Choose Customer");
			alert.show();
    		return;
    	}
    	
    	familyMemberList.getItems().clear();
    	familyMemberList.getItems().addAll(GroundAttendantControl.getInstance().getFamilyMembersOfCustomer(passport));
    	
    	fmPane.setVisible(true);
    }

	
    @FXML
    void switchTo_Login(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/LoginScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Login Screen");
		 stage.show();
    }






}
