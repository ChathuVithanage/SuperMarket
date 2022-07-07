package dto;

public class CustomOrderDto {
    private String itemCode;
    private Integer orderQty;
    private Integer qtyOnHand;
    private Double discount;
    private Double totalPrice;
    private String description;

    public CustomOrderDto(String itemCode, Integer orderQty) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
    }

    public CustomOrderDto(String itemCode, Integer orderQty, Integer qtyOnHand, Double discount, Double totalPrice) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "CustomOrderDto{" +
                "itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                ", qtyOnHand=" + qtyOnHand +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
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

    public CustomOrderDto() {
    }

    public CustomOrderDto(String itemCode, String description, Integer orderQty, Integer qtyOnHand, Double discount, Double totalPrice) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.description = description;

    }
}
