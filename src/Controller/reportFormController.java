package Controller;

import MainView.tdm.reportTM;
import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import bo.custom.SystemReportBo;
import dto.OrderDetailDTO;
import dto.ReportDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class reportFormController {

    public TableView tblMovableItem;
    public TableColumn colMOrderId;
    public TableColumn colMItemCode;
    public TableColumn colMQty;
    public TableColumn colMDiscount;
    public BarChart barChartForReports;
    public AnchorPane mainPane;
    public Label txtTotal;
    public Label txtSale;
    SystemReportBo systemReportBo = (SystemReportBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SYSTEMREPORT);

    private final PurchaseOrderBO orderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    private static ObservableList<XYChart.Series<String,Number>> barChartData = FXCollections.observableArrayList();

    public void initialize(){

        barChartForReports.setData(barChartData);
        barChartData.clear();

        colMOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colMItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colMQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colMDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
    }

    private void generateReport(int n) throws SQLException, ClassNotFoundException {

        int Items = 0;
        double Price = 0.0;

        ArrayList<ReportDto> report =  orderBO.generateReport(n);
        if(!report.isEmpty()){
            //pieChartData.clear();
            barChartData.clear();
            XYChart.Series sales = new XYChart.Series<String,Number>();
            sales.setName("sales");
            barChartData.add(sales);
            for (ReportDto dto : report) {
                //pieChartData.add(new PieChart.Data(dto.getItemCode(),dto.getTotalItemsSold()));
                barChartData.get(0).getData().add(new XYChart.Data<>(dto.getItemCode(), dto.getTotalEarned()));
                Items+=dto.getTotalItemsSold();
                Price+=dto.getTotalEarned();
            }
        }else new Alert(Alert.AlertType.WARNING,"no records to generate a report!").showAndWait();
        txtTotal.setText(String.valueOf(Items));
        txtSale.setText(String.valueOf(Price));
    }

    public void btnLeastOnAction(ActionEvent actionEvent) {
        tblMovableItem.getItems().clear();
        try {
            ArrayList<OrderDetailDTO> orderDetailDTOS = systemReportBo.leastMovableItem();
            for (OrderDetailDTO dto:orderDetailDTOS
                 ) {
                tblMovableItem.getItems().add(new reportTM(dto.getOid(),dto.getItemCode(),dto.getQty(), dto.getDiscount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnMostOnAction(ActionEvent actionEvent) {

        tblMovableItem.getItems().clear();

        try {
            ArrayList<OrderDetailDTO> orderDetailDTOS = null;
            orderDetailDTOS = systemReportBo.mostMovableItem();

            for (OrderDetailDTO dto:orderDetailDTOS
            ) {
                tblMovableItem.getItems().add(new reportTM(dto.getOid(),dto.getItemCode(),dto.getQty(), dto.getDiscount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void dailyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        generateReport(0);
    }

    public void monthlyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        generateReport(1);
    }

    public void annuallyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        generateReport(2);
    }
}
