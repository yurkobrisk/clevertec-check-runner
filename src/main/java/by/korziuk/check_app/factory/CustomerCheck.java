package by.korziuk.check_app.factory;

import by.korziuk.check_app.builder.Item;
import by.korziuk.check_app.builder.ItemBuilder;
import by.korziuk.check_app.model.Card;

import java.util.List;

public class CustomerCheck implements Check {
    private List<Item> items;
    private Card card;

    /**
     * Create Customer Check from data
     * @param data input arguments
     */
    @Override
    public void create(String[] data) {
        System.out.println("create customer check ...");

        //ToDo iterate data and fill items list

        Item item = new ItemBuilder()
                .setQuantity(5)
                .setProduct(5)
                .setdDiscount(5)
                .setCard(1234)
                .build();
    }
}
