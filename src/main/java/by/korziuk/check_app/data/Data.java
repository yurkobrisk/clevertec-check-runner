package by.korziuk.check_app.data;

import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;

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
        productCollection.add(new Product(1, "potato", "potato super", 140));
        productCollection.add(new Product(2, "potato", "potato mini", 99));
        productCollection.add(new Product(3, "potato", "potato maxi", 201));
        productCollection.add(new Product(4, "pomodoro", "pomodoro super", 890));
        productCollection.add(new Product(5, "pomodoro", "pomodoro mini", 900));
        productCollection.add(new Product(6, "pomodoro", "pomodoro maxi", 540));
        productCollection.add(new Product(7, "apple", "apple green", 470));
        productCollection.add(new Product(8, "banana", "banana yellow", 560));
        productCollection.add(new Product(9, "pesca", "pesca italy", 960));
        productCollection.add(new Product(10, "fikidindia", "fikidindia small", 899));

        cardCollection.add(new Card(1234, "Ivan", "Petrov", 0));
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
