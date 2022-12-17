package by.korziuk.check_app.factory;

import by.korziuk.check_app.builder.Item;
import by.korziuk.check_app.builder.ItemBuilder;
import by.korziuk.check_app.model.Card;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<Integer, Integer> inputData = handle(data);

        System.out.println("input data converted into map ...");

        //ToDo iterate data and fill items list

        Item item = new ItemBuilder()
                .setQuantity(5)
                .setProduct(5)
                .setdDiscount(5)
                .setCard(1234)
                .build();
    }

    /**
     * Method handles input data, analyze, convert into Map
     * @param data input array, params
     * @return The Map with params
     */
    protected Map<Integer, Integer> handle(String[] data) {
        if (data == null) {
            return null;
        }
        if (hasError(data)) {
            System.out.println("Attention. Incorrect data present in the input array. Data were filtered.");
        }

        return Arrays.stream(data)
                .filter(str -> str.matches("(\\w{1,})-(\\d{1,})"))
                .map(item -> item.replace("card", "0").split("-"))
                .filter(array -> isInteger(array[0]) && isInteger(array[1]))
                .collect(Collectors.toMap(
                        element -> Integer.parseInt(element[0]),
                        element -> Integer.parseInt(element[1])
                ));
    }

    /**
     * Method checks is data has incorrect format. Correct format - [number]-[number] and card-[number]
     * @param data input String array
     * @return true if errors are present
     */
    private boolean hasError(String[] data) {
        return Arrays.stream(data)
                .filter(str -> str.matches("(\\w{1,})-(\\d{1,})"))
                .map(item -> item.replace("card", "0").split("-"))
                .filter(array -> isInteger(array[0]) && isInteger(array[1]))
                .count() != data.length;
    }

    /**
     * Method checks string represents an integer
     * @param str input String
     * @return true if string can be converted to integer without exception, else false
     */
    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
