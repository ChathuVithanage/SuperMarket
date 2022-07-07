package bo.custom;

import bo.SuperBo;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SystemReportBo extends SuperBo {
    ArrayList<OrderDetailDTO> mostMovableItem() throws Exception,ClassNotFoundException;
    ArrayList<OrderDetailDTO> leastMovableItem() throws Exception,ClassNotFoundException;
    ArrayList<OrderDTO> getAllOrderByDaily(String date) throws SQLException, ClassNotFoundException;
}
