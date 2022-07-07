package bo.custom;

import bo.SuperBo;
import dto.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/

public interface PurchaseOrderBO extends SuperBo {
    boolean cancelOrder(String oid, ArrayList<CustomOrderDto> orderDTOS) throws SQLException, ClassNotFoundException;

    boolean purchaseOrder(OrderDTO dto, ArrayList<OrderDetailDTO> dtos) throws SQLException, ClassNotFoundException;

    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ItemDto searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException;

    boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException;

    String genarateNewOrderID() throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

    ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException;

    public ArrayList<ReportDto> generateReport(int code) throws  SQLException, ClassNotFoundException;

    public ArrayList<CustomOrderDto> getOrderDetailsFiltered(String oid) throws SQLException, ClassNotFoundException;

    public boolean updateOrder(ArrayList<CustomOrderDto> orderDTOS,ArrayList<CustomOrderDto> removeOrderDTOS, String id) throws SQLException, ClassNotFoundException;

}
