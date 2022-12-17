package by.korziuk.check_app.builder;

public class ItemBuilder {
    private int quantity = 1;
    private int productId = 0;
    private int discount = 0;
    private int cardNumber = 0;

    public ItemBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ItemBuilder setProduct(int productId) {
        this.productId = productId;
        return this;
    }

    public ItemBuilder setdDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public ItemBuilder setCard(int cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public Item build() {
        Item item = new Item();
        item.setQuantity(quantity);
        item.setProductId(productId);
        item.setProduct(productId);
        item.setDiscount(discount);
        item.setCardNumber(cardNumber);
        return item;
    }
}
