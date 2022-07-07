package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDao;
import dao.custom.ItemDao;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.impl.OrderDetailsDAOImpl;
import db.DBConnection;
import dto.*;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/
public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private final OrderDetailsDAOImpl orderDetailsDAO = new OrderDetailsDAOImpl();

    //Hiding the object creation logic using the Factory pattern
    private final CustomerDao customerDAO = (CustomerDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);// hide the object creation logic through the factory
    private final ItemDao itemDAO = (ItemDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    //private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    //private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);


    @Override
    public boolean cancelOrder(String oid, ArrayList<CustomOrderDto> orderDTOS) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        boolean save;
        // updating qtyOnHand in items table
        for (CustomOrderDto dto : orderDTOS) {
            Item item = itemDAO.search(dto.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand()+dto.getOrderQty());
            save = itemDAO.update(item);
            if(!save){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        save = orderDAO.delete(oid);
        if(!save){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public boolean purchaseOrder(OrderDTO dto, ArrayList<OrderDetailDTO> dtos) throws SQLException, ClassNotFoundException {

        //Transaction
        Connection connection = DBConnection.getDbConnection().getConnection();

        if (orderDAO.exist(dto.getOrderID())) {

        }
        connection.setAutoCommit(false);
        boolean save = orderDAO.save(new Orders(dto.getOrderID(), dto.getCustomerID()));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detailDTO : dtos) {
            boolean save1 = orderDetailsDAO.save(new OrderDetails(detailDTO.getOid(), detailDTO.getItemCode(), detailDTO.getQty(), detailDTO.getDiscount(),detailDTO.getTotalPrice()));
            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDto item = searchItem(detailDTO.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getQty());

            //update item
            boolean update = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getDiscount(), item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;

            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean updateOrder(ArrayList<CustomOrderDto> orderDTOS,ArrayList<CustomOrderDto> removeOrderDTOS, String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        for (CustomOrderDto dto : orderDTOS) {
            if(!orderDetailsDAO.update(new OrderDetails(id,dto.getItemCode(),dto.getOrderQty(),dto.getDiscount(),dto.getTotalPrice()))){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        for (CustomOrderDto dto : removeOrderDTOS) {
            if(!orderDetailsDAO.deleteCustomOrder(id,dto.getItemCode())){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDto(ent.getId(), ent.getName());
    }

    @Override
    public ItemDto searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDto(ent.getCode(), ent.getDescription(), ent.getPackSize(), ent.getUnitPrice(), ent.getDiscount(), ent.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String genarateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.genarateId();
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDto> allCustomers = new ArrayList<>();
        for (Customer ent : all) {
            allCustomers.add(new CustomerDto(ent.getId(), ent.getName()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDto> allItems = new ArrayList<>();
        for (Item ent : all) {
            allItems.add(new ItemDto(ent.getCode(), ent.getDescription(), ent.getPackSize(), ent.getUnitPrice(), ent.getDiscount(), ent.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public ArrayList<ReportDto> generateReport(int code) throws SQLException, ClassNotFoundException {
        ArrayList<CustomOrder> rpt = orderDetailsDAO.generateReport(code);
        ArrayList<ReportDto> report = new ArrayList<>();
        if(rpt!=null){
            for (CustomOrder customOrder : rpt) {
                report.add(new ReportDto(customOrder.getItemCode(),customOrder.getTotalPrice(),customOrder.getOrderQty()));
            }
            return report;
        }
        return null;
    }

    @Override
    public ArrayList<CustomOrderDto> getOrderDetailsFiltered(String oid) throws SQLException, ClassNotFoundException {
        ArrayList<CustomOrder> orderDetails = orderDetailsDAO.getAllOrdersFiltered(oid);
        ArrayList<CustomOrderDto> dtOs = new ArrayList<>();
        for (CustomOrder cod : orderDetails) {
            dtOs.add(new CustomOrderDto(cod.getItemCode(),cod.getDescription(),cod.getOrderQty(),cod.getQtyOnHand(),cod.getDiscount(),cod.getTotalPrice()));
        }
        return dtOs;
    }

}
