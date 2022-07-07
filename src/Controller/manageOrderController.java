package Controller;

import MainView.tdm.OrderTM;
import bo.BOFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderDetailsBo;
import bo.custom.PurchaseOrderBO;
import dto.*;
import entity.Orders;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class manageOrderController {
    public ComboBox cmbSOrderId;
    public TableView tblOrder;
    public TableColumn colSItemCode;
    public TableColumn colSDescription;
    public TableColumn colSPrice;
    public TableColumn colSQty;
    public TableColumn colSQtyHand;
    public TableColumn colSDiscount;
    public TableColumn colSTotal;
    
    public TextField txtSQty;
    public TextField txtSDiscount;
    public TextField txtSTotal;
    public ComboBox cmbSCusId;
    public AnchorPane mainPane;

    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    OrderDetailsBo orderDetailsBo = (OrderDetailsBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERDETAILS);
    ItemBo itemBo= (ItemBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    CustomerBo customerBo = (CustomerBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public ObservableList<OrderTM> itemList = FXCollections.observableArrayList();

    public ObservableList<OrderTM> removedItemList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllCustomers();

        colSItemCode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colSDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSQtyHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colSDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colSTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrder.setItems(itemList);
    }

    public void loadAllCustomers() throws SQLException, ClassNotFoundException {
        cmbSCusId.getSelectionModel().clearSelection();
        cmbSCusId.getItems().clear();
        ArrayList<CustomerDto> arrayList = purchaseOrderBO.getAllCustomers();
        for (CustomerDto customerDto : arrayList
        ) {
            cmbSCusId.getItems().add(customerDto.getId());
        }
    }

    public void removeOrderOnAction(ActionEvent actionEvent) {
        String oid = cmbSOrderId.getValue().toString();
        if(oid!=null) {
            ArrayList<CustomOrderDto> orderDTOS = new ArrayList<>();
            for (OrderTM tdm : itemList) {
                orderDTOS.add(new CustomOrderDto(tdm.getItemcode(), tdm.getQty()));
            }
            try {
                if (purchaseOrderBO.cancelOrder(oid, orderDTOS)) {
                    new Alert(Alert.AlertType.INFORMATION, "order deleted successfully").show();
                    itemList.clear();
                    removedItemList.clear();
                    cmbSOrderId.getItems().remove(cmbSOrderId.getSelectionModel().getSelectedItem());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else new Alert(Alert.AlertType.WARNING,"Select an order first!").show();

    }

    public void cmbSCusIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //cmbSCusId.getItems().clear();
        cmbSOrderId.getItems().clear();
        ArrayList <OrderDTO> allOrder = orderDetailsBo.getAllOrdersByCusId(String.valueOf(cmbSCusId.getValue()));
        for (OrderDTO order:allOrder
             ) {
            cmbSOrderId.getItems().add(order.getOrderID());
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

    public void cmbOrderIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!itemList.isEmpty()){
            itemList.clear();
        }
        if(!removedItemList.isEmpty()){
            removedItemList.clear();
        }
        if(!cmbSOrderId.getSelectionModel().isEmpty()) {
            for (CustomOrderDto dto : purchaseOrderBO.getOrderDetailsFiltered(cmbSOrderId.getValue().toString())) {
                itemList.add(new OrderTM(dto.getItemCode(), dto.getDescription(), dto.getOrderQty(), dto.getQtyOnHand(), dto.getDiscount(), dto.getTotalPrice()));
            }
        }
    }

    public void UpdateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrderTM tdm = (OrderTM) tblOrder.getSelectionModel().getSelectedItem();
        Integer qty = 0;
        Double dis = 0.0;
        if(tdm!=null) {
            try {
                qty = Integer.parseInt(txtSQty.getText());
                dis = Double.parseDouble(txtSDiscount.getText());
            } catch (Throwable f) {
                if (f instanceof NumberFormatException) {
                    new Alert(Alert.AlertType.WARNING, "Please enter a valid amount").showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING, f.getMessage()).showAndWait();
                }
                return;
            }
            Double maxDiscount = itemBo.find(tdm.getItemcode()).getDiscount();
            if (dis > maxDiscount | dis < 0) {
                new Alert(Alert.AlertType.WARNING, "Discount has exceeded maximum or invalid!").showAndWait();
                return;
            }
            if (qty > tdm.getQtyOnHand() | qty < 0) {
                new Alert(Alert.AlertType.WARNING, "Quantity has exceeded maximum or uncountable!").showAndWait();
                return;
            }
            for (OrderTM item : itemList) {
                if (item.getItemcode().equals(tdm.getItemcode())) {
                    if (qty < item.getQty()) {
                        item.setQtyOnHand(item.getQtyOnHand() + (item.getQty() - qty));
                    } else {
                        item.setQtyOnHand(item.getQtyOnHand() - (qty - item.getQty()));
                    }
                    item.setQty(qty);
                    // updating the total price
                    ItemDto dto = itemBo.find(tdm.getItemcode());
                    // u-price
                    System.out.println(dto.getUnitPrice());
                    item.setTotal((qty * dto.getUnitPrice()) - (dis * qty));
                    item.setDiscount(dis);
                    new Alert(Alert.AlertType.INFORMATION, "updated!").show();
                    break;
                }
            }
            tblOrder.refresh();
        }else new Alert(Alert.AlertType.WARNING,"Please select an item!").show();
        txtSQty.clear();
        txtSDiscount.clear();
    }

    public void updateOrderOnAction(ActionEvent actionEvent) {
        String oId = cmbSOrderId.getValue().toString();
        if(oId!=null) {
            ArrayList<CustomOrderDto> orderDTOS = new ArrayList<>();
            ArrayList<CustomOrderDto> removeOrderDTOS = new ArrayList<>();
            if (!itemList.isEmpty()) {
                for (OrderTM tdm : itemList) {
                    orderDTOS.add(new CustomOrderDto(tdm.getItemcode(), tdm.getQty(), tdm.getQtyOnHand(), tdm.getDiscount(), tdm.getTotal()));
                }
            }
            if (!removedItemList.isEmpty()) {
                for (OrderTM rmvTDM : removedItemList) {
                    removeOrderDTOS.add(new CustomOrderDto(rmvTDM.getItemcode(), rmvTDM.getQty(), rmvTDM.getQtyOnHand(), rmvTDM.getDiscount(), rmvTDM.getTotal()));
                }
            }
            try {
                if (purchaseOrderBO.updateOrder(orderDTOS, removeOrderDTOS, oId)) {
                    new Alert(Alert.AlertType.INFORMATION, "updated!").show();
                    itemList.clear();
                    removedItemList.clear();
                    cmbSOrderId.getSelectionModel().clearSelection();
                } else {
                    new Alert(Alert.AlertType.ERROR, "not updated properly! try again....").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "interrupted\nerror : " + e.getMessage()).show();
            }
        }else new Alert(Alert.AlertType.WARNING,"Select an order first!").show();
    }

    public void removeItemOnAction(ActionEvent actionEvent) {
        OrderTM tdm = (OrderTM) tblOrder.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            itemList.remove(tdm);
            removedItemList.add(tdm);
        } else new Alert(Alert.AlertType.WARNING,"Please select an item").show();
    }
}
