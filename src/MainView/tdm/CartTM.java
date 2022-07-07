package MainView.tdm;

import javafx.scene.control.Button;

public class CartTM {
    private String itemCode;
    private Double unitPrice;
    private Integer qty;
    private Double discount;
    private Double totalPrice;
    private Button delete = new Button("delete");

    public CartTM() {
    }

    public CartTM(String itemCode, Double unitPrice, Integer qty, Double discount, Double totalPrice) {
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.discount = discount;
        this.totalPrice = totalPrice;

        delete.setOnAction(e->{

        });
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", delete=" + delete +
                '}';
    }
}
