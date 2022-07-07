package dao.custom;

import dao.CrudDao;
import entity.CustomOrder;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/
public interface OrderDetailsDAO extends CrudDao<OrderDetails,String> {
    public ArrayList<CustomOrder> generateReport(int code) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomOrder(String oid, String itemCode) throws SQLException, ClassNotFoundException;
}
