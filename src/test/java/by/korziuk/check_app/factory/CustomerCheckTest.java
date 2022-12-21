package by.korziuk.check_app.factory;

import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

class CustomerCheckTest {

    @Test
    @DisplayName("should check the conversion of an array to a map")
    public void handle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String[].class};
        Method method = CustomerCheck.class.getDeclaredMethod("handle", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        String[] inputData = {"3-1", "2-5", "5-1", "card-1234"};
        //When
        HashMap<Integer, Integer> resultMap = (HashMap) method.invoke(customerCheck, (Object) inputData);
        //Then
        assertThat(resultMap.size()).isEqualTo(4);
        assertThat(resultMap.containsKey(0)).isTrue();
        assertThat(resultMap.containsValue(1234)).isTrue();
        assertThat(resultMap.get(5)).isEqualTo(1);
    }

    @Test
    @DisplayName("should handle incorrect data and return size 1")
    public void should_handle_incorrect_data_and_return_size_1()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String[].class};
        Method method = CustomerCheck.class.getDeclaredMethod("handle", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        String[] inputData = {"card-12349"};
        //When
        HashMap<Integer, Integer> resultMap = (HashMap) method.invoke(customerCheck, (Object) inputData);
        //Then
        assertThat(resultMap.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("should handle null empty data and return null")
    public void should_handle_null_empty_data_and_return_null()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String[].class};
        Method method = CustomerCheck.class.getDeclaredMethod("handle", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        String[] inputData = new String[]{};
        HashMap<Integer, Integer> resultMap1 = (HashMap) method.invoke(customerCheck, (Object) inputData);
        HashMap<Integer, Integer> resultMap2 = (HashMap) method.invoke(customerCheck, (Object) inputData);
        //When
        //Then
        assertThat(resultMap1).isNull();
        assertThat(resultMap2).isNull();
    }

    @Test
    public void handleDataAsProduct()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String.class};
        Method method = CustomerCheck.class.getDeclaredMethod("handleDataAsProduct", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        String data = customerCheck.readFile("products.txt");
        ArrayList<Product> products = (ArrayList<Product>) method.invoke(customerCheck, data);
        //Then
        assertThat(products.size()).isEqualTo(11);
        assertThat(products.get(3).getDescription()).isEqualTo("pomodoro super");
        assertThat(products.get(0).getId()).isEqualTo(1);
    }

    @Test
    public void handleDataAsCard()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String.class};
        Method method = CustomerCheck.class.getDeclaredMethod("handleDataAsCard", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        String data = customerCheck.readFile("cards.txt");
        ArrayList<Card> cards = (ArrayList<Card>) method.invoke(customerCheck, data);
        //Then
        assertThat(cards.size()).isEqualTo(6);
        assertThat(cards.get(3).getHolderLastName()).isEqualTo("Morgan");
        assertThat(cards.get(0).getId()).isEqualTo(1234);
    }

    @Test
    public void hasInputDataFileNames() {
        //Given
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        boolean isDataPresent = customerCheck.hasInputDataFileNames(new String[]{"products.txt", "cards.txt", "input.txt"});
        //Then
        assertThat(isDataPresent).isTrue();
    }



    @Test
    public void hasError() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String[].class};
        Method method = CustomerCheck.class.getDeclaredMethod("hasError", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        String[] array = new String[]{"1-2", "2-4", "5-4", "1-2", "card-1234"};
        //Then
        assertThat((boolean) method.invoke(customerCheck, (Object) array)).isEqualTo(false);
        assertThat((boolean) method.invoke(customerCheck, (Object) new String[]{"card-1234", "1-2d"})).isEqualTo(true);
        assertThat((boolean) method.invoke(customerCheck, (Object) new String[]{"5-5", "1-2d"})).isEqualTo(true);
        assertThat((boolean) method.invoke(customerCheck, (Object) new String[]{"12-d"})).isEqualTo(true);
    }

    @Test
    public void hasSameData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String[].class};
        Method method = CustomerCheck.class.getDeclaredMethod("hasSameData", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        String[] array = new String[]{"1-2", "2-4", "5-4", "1-2", "card-1234"};
        //Then
        assertThat((boolean) method.invoke(customerCheck, (Object) array)).isEqualTo(true);
    }

    @Test
    public void isInteger() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{String.class};
        Method method = CustomerCheck.class.getDeclaredMethod("isInteger", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        //Then
        assertThat((boolean) method.invoke(customerCheck, "1")).isEqualTo(true);
        assertThat((boolean) method.invoke(customerCheck, "1a2")).isEqualTo(false);
        assertThat((boolean) method.invoke(customerCheck, "fgh 2")).isEqualTo(false);
        assertThat((boolean) method.invoke(customerCheck, "")).isEqualTo(false);
    }

    @Test
    public void calculateDiscount() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{int.class};
        Method method = CustomerCheck.class.getDeclaredMethod("calculateDiscount", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        //Then
        assertThat((int) method.invoke(customerCheck, 1)).isEqualTo(0);
        assertThat((int) method.invoke(customerCheck, 5)).isEqualTo(0);
        assertThat((int) method.invoke(customerCheck, 6)).isEqualTo(10);
    }

    @Test
    public void calculateTotal() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{int.class, BigDecimal.class};
        Method method = CustomerCheck.class.getDeclaredMethod("calculateTotal", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        BigDecimal result = (BigDecimal) method.invoke(customerCheck, 3, new BigDecimal("10.13"));
        //Then
        assertThat(result).isEqualTo("30.39");
    }

    @Test
    public void calculateDiscont() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{BigDecimal.class, int.class};
        Method method = CustomerCheck.class.getDeclaredMethod("calculateDiscont", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        BigDecimal result = (BigDecimal) method.invoke(customerCheck, new BigDecimal("10.00"), 5);
        //Then
        assertThat(result).isEqualTo("9.50");
    }

    @Test
    public void getDiscont() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Class[] args = new Class[]{BigDecimal.class, int.class};
        Method method = CustomerCheck.class.getDeclaredMethod("getDiscont", args);
        method.setAccessible(true);
        CustomerCheck customerCheck = new CustomerCheck();
        //When
        BigDecimal result = (BigDecimal) method.invoke(customerCheck, new BigDecimal("10.00"), 5);
        //Then
        assertThat(result).isEqualTo("0.50");
    }
}
