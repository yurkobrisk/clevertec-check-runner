package by.korziuk.check_app.builder;

import by.korziuk.check_app.CheckRunner;
import by.korziuk.check_app.exception.NoSuchCardException;
import by.korziuk.check_app.exception.NoSuchIdentifierException;
import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;

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

    public void setProduct(int productId) throws NoSuchIdentifierException {
        // Get data from dataSet or DataBase
        if (productId == -1) {
            this.product = null;
        } else {
            this.product = CheckRunner.data.getProductCollection()
                    .stream()
                    .filter(product -> product.getId() == productId)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchIdentifierException("Product with id: " + productId + " not found."));
        }
    }

    public Card getCard() {
        return card;
    }

    public void setCard(int cardNumber) throws NoSuchCardException {
        // Get data from dataSet or DataBase
        if (cardNumber == -1) {
            this.card = null;
        } else {
            this.card = CheckRunner.data.getCardCollection()
                    .stream()
                    .filter(card -> card.getId() == cardNumber)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchCardException("Card with number: " + cardNumber + " not found."));
        }
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
