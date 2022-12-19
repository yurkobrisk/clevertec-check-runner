package by.korziuk.check_app.factory;

import by.korziuk.check_app.builder.Item;
import by.korziuk.check_app.builder.ItemBuilder;
import by.korziuk.check_app.model.Card;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                        .setDiscount(0)
                        .setCard(value)
                        .build());
            } else { // product
                items.add(new ItemBuilder()
                        .setQuantity(value)
                        .setProduct(key)
                        .setDiscount(calculateDiscount(value))
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
        //ToDo create method return String
        System.out.println(createView());
        //ToDo create method save to file


    }

    /**
     * Method create view for check
     * @return check view in text format
     */
    public StringBuilder createView() {
        BigDecimal total = new BigDecimal("0.00");
        StringBuilder resultCheck = new StringBuilder("\n");
        resultCheck.append(String.format("%4s  %-20s  %8s  %8s\n", "QTY", "DESCRIPTION", "PRICE", "TOTAL"));

        for (Item item : items) {
            BigDecimal totalItemPrice, discountItemPrice = new BigDecimal("0.00");
            if (item.getProduct() != null) {
                resultCheck.append(String.format("%4s  %-20s  %8s  %8s\n",
                        item.getQuantity(),
                        item.getProduct().getDescription(),
                        item.getProduct().getPrice(),
                        totalItemPrice = calculateTotal(item.getQuantity(), item.getProduct().getPrice())));
                if (item.getDiscount() > 0) {
                    resultCheck.append(String.format("                         discount:%4s%8s\n",
                            item.getDiscount() + "%",
                            discountItemPrice = (getDiscont(totalItemPrice, item.getDiscount()))));
                }

                total = total.add(totalItemPrice).subtract(discountItemPrice);
            }
            if (item.getCardNumber() != 0) {
                card = item.getCard();
            }
        }

        resultCheck.append("==============================================\n");

        if (card != null) {
            resultCheck.append(String.format("                discount card-%-6s%2s%8s\n",
                    card.getId(),
                    card.getDiscount() + "%",
                    getDiscont(total, card.getDiscount())));
            resultCheck.append(String.format("TOTAL %40s", total.subtract(getDiscont(total, card.getDiscount()))));
            return resultCheck;
        }
        resultCheck.append(String.format("TOTAL %40s", total));
        return resultCheck;
    }

    /**
     * Method calculate total price for product
     * @param quantity of roducts
     * @param price of product
     * @return total price for product
     */
    private BigDecimal calculateTotal(int quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }

    /**
     * Method calculate discount for product
     * @param total product price
     * @param discount for product in %
     * @return discount = total * discount / 100%
     */
    private BigDecimal calculateDiscont(BigDecimal total, int discount) {
        BigDecimal totalWithDiscount = total
                .multiply(new BigDecimal(discount))
                .divide(new BigDecimal("100"), RoundingMode.DOWN);
        return total.subtract(totalWithDiscount);
    }

    private BigDecimal getDiscont(BigDecimal total, int discount) {
        return total
                .multiply(new BigDecimal(discount))
                .divide(new BigDecimal("100"), RoundingMode.DOWN);
    }
}
