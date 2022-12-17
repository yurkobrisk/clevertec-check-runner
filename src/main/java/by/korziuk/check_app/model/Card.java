package by.korziuk.check_app.model;

public class Card {

    private int id;
    private String holderName;
    private String holderLastName;
    private int discount;

    public Card() {
    }

    public Card(int id, String holderName, String holderLastName, int discount) {
        this.id = id;
        this.holderName = holderName;
        this.holderLastName = holderLastName;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderLastName() {
        return holderLastName;
    }

    public void setHolderLastName(String holderLastName) {
        this.holderLastName = holderLastName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", holderLastName='" + holderLastName + '\'' +
                ", discount=" + discount +
                '}';
    }
}
