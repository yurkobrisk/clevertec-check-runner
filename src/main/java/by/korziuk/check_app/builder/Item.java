package by.korziuk.check_app.builder;

import by.korziuk.check_app.model.Product;

public class Item {

    private int quantity;
    private int productId;
    private int discount;
    private int cardNumber;
    private Product product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(int productId) {
        //ToDo Get data from dataSet or DataBase
        this.product = null;
    }

    @Override
    public String toString() {
        return "Item{" +
                "quantity=" + quantity +
                ", productId=" + productId +
                ", discount=" + discount +
                ", cardNumber=" + cardNumber +
                ", product=" + product +
                '}';
    }
}
