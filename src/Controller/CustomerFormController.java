package Controller;

import MainView.tdm.CustomerTM;
import bo.BOFactory;
import bo.custom.CustomerBo;
import bo.custom.impl.CustomerBoImpl;
import dao.custom.CustomerDao;
import dao.custom.impl.CustomerDaoImpl;
import dto.CustomerDto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController {
    public Label lblCusId;

    public ComboBox cmbCusTitle;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtCity;
    public ComboBox cmbCusProvince;
    public TextField txtPostalCode;
    public Button btnCustSave;
    public AnchorPane mainPane;
    
    public ComboBox cmbUCustId;
    public ComboBox cmbUTitle;
    public TextField txtUCustName;
    public TextField txtUAddress;
    public TextField txtUCity;
    public ComboBox cmbUProvince;
    public TextField txtUPostalCode;

    public TableView tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusTitle;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusCity;
    public TableColumn colCusProvince;
    public TableColumn colCusPostalCode;
    public Button btnCustomerSave;
    public Button btnCustomerUpdate;


    //CustomerBo customerBo=new CustomerBoImpl();
    CustomerDao customerDao=new CustomerDaoImpl();

    private final CustomerBo customerBO = (CustomerBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    String NameRegex = "^[A-Z]\\.[A-Z]\\.[A-Z][a-z]{3,15}$";
    String AddressRegex = "^[A-z ]{3,60}$";
    String CityRegex = "^[A-z ]{3,60}$";
    String PostalCodeRegex = "^[0-9]{5}$";

    private String forValid = "-fx-border-color: green;";
    private String forInvalid = "-fx-border-color: red;";

    private boolean isNameValidated = false;
    private boolean isAddressValidated = false;
    private boolean isCityValidated = false;
    private boolean isPostalCodeValidated = false;

    private boolean isUNameValidated = false;
    private boolean isUAddressValidated = false;
    private boolean isUCityValidated = false;
    private boolean isUPostalCodeValidated = false;

    public boolean validator(String pattern, String matcher){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(matcher);
        boolean matchFound = mat.find();
        return matchFound;
    }

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbCusProvince.getItems().add("Southern");
        cmbCusProvince.getItems().add("Uva");
        cmbCusProvince.getItems().add("Eastern");
        cmbCusProvince.getItems().add("Sabaragamuwa");
        cmbCusProvince.getItems().add("Central");
        cmbCusProvince.getItems().add("North Central");
        cmbCusProvince.getItems().add("Western");
        cmbCusProvince.getItems().add("North Western");
        cmbCusProvince.getItems().add("Northern");

        cmbUProvince.getItems().add("Southern");
        cmbUProvince.getItems().add("Uva");
        cmbUProvince.getItems().add("Eastern");
        cmbUProvince.getItems().add("Sabaragamuwa");
        cmbUProvince.getItems().add("Central");
        cmbUProvince.getItems().add("North Central");
        cmbUProvince.getItems().add("Western");
        cmbUProvince.getItems().add("North Western");
        cmbUProvince.getItems().add("Northern");

        cmbCusTitle.getItems().add("Miss");
        cmbCusTitle.getItems().add("Mrs");
        cmbCusTitle.getItems().add("Mr");
        cmbUTitle.getItems().add("Miss");
        cmbUTitle.getItems().add("Mrs");
        cmbUTitle.getItems().add("Mr");
        try {
            loadAllCustomer();
            loadCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lblCusId.setText(customerBO.genarateNewCustomerId());

        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCusCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCusProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colCusPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    }

    public void btnAddOnAction(ActionEvent actionEvent){
        String id=lblCusId.getText();
        String title = String.valueOf(cmbCusTitle.getValue());
        String name=txtName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=String.valueOf(cmbCusProvince.getValue());
        String postalCode=txtPostalCode.getText();


        try {
            if((customerBO).saveCustomer(new CustomerDto(id,title,name,address,city,province,postalCode))){
                new Alert(Alert.AlertType.INFORMATION,"saved!").show();
                lblCusId.setText(customerBO.genarateNewCustomerId());
                loadCustomers();
                loadAllCustomer();
                clearAddCustomerUI();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCustUpdateOnAction(ActionEvent actionEvent) {
        try {
            if(customerBO.updateCustomer(new CustomerDto(String.valueOf(cmbUCustId.getValue()), String.valueOf(cmbUTitle.getValue()),txtUCustName.getText(),
                    txtUAddress.getText(),txtUCity.getText(),String.valueOf(cmbUProvince.getValue()),txtUPostalCode.getText()))){
                new Alert(Alert.AlertType.INFORMATION,"updated!").show();
                loadCustomers();
                loadAllCustomer();
                clearUpdateCustomerUI();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearUpdateCustomerUI(){
        cmbUCustId.setValue(null);
        cmbUTitle.setValue(null);
        txtUCustName.clear();
        txtUAddress.clear();
        txtUCity.clear();
        cmbUProvince.setValue(null);
        txtUPostalCode.clear();
    }

    private void clearAddCustomerUI(){
       // lblCusId.clear();
        cmbCusTitle.setValue(null);
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        cmbCusProvince.setValue(null);
        txtPostalCode.clear();

    }

    public void cmbCustIdOnAction(ActionEvent actionEvent) {

            if(cmbUCustId.getValue()!=null){
                CustomerDto search = null;
                try {
                    search = customerBO.search(String.valueOf(cmbUCustId.getValue()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                cmbUTitle.setValue(search.getTitle());
                txtUCustName.setText(search.getName());
                txtUAddress.setText(search.getAddress());
                txtUCity.setText(search.getCity());
                cmbUProvince.setValue(search.getProvince());
                txtUPostalCode.setText(search.getPostalCode());
            }

        // customerBo.search(cmbUCustId.getItems());
    }

    public void loadAllCustomer() throws SQLException, ClassNotFoundException {
        cmbUCustId.getSelectionModel().clearSelection();
        cmbUCustId.getItems().clear();
        ArrayList<CustomerDto> arrayList=customerBO.getAllCustomer();
        for (CustomerDto dto:arrayList
        ) {
            cmbUCustId.getItems().add(dto.getId());
        }
    }

    private void loadCustomers() throws SQLException, ClassNotFoundException {
        tblCustomer.getItems().clear();
        ArrayList<CustomerDto> arrayList=customerBO.getAllCustomer();
        for (CustomerDto dto:arrayList) {
            tblCustomer.getItems().add(new CustomerTM(dto.getId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(),
                    dto.getPostalCode()));
        }

    }

    public void btnCustDeleteOnAction(ActionEvent actionEvent) {
        try {
            if(customerBO.deleteCustomer(String.valueOf(cmbUCustId.getValue()))){
                new Alert(Alert.AlertType.INFORMATION,"deleted!").show();
                loadCustomers();
                loadAllCustomer();
                clearUpdateCustomerUI();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void navigateToBack(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("../MainView/cashierForm.fxml");
        Parent mainPane = FXMLLoader.load(resource);
        Scene scene = new Scene(mainPane);
        Stage primaryStage = (Stage) (this.mainPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("../MainView/mainForm.fxml");
        Parent mainPane = FXMLLoader.load(resource);
        Scene scene = new Scene(mainPane);
        Stage primaryStage = (Stage) (this.mainPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

   

    private void checker(){
        if(isNameValidated & isAddressValidated & isCityValidated & isPostalCodeValidated){
            btnCustomerSave.setDisable(false);
        }else {
            btnCustomerSave.setDisable(true);
        }
    }

    public void cusNameOnKeyReleased(KeyEvent keyEvent) {
        if(validator(NameRegex,txtName.getText())){
            txtName.setStyle(forValid);
            isNameValidated=true;
        }else{
            txtName.setStyle(forInvalid);
            isNameValidated=false;
        }
        checker();
    }

    public void cusAddressOnKeyReleased(KeyEvent keyEvent) {
        if(validator(AddressRegex,txtAddress.getText())){
            txtAddress.setStyle(forValid);
            isAddressValidated=true;
        }else{
            txtAddress.setStyle(forInvalid);
            isAddressValidated=false;
        }
        checker();
    }

    public void cusCityOnKeyReleased(KeyEvent keyEvent) {
        if(validator(CityRegex,txtCity.getText())){
            txtCity.setStyle(forValid);
            isCityValidated=true;
        }else{
            txtCity.setStyle(forInvalid);
            isCityValidated=false;
        }
        checker();
    }

    public void cusPostalCodeOnKeyReleased(KeyEvent keyEvent) {
        if(validator(PostalCodeRegex,txtPostalCode.getText())){
            txtPostalCode.setStyle(forValid);
            isPostalCodeValidated=true;
        }else{
            txtPostalCode.setStyle(forInvalid);
            isPostalCodeValidated=false;
        }
        checker();
    }

    private void Uchecker(){
        if(isUNameValidated & isUAddressValidated & isUCityValidated & isUPostalCodeValidated){
            btnCustomerUpdate.setDisable(false);
        }else {
            btnCustomerUpdate.setDisable(true);
        }
    }

    public void cusUNameOnKeyReleased(KeyEvent keyEvent) {
        if(validator(NameRegex,txtUCustName.getText())){
            txtUCustName.setStyle(forValid);
            isUNameValidated=true;
        }else{
            txtUCustName.setStyle(forInvalid);
            isUNameValidated=false;
        }
        Uchecker();
    }

    public void cusUAddressOnKeyReleased(KeyEvent keyEvent) {
        if(validator(AddressRegex,txtUAddress.getText())){
            txtUAddress.setStyle(forValid);
            isUAddressValidated=true;
        }else{
            txtUAddress.setStyle(forInvalid);
            isUAddressValidated=false;
        }
        Uchecker();
    }

    public void cusUCityOnKeyReleased(KeyEvent keyEvent) {
        if(validator(CityRegex,txtUCity.getText())){
            txtUCity.setStyle(forValid);
            isUCityValidated=true;
        }else{
            txtUCity.setStyle(forInvalid);
            isUCityValidated=false;
        }
        Uchecker();
    }

    public void cusUPostalCodeOnKeyReleased(KeyEvent keyEvent) {
        if(validator(PostalCodeRegex,txtUPostalCode.getText())){
            txtUPostalCode.setStyle(forValid);
            isUPostalCodeValidated=true;
        }else{
            txtUPostalCode.setStyle(forInvalid);
            isUPostalCodeValidated=false;
        }
        Uchecker();
    }
}
