package by.korziuk.check_app.data;

import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<Product> productCollection;
    private final List<Card> cardCollection;

    public Data() {
        productCollection = new ArrayList<>();
        cardCollection = new ArrayList<>();
        initData();
    }

    private void initData() {
        productCollection.add(new Product(1, "potato", "potato super", new BigDecimal("1.40")));
        productCollection.add(new Product(2, "potato", "potato mini", new BigDecimal("0.99")));
        productCollection.add(new Product(3, "potato", "potato maxi", new BigDecimal("2.01")));
        productCollection.add(new Product(4, "pomodoro", "pomodoro super", new BigDecimal("8.90")));
        productCollection.add(new Product(5, "pomodoro", "pomodoro mini", new BigDecimal("9.00")));
        productCollection.add(new Product(6, "pomodoro", "pomodoro maxi", new BigDecimal("5.40")));
        productCollection.add(new Product(7, "apple", "apple green", new BigDecimal("4.70")));
        productCollection.add(new Product(8, "banana", "banana yellow", new BigDecimal("5.60")));
        productCollection.add(new Product(9, "pesca", "pesca italy", new BigDecimal("9.60")));
        productCollection.add(new Product(10, "fikidindia", "fikidindia small", new BigDecimal("8.99")));

        cardCollection.add(new Card(1234, "Ivan", "Petrov", 7));
        cardCollection.add(new Card(4428, "Petr", "Ivanov", 5));
        cardCollection.add(new Card(8640, "Dmitry", "Sidorov", 1));
        cardCollection.add(new Card(7854, "Pol", "Morgan", 3));
        cardCollection.add(new Card(1211, "Ron", "Grieen", 0));
    }

    public List<Product> getProductCollection() {
        return productCollection;
    }

    public List<Card> getCardCollection() {
        return cardCollection;
    }
}
