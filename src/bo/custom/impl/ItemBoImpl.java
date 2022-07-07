package bo.custom.impl;

import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import dao.DAOFactory;
import dao.custom.CustomerDao;
import dao.custom.ItemDao;
import dto.CustomerDto;
import dto.ItemDto;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl  implements ItemBo {

    private final ItemDao itemDao = (ItemDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    //CustomerDao customerDao=new CustomerDaoImpl();
    @Override
    public ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDao.getAll();
        ArrayList<ItemDto> allItems= new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDto(item.getCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getDiscount(),
                    item.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(dto.getCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(),
                dto.getDiscount(), dto.getQtyOnHand()));
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.exist(code);
    }

    public ItemDto find(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDao.search(code);
        return new ItemDto(item.getCode(),item.getPackSize(),item.getUnitPrice(),item.getDiscount());
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return  itemDao.update(new Item(dto.getCode(), dto.getDescription(), dto.getPackSize(),
                dto.getUnitPrice(), dto.getDiscount(), dto.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return  itemDao.delete(id);
    }

    @Override
    public String genarateNewItemCode() throws SQLException, ClassNotFoundException {
        return itemDao.genarateId();
    }

    @Override
    public ItemDto search(String s) throws SQLException, ClassNotFoundException {

        Item t = itemDao.search(s);
        if(t!=null){
            return new ItemDto(t.getCode(),t.getDescription(),t.getPackSize(),t.getUnitPrice(),t.getDiscount(),t.getQtyOnHand());
        }
        return null;
    }
}
