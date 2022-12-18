package by.korziuk.check_app.builder;

import by.korziuk.check_app.CheckRunner;
import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;

import java.util.Objects;

public class Item {

    private int quantity;
    private int productId;
    private int discount;
    private int cardNumber;
    private Product product;
    private Card card;

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
        this.product = CheckRunner.data.getProductCollection()
                .stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElse(null);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(int cardId) {
        this.card = CheckRunner.data.getCardCollection()
                .stream()
                .filter(card -> card.getId() == cardId)
                .findFirst()
                .orElse(null);
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
