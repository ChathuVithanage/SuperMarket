package bo.custom.impl;

import CrudUtil.CrudUtil;
import bo.custom.OrderDetailsBo;
import dao.DAOFactory;
import dao.custom.OrderDetailsDAO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBoImpl implements OrderDetailsBo {

    OrderDetailsDAO orderDetailsDo= (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public ArrayList<OrderDetailDTO> getAllOrderdetails() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO>arrayList=new ArrayList<>();
        ArrayList<OrderDetails> all = orderDetailsDo.getAll();
        for (OrderDetails orderDetail : all) {
            arrayList.add(new OrderDetailDTO(orderDetail.getOid(),orderDetail.getItemCode(),orderDetail.getQty(),orderDetail.getDiscount(),orderDetail.getTotalPrice()));
        }
        return arrayList;
    }

    @Override
    public boolean saveOrderdetails(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existOrderdetails(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateOrderdetails(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteOrderdetails(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String genarateNewOrderCode() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetailDTO search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrdersByCusId(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM orders WHERE customerId=?", id);
        ArrayList<OrderDTO> allOrders=new ArrayList<>();
        while(resultSet.next()){
            OrderDTO order= new OrderDTO(resultSet.getString(1),
                    resultSet.getString(2)
            );

            allOrders.add(order);
        }
        return allOrders;
    }

}
