package dao.custom.impl;

import CrudUtil.CrudUtil;
import dao.custom.ItemDao;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return  CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?,?)",
                entity.getCode(),entity.getDescription(),entity.getPackSize(),entity.getUnitPrice(),
                entity.getDiscount(),entity.getQtyOnHand());

    }

    @Override
    public boolean exist(String s) {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return  CrudUtil.executeUpdate("DELETE FROM Item WHERE code=?",s);
    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Item WHERE code=?", s);
        if(resultSet.next()){
            return new Item(
                    resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getDouble(4),resultSet.getDouble(5),resultSet.getInt(6));
        }
        return  null;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> arrayList=new ArrayList<>();
        while(rst.next()){
            arrayList.add(new Item(rst.getString(1), rst.getString(2),
                    rst.getString(3), rst.getDouble(4), rst.getDouble(5),
                    rst.getInt(6)));
        }
        return  arrayList ;
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item set description=?,packSize=?,unitPrice=?,discount=?,qtyOnHand=? WHERE code=?",
                dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getDiscount(),dto.getQtyOnHand(),dto.getCode());
    }

    @Override
    public String genarateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String code = rst.getString("code");
            int newItemCode = Integer.parseInt(code.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemCode);
        } else {
            return "I00-001";
        }
    }
}
