package by.korziuk.check_app.factory;

import by.korziuk.check_app.builder.Item;
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

    }
}
