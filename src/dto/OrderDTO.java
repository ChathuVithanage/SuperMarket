package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



public class OrderDTO  {

    private String OrderID;
    private String CustomerID;

    public OrderDTO(String orderID, String customerID) {
        OrderID = orderID;
        CustomerID = customerID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }


    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

}
