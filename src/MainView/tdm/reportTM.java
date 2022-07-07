package MainView.tdm;

public class reportTM {
    private String orderId;
    private String itemCode;
    private Integer qty;
    private Double discount;
    private Double totalPrice;

    public reportTM() {
    }

    public reportTM(String orderId, String itemCode, Integer qty, Double discount, Double totalPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public reportTM(String orderId, String itemCode, Integer qty, Double discount) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "reportTM{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
