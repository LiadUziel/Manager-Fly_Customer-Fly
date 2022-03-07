package boundary;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import control.CustomerScreenControl;
import entity.Flight;
import entity.OrderFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerScreen implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	

    @FXML
    private Label customerLabel;

	
    // Order Flight Table
    @FXML
    private TableView<OrderFlight> orderFlightTable;
    
    @FXML
    private TableColumn<OrderFlight, String> flightNumberCol;

    @FXML
    private TableColumn<OrderFlight, String> orderIdCol;

    @FXML
    private TableColumn<OrderFlight, Boolean> hasCancelledCol;

    


    // End Table

    @FXML
    private Label alternativeFlightsLabel;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		customerLabel.setText("Hello " + LoginScreen.customerPassport);
		
		// Flights table
		flightNumberCol.setCellValueFactory(new PropertyValueFactory<OrderFlight, String>("flightNumber"));
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderFlight, String>("orderId"));
		hasCancelledCol.setCellValueFactory(new PropertyValueFactory<OrderFlight, Boolean>("hasCancelled"));

		ObservableList<OrderFlight> allOrderFlights = FXCollections.observableArrayList(CustomerScreenControl.getInstance().getOrderFlightsOfSCutomer(LoginScreen.customerPassport));
		orderFlightTable.setItems(allOrderFlights);
		
		// alternativeFlightsLabel
		if (CancelOrders.alternativeFlightsByCustomer == null)
			CancelOrders.alternativeFlightsByCustomer = new HashMap<>();
		if (CancelOrders.alternativeFlightsByCustomer.get(LoginScreen.customerPassport) != null) {
			String str = "";
			for (Flight altFlight : CancelOrders.alternativeFlightsByCustomer.get(LoginScreen.customerPassport))
				str += altFlight + "\n";
			alternativeFlightsLabel.setText(str);
		}
		else
			alternativeFlightsLabel.setText("");
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
    
    @FXML
    void switchTo_OrderFlight(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/OrderFlight.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("OrderFlight");
		 stage.show();
    }

















}
