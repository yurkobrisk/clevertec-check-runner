package by.korziuk.check_app.factory;

import by.korziuk.check_app.CheckRunner;
import by.korziuk.check_app.builder.Item;
import by.korziuk.check_app.builder.ItemBuilder;
import by.korziuk.check_app.exception.IncorrectDataException;
import by.korziuk.check_app.exception.NoDataException;
import by.korziuk.check_app.exception.NoSuchCardException;
import by.korziuk.check_app.exception.NoSuchIdentifierException;
import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerCheck implements Check {
    private List<Item> items;
    private Card card;

    /**
     * Create Customer Check from data
     * @param data input arguments
     */
    @Override
    public void create(String[] data) throws IncorrectDataException, NoDataException {
//        create customer check ...
        if (data.length == 0) {
            throw new NoDataException(" ... no input data found!");
        } else if (hasInputDataFileNames(data)) {
            CheckRunner.data.setProductCollection(handleDataAsProduct(readFile(data[0])));
            CheckRunner.data.setCardCollection(handleDataAsCard(readFile(data[1])));
            String dataTxt = readFile(data[2]);
            data = dataTxt.split(" ");
        } else if (isFileName(data)) {
            String dataTxt = readFile(data[0]);
            data = dataTxt.split(" ");
        }


        Map<Integer, Integer> inputData = handle(data);
//        System.out.println("input data converted into map ...");

        // iterate data and fill items list
        items = new ArrayList<>();
        inputData.forEach((key, value) -> {
            if (key == 0) { // card promo
                try {
                    items.add(new ItemBuilder()
                            .setCard(value)
                            .build());
                } catch (NoSuchCardException | NoSuchIdentifierException e) {
                    System.out.println(e.getMessage());
                }
            } else { // product
                try {
                    items.add(new ItemBuilder()
                            .setQuantity(value)
                            .setProduct(key)
                            .setDiscount(calculateDiscount(value))
                            .build());
                } catch (NoSuchIdentifierException | NoSuchCardException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * Method checkes array is it a file name
     * @param data input string array
     * @return true if it`s just a simple file name
     */
    private boolean isFileName(String[] data) {
        if (data.length >1) {
            return false;
        } else {
            return data[0].matches("(\\w{1,}).txt");
        }
    }

    /**
     * Method checks is String array contains file names
     * @param data string array
     * @return true if data in array is file names
     */
    protected boolean hasInputDataFileNames(String[] data) {
        if (data.length != 3) {
            return false;
        } else {
            return Arrays.stream(data).filter(this::isFileName).count() == 3;
        }
    }

    /**
     * Method checks is input string same as a pattern file name
     * @param fileName string
     * @return true if it`s file name
     */
    protected boolean isFileName(String fileName) {
        return fileName.matches("(\\w{1,}).txt");
    }

    /**
     * Method read data from file
     * @param fileName input file name
     * @return data from file as a string
     */
    protected String readFile(String fileName) {
        String data = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            data = reader.lines().collect(Collectors.joining());
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
        } catch (IOException e) {
            System.out.printf("Can`t read data from file %s", fileName);
        }
        return data;
    }

    /**
     * Method handle input data to products
     * @param data input String
     * @return list of products
     */
    protected ArrayList<Product> handleDataAsProduct(String data) {
        //ToDo check data from file is correct
        return Arrays.stream(data.split(";"))
                .map(record -> record.split(","))
                .map(array -> new Product(
                        Integer.parseInt(array[0].trim()),
                        array[1].trim(),
                        array[2].trim(),
                        new BigDecimal(array[3].trim())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method handle input data to cards
     * @param data input String
     * @return list of cards
     */
    protected ArrayList<Card> handleDataAsCard(String data) {
        //ToDo check data from file is correct
        return Arrays.stream(data.split(";"))
                .map(record -> record.split(","))
                .map(array -> new Card(
                        Integer.parseInt(array[0].trim()),
                        array[1].trim(),
                        array[2].trim(),
                        Integer.parseInt(array[3].trim())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Method handles input data, analyze, convert into Map
     * @param data input array, params
     * @return The Map with params
     */
    protected Map<Integer, Integer> handle(String[] data) throws IncorrectDataException {
        if (data == null || Arrays.equals(data, new String[]{})) {
            return null;
        }
        if (hasError(data) || !hasSameData(data)) {
            throw new IncorrectDataException("Incorrect data present in the input array");
//            System.out.println("Attention. Incorrect data present in the input array. Data were filtered.");
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
     * Method check the same product Id is present in array
     * @param data input array
     * @return true if data hasn`t repeatable
     */
    private boolean hasSameData(String[] data) {
        List<String> lst = Arrays.stream(data)
                .map(element -> element.replaceAll("-(\\d+)", ""))
                .filter(item -> !item.equals("card"))
                .collect(Collectors.toCollection(ArrayList::new));
        Set<String> set = new HashSet<>(lst);
        return set.size() == lst.size();
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

    /**
     * Method writes check to file in current folder
     */
    @Override
    public void printCheck() {
        StringBuilder view = createView();
        System.out.println(view);

        File file = new File("check.txt");
        System.out.print("check writing to: " + file.getAbsolutePath());
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(view.toString());
            System.out.println(" ... file done.");
        } catch (IOException e) {
            System.out.println("File saves error ...");
        }
    }

    /**
     * Method create view for check
     * @return check view in text format
     */
    private StringBuilder createView() {
        if (items.size() == 0) {
            return new StringBuilder();
        }
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
            if (item.getCardNumber() != -1) {
                card = item.getCard();
            }
        }

        resultCheck.append("==============================================\n");

        if (card != null) {
            resultCheck.append(String.format("                discount card-%-6s%2s%8s\n",
                    card.getId(),
                    card.getDiscount() + "%",
                    getDiscont(total, card.getDiscount())));
            resultCheck.append(String.format("TOTAL %40s\n", total.subtract(getDiscont(total, card.getDiscount()))));
            return resultCheck;
        }
        resultCheck.append(String.format("TOTAL %40s\n", total));
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

    /**
     * Method calculate discount for product
     * @param total price
     * @param discount value
     * @return it`s a percent from total price
     */
    private BigDecimal getDiscont(BigDecimal total, int discount) {
        return total
                .multiply(new BigDecimal(discount))
                .divide(new BigDecimal("100"), RoundingMode.DOWN);
    }
}
