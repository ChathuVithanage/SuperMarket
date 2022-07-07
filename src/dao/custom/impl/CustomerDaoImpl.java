package dao.custom.impl;

import CrudUtil.CrudUtil;
import dao.custom.CustomerDao;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
      return  CrudUtil.executeUpdate("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",
                entity.getId(),entity.getTitle(),entity.getName(),entity.getAddress(),entity.getCity(),entity.getProvince(),entity.getPostalCode());

    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return  CrudUtil.executeUpdate("DELETE FROM Customer WHERE id=?",s);
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Customer WHERE id=?", s);
        if(resultSet.next()){
            return new Customer(
                    resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7)
            );
        }
        return  null;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> arrayList=new ArrayList<>();
        while(rst.next()){
            arrayList.add(new Customer(rst.getString(1), rst.getString(2),
                    rst.getString(3), rst.getString(4), rst.getString(5),
                    rst.getString(6), rst.getString(7)));
        }
         //return allCustomer;
        return  arrayList ;
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer set title=?,name=?,address=?,city=?,province=?,postalCode=? WHERE id=?",
                dto.getTitle(),dto.getName(),dto.getAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode(),dto.getId());
    }

    @Override
    public String genarateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }
}
