package Controller;

import MainView.tdm.CartTM;
import bo.BOFactory;
import bo.custom.ItemBo;
import bo.custom.PurchaseOrderBO;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.OrderDetails;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class PlaceOrderController {
    public AnchorPane mainPane;
    public Label lblOrderId;

    public ComboBox cmbPOCusId;
    public Label lblPOCusName;

    public ComboBox cmbPOCode;
    public Label lblPODescription;
    public Label lblPOPackSize;
    public Label lblPOQtyHand;
    public Label lblPOPrice;
    public Label lblPODiscount;

    public TableView tblCart;
    public TableColumn colCartItemCode;
    public TableColumn colCartUnitPrice;
    public TableColumn colCartQty;
    public TableColumn colCartDiscount;
    public TableColumn colCartTotPrice;
    public TableColumn colCartOption;
    public Label lblTotal;
    public TextField txtCartQty;
    public TextField txtCartDiscount;

    public Label lblDate;

    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    ItemBo itemBo = (ItemBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    ArrayList<ItemDto> itemsToChoose = null;

    private ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllCustomers();
        loadAllItems();
        loadDate();
        lblOrderId.setText(purchaseOrderBO.genarateNewOrderID());

        itemsToChoose = itemBo.getAllItem();

        colCartItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colCartUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCartDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colCartTotPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colCartOption.setCellValueFactory(new PropertyValueFactory<>("delete"));
        tblCart.setItems(obList);
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

    public void cmbPOCusIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbPOCusId.getValue() != null) {
            CustomerDto search = null;
            search = purchaseOrderBO.searchCustomer(String.valueOf(cmbPOCusId.getValue()));

            lblPOCusName.setText(search.getName());
        }
    }

    public void loadAllCustomers() throws SQLException, ClassNotFoundException {
        cmbPOCusId.getSelectionModel().clearSelection();
        cmbPOCusId.getItems().clear();
        ArrayList<CustomerDto> arrayList = purchaseOrderBO.getAllCustomers();
        for (CustomerDto customerDto : arrayList
        ) {
            cmbPOCusId.getItems().add(customerDto.getId());
        }
    }

    public void loadAllItems() throws SQLException, ClassNotFoundException {
        cmbPOCode.getSelectionModel().clearSelection();
        cmbPOCode.getItems().clear();
        ArrayList<ItemDto> arrayList = purchaseOrderBO.getAllItems();
        for (ItemDto itemDto : arrayList
        ) {
            cmbPOCode.getItems().add(itemDto.getCode());
        }
    }

    public void cmbPOCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbPOCode.getValue() != null) {
            ItemDto search = null;
            for (ItemDto dto : itemsToChoose) {
                if (dto.getCode().equals(cmbPOCode.getValue().toString())) {
                    search = dto;
                }
            }

            lblPODescription.setText(search.getDescription());
            lblPOPackSize.setText(search.getPackSize());
            lblPOQtyHand.setText(String.valueOf(search.getQtyOnHand()));
            lblPOPrice.setText(String.valueOf(search.getUnitPrice()));
            lblPODiscount.setText(String.valueOf(search.getDiscount()));
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {


        if ((Integer.parseInt(lblPOQtyHand.getText()) >= Integer.parseInt(txtCartQty.getText())) & (Integer.parseInt(txtCartQty.getText()) > 0) &
                (Double.parseDouble(lblPODiscount.getText()) >= Double.parseDouble(txtCartDiscount.getText())) & (Double.parseDouble(txtCartDiscount.getText()) > 0)) {
            try {
                Double unitPrice = Double.parseDouble(lblPOPrice.getText());
                int qty = Integer.parseInt(txtCartQty.getText());
                Double discount = Double.parseDouble(txtCartDiscount.getText());
                Double total = (unitPrice * qty) - (discount * qty);

                CartTM tm = new CartTM(cmbPOCode.getValue().toString(), Double.parseDouble(lblPOPrice.getText()), qty, discount, total);

                tm.getDelete().setOnAction(e -> {
                    obList.remove(tm);
                });

                boolean found = false;
                for (CartTM tm1 : obList) {
                    // if item exist in cart
                    if (tm1.getItemCode().equals(tm.getItemCode())) {
                        found = true;
                        if (!tm.getDiscount().equals(tm1.getDiscount())) {
                            new Alert(Alert.AlertType.WARNING, "please check the discount!").showAndWait();
                            return;
                        } else {
                            tm1.setQty(tm.getQty() + tm1.getQty());
                            tm1.setTotalPrice(tm.getTotalPrice() + tm1.getTotalPrice());
                            tblCart.refresh();
                            break;
                        }
                    }
                }

                if (!found) {
                    obList.add(tm);
                }

                // reducing items
                for (ItemDto itemDto : itemsToChoose) {
                    if (itemDto.getCode().equals(tm.getItemCode())) {
                        int reduced = itemDto.getQtyOnHand() - tm.getQty();
                        itemDto.setQtyOnHand(reduced);
                        lblPOQtyHand.setText(String.valueOf(reduced));
                    }
                }

            } catch (Throwable t) {
                throw t;
            }
        } else new Alert(Alert.AlertType.WARNING, "non-computable values!").show();
        calculateTotal();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (CartTM tm : obList)
            total = total.add(BigDecimal.valueOf(tm.getTotalPrice()));
        {

        }
        lblTotal.setText("Total: " + total);
    }

    //Set Date
    private void loadDate() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        if (!obList.isEmpty() & cmbPOCusId.getSelectionModel().getSelectedIndex() != -1) {
            String oId = lblOrderId.getText();
            String cId = cmbPOCusId.getValue().toString();
            OrderDTO orderDTO = new OrderDTO(oId, cId);
            ArrayList<OrderDetailDTO> dtoLst = new ArrayList<>();
            for (CartTM tdm : obList) {
                dtoLst.add(new OrderDetailDTO(oId, tdm.getItemCode(), tdm.getQty(), tdm.getDiscount(), tdm.getTotalPrice()));
            }
            try {
                if (purchaseOrderBO.purchaseOrder(orderDTO, dtoLst)) {
                    new Alert(Alert.AlertType.INFORMATION, "order saved successfully!").show();
                    lblOrderId.setText(purchaseOrderBO.genarateNewOrderID());
                    obList.clear();
                    lblTotal.setText("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "interrupted").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "order cannot be saved \n error : " + e.getMessage());
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "please check whether\n-> Customer is selected\n-> If the cart is empty").show();
        }
    }
}
