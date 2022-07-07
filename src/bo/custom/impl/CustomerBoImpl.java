package bo.custom.impl;
import bo.custom.CustomerBo;
import dao.DAOFactory;
import dao.custom.CustomerDao;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBo {

    private final CustomerDao customerDAO = (CustomerDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    //CustomerDao customerDao=new CustomerDaoImpl();
    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDto> allCustomers= new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDto(customer.getId(),customer.getTitle(),customer.getName(),customer.getAddress(),customer.getCity(),
                    customer.getProvince(),customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getId(),dto.getTitle(),dto.getName(),dto.getAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean existCustomer(String s){
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return  customerDAO.update(new Customer(dto.getId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(),
                dto.getPostalCode()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
       return  customerDAO.delete(id);
    }

    @Override
    public String genarateNewCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.genarateId();
    }

    @Override
    public CustomerDto search(String s) throws SQLException, ClassNotFoundException {

        Customer c = customerDAO.search(s);
        if(c!=null){
            return new CustomerDto(c.getId(),c.getTitle(),c.getName(),c.getAddress(),c.getCity(),c.getProvince(),c.getPostalCode());
        }
        return null;
      //return  CustomerDto.search(s);
    }
}
