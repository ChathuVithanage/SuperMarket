package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBo extends SuperBo {


    ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException;

    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException;

    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    public String genarateNewItemCode() throws SQLException, ClassNotFoundException;

    public ItemDto search(String s) throws SQLException, ClassNotFoundException;

    public ItemDto find(String code) throws SQLException, ClassNotFoundException;
}
