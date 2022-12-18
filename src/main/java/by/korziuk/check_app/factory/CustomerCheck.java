package by.korziuk.check_app.factory;

import by.korziuk.check_app.builder.Item;
import by.korziuk.check_app.builder.ItemBuilder;
import by.korziuk.check_app.model.Card;

import java.util.ArrayList;
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

        // iterate data and fill items list
        items = new ArrayList<>();
        inputData.forEach((key, value) -> {
            if (key == 0) { // card promo
                items.add(new ItemBuilder()
                        .setQuantity(0)
                        .setProduct(0)
                        .setdDiscount(0)
                        .setCard(value)
                        .build());
            } else { // product
                items.add(new ItemBuilder()
                        .setQuantity(value)
                        .setProduct(key)
                        .setdDiscount(calculateDiscount(value))
                        .setCard(0)
                        .build());
            }
        });
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

    /**
     * Method calculate discount for product, by default 0, but if quantity more 5 discount 10%
     * @param quantity of the product
     * @return discount in %
     */
    private int calculateDiscount(int quantity) {
        if (quantity > 5) {
            return 10;
        } else return 0;
    }

    @Override
    public void printCheck() {
        System.out.println();
        System.out.printf("%4s  %-20s  %8s  %8s \n", "QTY", "DESCRIPTION", "PRICE", "TOTAL");
        items.stream()
                .filter(element -> element.getProduct() != null)
                .forEach(item -> System.out.printf("%4s  %-20s  %8s  %8s \n",
                item.getQuantity(),
                item.getProduct().getDescription(),
                item.getProduct().getPrice(),
                calculateTotal(item.getQuantity(), item.getProduct().getPrice(), item.getDiscount())));
    }

    private long calculateTotal(int quantity, long price, int discount) {
        return quantity * price - (quantity * price * discount / 100);
    }
}
