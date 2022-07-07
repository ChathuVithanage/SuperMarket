package dao.custom.impl;

import CrudUtil.CrudUtil;
import dao.custom.OrderDAO;
import entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDAOImpl implements OrderDAO{

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,NOW(),?)", entity.getOid(), entity.getCustomerID());
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Orders` WHERE oid=?", oid);
    }

    @Override
    public String genarateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }

}
