package bo.custom;

import bo.SuperBo;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBo extends SuperBo {

    ArrayList<OrderDetailDTO> getAllOrderdetails() throws SQLException, ClassNotFoundException;

    public boolean saveOrderdetails(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existOrderdetails(String id) throws SQLException, ClassNotFoundException;

    public boolean updateOrderdetails(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteOrderdetails(String id) throws SQLException, ClassNotFoundException;

    public String genarateNewOrderCode() throws SQLException, ClassNotFoundException;

    OrderDetailDTO search(String id)throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDTO> getAllOrdersByCusId(String id)throws SQLException, ClassNotFoundException;
}
