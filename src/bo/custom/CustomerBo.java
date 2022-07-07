package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBo {

    ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException;

    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    public String genarateNewCustomerId() throws SQLException, ClassNotFoundException;

    public CustomerDto search(String s) throws SQLException, ClassNotFoundException;
}
