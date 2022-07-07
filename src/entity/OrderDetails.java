package entity;

public class OrderDetails {
    private String oid;
    private String itemCode;
    private Integer qty;
    private Double discount;
    private Double totalPrice;

    public OrderDetails(String oid, String itemCode, Integer qty, Double discount, Double totalPrice) {
        this.oid = oid;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
