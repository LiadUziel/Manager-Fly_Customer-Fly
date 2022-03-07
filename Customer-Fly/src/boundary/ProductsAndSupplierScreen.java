package boundary;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ProductsAndSupplierControl;
import entity.EntertainmentProduct;
import entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProductsAndSupplierScreen implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private TableView<EntertainmentProduct> productsTable;

    @FXML
    private TableColumn<EntertainmentProduct, String> productIDCol;

    @FXML
    private TableColumn<EntertainmentProduct, String> typeCol;

    @FXML
    private TableColumn<EntertainmentProduct, String> productNameCol;

    @FXML
    private TableColumn<EntertainmentProduct, String> descriptionCol;

    @FXML
    private TableView<Supplier> suppliersTable;

    @FXML
    private TableColumn<Supplier, String> supplierIdCol;

    @FXML
    private TableColumn<Supplier, String> supplierNameCol;

    @FXML
    private TableColumn<Supplier, String> phoneCol;

    @FXML
    private TableColumn<Supplier, String> mailCol;

    @FXML
    private AnchorPane supplierPane;

    @FXML
    private TextField supplierIdTxt;

    @FXML
    private TextField supploerNameTxt;

    @FXML
    private TextField supploerPhoneTxt;

    @FXML
    private TextField supplierMailTxt;

    @FXML
    private AnchorPane productsPane;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productDescTxt;

    @FXML
    private ComboBox<String> productTypeChoose;

    @FXML
    private ComboBox<String> supplierOfProductChoose;
    
    
    ArrayList<EntertainmentProduct> allProducts;
    ArrayList<Supplier> allSuppliers;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// products table
		productIDCol.setCellValueFactory(new PropertyValueFactory<EntertainmentProduct, String>("productID"));
		typeCol.setCellValueFactory(new PropertyValueFactory<EntertainmentProduct, String>("type"));
		productNameCol.setCellValueFactory(new PropertyValueFactory<EntertainmentProduct, String>("Name"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<EntertainmentProduct, String>("description"));

		allProducts = ProductsAndSupplierControl.getInstance().getAllProducts();
		ObservableList<EntertainmentProduct> allProducts1 = FXCollections.observableArrayList(allProducts);
		productsTable.setItems(allProducts1);

		// suppliers table
		supplierIdCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierID"));
		supplierNameCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phone"));
		mailCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("mail"));

		allSuppliers = ProductsAndSupplierControl.getInstance().getAllSuppliers();
		ObservableList<Supplier> allSuppliers1 = FXCollections.observableArrayList(allSuppliers);
		suppliersTable.setItems(allSuppliers1);
		
		// productsAddPane
		productsPane.setVisible(false);
		productTypeChoose.getItems().add("Game");
		productTypeChoose.getItems().add("Movie");
		productTypeChoose.setValue("Game");
		for (Supplier sup : allSuppliers)
			supplierOfProductChoose.getItems().add(sup.getSupplierID());
		
		// supplierAddPane
		supplierPane.setVisible(false);
	}


    @FXML
    void showProductPane(ActionEvent event) {
		productsPane.setVisible(true);
    }

    @FXML
    void showSupplierPane(ActionEvent event) {
    	supplierPane.setVisible(true);
    }

    @FXML
    void submitAddProduct(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	String productID = productIdTxt.getText(), productName = productNameTxt.getText(), productDesc = productDescTxt.getText(),
    			productType = productTypeChoose.getValue(), supplierOfProduct = supplierOfProductChoose.getValue();
    	
    	if (productID==null || productID.equals("") || productName==null || productName.equals("") ||
    			productDesc==null || productDesc.equals("") || supplierOfProduct==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
    	ArrayList<String> productIDs = new ArrayList<>();
    	for (EntertainmentProduct temProduct: allProducts)
    		productIDs.add(temProduct.getProductID());
    	if (productIDs.contains(productID)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("This product is already exist");
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Entertainment product");
		alert.setHeaderText("Are you sure you want to add: " + productID + "?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			EntertainmentProduct ep = new EntertainmentProduct(productID, productName, productDesc, productType);
			ProductsAndSupplierControl.getInstance().addProductToDB(ep);
			ProductsAndSupplierControl.getInstance().addSupplierProductToDB(supplierOfProduct, productID);
			switchTo_ProductsAndSupplierScreen(event);
		}
    }

    @FXML
    void submitAddSupplier(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	String supplierID = supplierIdTxt.getText(), supplierName = supploerNameTxt.getText(),
    			supplierPhone = supploerPhoneTxt.getText(), supplierMail = supplierMailTxt.getText();
    	
    	if (supplierID==null || supplierID.equals("") || supplierName==null || supplierName.equals("") ||
    			supplierPhone==null || supplierPhone.equals("") || supplierMail==null || supplierMail.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incomplete field");
			alert.setHeaderText("Please complete all the fields");
			alert.show();
    		return;
    	}
    	
    	ArrayList<String> supplierIDs = new ArrayList<>();
    	for (Supplier tempSupplier: allSuppliers)
    		supplierIDs.add(tempSupplier.getSupplierID());
    	if (supplierIDs.contains(supplierID)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid input");
			alert.setHeaderText("This supplier is already exist");
			alert.showAndWait();
			return;
    	}
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Supplier");
		alert.setHeaderText("Are you sure you want to add: " + supplierID + "?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			Supplier supp = new Supplier(supplierID, supplierName, supplierPhone, supplierMail);
			ProductsAndSupplierControl.getInstance().addSupplierToDB(supp);
			switchTo_ProductsAndSupplierScreen(event);
		}
    }

    
    @FXML
    void switchTo_ProductsAndSupplierScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ProductsAndSupplierScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager - Products AndS upplier Screen");
		 stage.show();
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
    void switchTo_ProductsInFlights(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ProductsInFlights.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager - Products In Flights");
		 stage.show();
    }
    
    
    @FXML
    void switchTo_ProductsReportScreen(ActionEvent event) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("/boundary/ProductsReportScreen.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.setTitle("Flight Manager - Products Report Screen");
		 stage.show();
    }


}
