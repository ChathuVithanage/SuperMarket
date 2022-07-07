package dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class OrderDetailDTO implements Serializable {

    private String oid;
    private String itemCode;
    private int qty;
    private Double Discount;
    private Double totalPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String oid, String itemCode, int qty, Double discount, Double totalPrice) {
        this.oid = oid;
        this.itemCode = itemCode;
        this.qty = qty;
        this.Discount = discount;
        this.totalPrice = totalPrice;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "oid='" + oid + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", Discount=" + Discount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
