package entity;

public class Item {
    private String code;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Double discount;
    private Integer qtyOnHand;

    public Item(String code, String description, String packSize, Double unitPrice, Double discount, Integer qtyOnHand) {
        this.code = code;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.qtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}

