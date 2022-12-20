package by.korziuk.check_app.builder;

import by.korziuk.check_app.exception.NoSuchCardException;
import by.korziuk.check_app.exception.NoSuchIdentifierException;

public class ItemBuilder {
    private int quantity = -1;
    private int productId = -1;
    private int discount = -1;
    private int cardNumber = -1;

    public ItemBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ItemBuilder setProduct(int productId) {
        this.productId = productId;
        return this;
    }

    public ItemBuilder setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public ItemBuilder setCard(int cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public Item build() throws NoSuchIdentifierException, NoSuchCardException {
        Item item = new Item();
        item.setQuantity(quantity);
        item.setProductId(productId);
        item.setProduct(productId);
        item.setDiscount(discount);
        item.setCardNumber(cardNumber);
        item.setCard(cardNumber);
        return item;
    }
}
