package Controller;

import MainView.tdm.CustomerTM;
import MainView.tdm.ItemTM;
import bo.BOFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.ItemDto;
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

public class ManageItemController{
    public AnchorPane mainPane;

    public Label lblItemCode;
    public JFXTextField txtItemPackSize;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemQtyHand;
    public JFXTextField txtItemDiscount;
    
    public ComboBox cmbUcode;
    public JFXTextField txtUPackSize;
    public JFXTextField txtUPrice;
    public JFXTextField txtUDescription;
    public JFXTextField txtUQtyHand;
    public JFXTextField txtUDiscount;
    
    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colQtyHand;

    private final ItemBo itemBo = (ItemBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public Button btnItemSave;
    public Button btnItemUpdate;

    String DescriptionRegex = "^[A-z ]{3,50}$";
    String UnitPriceRegex = "^[0-9]{2,60}$";
    String QtyHandRegex = "^[0-9]{2,40}$";
    String DiscountRegex = "^[0-9]{2,60}$";

    private String forValid = "-fx-border-color: green;";
    private String forInvalid = "-fx-border-color: red;";

    private boolean  isUnitPriceValidated= false;
    private boolean  isDescriptionValidated= false;
    private boolean  isQtyHandValidated= false;
    private boolean  isDiscountValidated= false;

    private boolean  isUPriceValidated= false;
    private boolean  isUDescriptionValidated= false;
    private boolean  isUQtyHandValidated= false;
    private boolean  isUDiscountValidated= false;

    public boolean validator(String pattern, String matcher){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(matcher);
        boolean matchFound = mat.find();
        return matchFound;
    }

    public void initialize() throws SQLException, ClassNotFoundException {

        loadAllItem();
        loadItems();
        lblItemCode.setText(itemBo.genarateNewItemCode());

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colQtyHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }


    public void btnItemSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=lblItemCode.getText();
        String description=txtItemDescription.getText();
        String packSize=txtItemPackSize.getText();
        Double unitPrice=Double.parseDouble(txtItemUnitPrice.getText());
        Double discount=Double.parseDouble(txtItemDiscount.getText());
        Integer qtyOnHand=Integer.parseInt(txtItemQtyHand.getText());

        if((itemBo).saveItem(new ItemDto(code,description,packSize,unitPrice,discount,qtyOnHand))) {
            new Alert(Alert.AlertType.INFORMATION,"saved!").show();
            lblItemCode.setText(itemBo.genarateNewItemCode());
            loadAllItem();
            loadItems();
            clearAddItemUI();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (itemBo.updateItem(new ItemDto(String.valueOf(cmbUcode.getValue()), txtUDescription.getText(), txtUPackSize.getText(),
                    Double.parseDouble(txtUPrice.getText()),Double.parseDouble(txtUDiscount.getText()) ,Integer.parseInt(txtUQtyHand.getText())))) {
                new Alert(Alert.AlertType.INFORMATION, "updated!").show();
                loadItems();
                loadAllItem();
                clearUpdateItemUI();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearUpdateItemUI(){
        txtUDescription.clear();
        txtUPackSize.clear();
        txtUPrice.clear();
        txtUDiscount.clear();
        txtUQtyHand.clear();
    }

    private void clearAddItemUI(){
        txtItemDescription.clear();
        txtItemPackSize.clear();
        txtItemUnitPrice.clear();
        txtItemDiscount.clear();
        txtItemQtyHand.clear();
    }

    public void loadAllItem() throws SQLException, ClassNotFoundException {
        cmbUcode.getSelectionModel().clearSelection();
        cmbUcode.getItems().clear();
        ArrayList<ItemDto> arrayList=itemBo.getAllItem();
        for (ItemDto dto:arrayList
        ) {
            cmbUcode.getItems().add(dto.getCode());
        }
    }

    private void loadItems() throws SQLException, ClassNotFoundException {
        tblItem.getItems().clear();
        ArrayList<ItemDto> arrayList=itemBo.getAllItem();
        for (ItemDto dto:arrayList) {
            tblItem.getItems().add(new ItemTM(dto.getCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getDiscount(), dto.getQtyOnHand()));
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (itemBo.deleteItem(String.valueOf(cmbUcode.getValue()))) {
            new Alert(Alert.AlertType.INFORMATION, "deleted!").show();
            loadAllItem();
            loadItems();
            clearUpdateItemUI();
        }
    }

    public void cmbItemCodeOnAction (ActionEvent actionEvent){
        if (cmbUcode.getValue() != null) {
            ItemDto search = null;
            try {
                search = itemBo.search(String.valueOf(cmbUcode.getValue()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            txtUDescription.setText(search.getDescription());
            txtUPackSize.setText(search.getPackSize());
            txtUPrice.setText(String.valueOf(search.getUnitPrice()));
            txtUDiscount.setText(String.valueOf(search.getDiscount()));
            txtUQtyHand.setText(String.valueOf(search.getQtyOnHand()));
        }

    }

    public void navigateToBack(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("../MainView/administratorForm.fxml");
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

    public void packSizeOnKeyReleased(KeyEvent keyEvent) {
    }
    private void checker(){
        if(isUnitPriceValidated & isDescriptionValidated & isQtyHandValidated & isDiscountValidated){
            btnItemSave.setDisable(false);
        }else {
            btnItemSave.setDisable(true);
        }
    }

    public void unitPriceOnKeyReleased(KeyEvent keyEvent) {
        if(validator(UnitPriceRegex,txtItemUnitPrice.getText())){
            txtItemUnitPrice.setStyle(forValid);
            isUnitPriceValidated=true;
        }else{
            txtItemUnitPrice.setStyle(forInvalid);
            isUnitPriceValidated=false;
        }
        checker();
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        if(validator(DescriptionRegex,txtItemDescription.getText())){
            txtItemDescription.setStyle(forValid);
            isDescriptionValidated=true;
        }else{
            txtItemDescription.setStyle(forInvalid);
            isDescriptionValidated=false;
        }
        checker();
    }

    public void qtyHandOnKeyReleased(KeyEvent keyEvent) {
        if(validator(QtyHandRegex,txtItemQtyHand.getText())){
            txtItemQtyHand.setStyle(forValid);
            isQtyHandValidated=true;
        }else{
            txtItemQtyHand.setStyle(forInvalid);
            isQtyHandValidated=false;
        }
        checker();
    }

    public void discountOnKeyReleased(KeyEvent keyEvent) {
        if(validator(DiscountRegex,txtItemDiscount.getText())){
            txtItemDiscount.setStyle(forValid);
            isDiscountValidated=true;
        }else{
            txtItemDiscount.setStyle(forInvalid);
            isDiscountValidated=false;
        }
        checker();

    }

    private void Uchecker(){
        if(isUPriceValidated & isUDescriptionValidated & isUQtyHandValidated & isUDiscountValidated){
            btnItemUpdate.setDisable(false);
        }else {
            btnItemUpdate.setDisable(true);
        }
    }

    public void UPriceOnKeyReleased(KeyEvent keyEvent) {
        if(validator(UnitPriceRegex,txtUPrice.getText())){
            txtUPrice.setStyle(forValid);
            isUPriceValidated=true;
        }else{
            txtUPrice.setStyle(forInvalid);
            isUPriceValidated=false;
        }
        Uchecker();
    }

    public void UdescriptionOnKeyReleased(KeyEvent keyEvent) {
        if(validator(DescriptionRegex,txtUDescription.getText())){
            txtUDescription.setStyle(forValid);
            isUDescriptionValidated=true;
        }else{
            txtUDescription.setStyle(forInvalid);
            isUDescriptionValidated=false;
        }
        Uchecker();
    }

    public void UqtyHandOnKeyReleased(KeyEvent keyEvent) {
        if(validator(QtyHandRegex,txtUQtyHand.getText())){
            txtUQtyHand.setStyle(forValid);
            isUQtyHandValidated=true;
        }else{
            txtUQtyHand.setStyle(forInvalid);
            isUQtyHandValidated=false;
        }
        Uchecker();
    }

    public void UDiscountOnKeyReleased(KeyEvent keyEvent) {
        if(validator(DiscountRegex,txtUDiscount.getText())){
            txtUDiscount.setStyle(forValid);
            isUDiscountValidated=true;
        }else{
            txtUDiscount.setStyle(forInvalid);
            isUDiscountValidated=false;
        }
        Uchecker();
    }
}
