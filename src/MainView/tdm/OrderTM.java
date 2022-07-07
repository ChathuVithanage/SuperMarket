package MainView.tdm;

public class OrderTM {
    private String itemcode;
    private String description;
    private Integer qty;
    private Integer qtyOnHand;
    private Double discount;
    private Double Total;

    public OrderTM(String itemcode, Integer qty) {
        this.itemcode = itemcode;
        this.qty = qty;
    }

    public OrderTM() {
    }

    public OrderTM(String itemcode, String description, Integer qty, Integer qtyOnHand, Double discount, Double total) {
        this.itemcode = itemcode;
        this.description = description;
        this.qty = qty;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
        Total = total;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "itemcode='" + itemcode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", qtyOnHand=" + qtyOnHand +
                ", discount=" + discount +
                ", Total=" + Total +
                '}';
    }
}
