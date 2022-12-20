package by.korziuk.check_app.factory;

import by.korziuk.check_app.exception.IncorrectDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CustomerCheckTest {

    @Test
    @DisplayName("should check the conversion of an array to a map")
    void handle() {
        //Given
        String[] inpuData = {"3-1", "2-5", "5-1", "card-1234"};
        //When
        Map<Integer, Integer> resultMap = null;
        try {
            resultMap = new CustomerCheck().handle(inpuData);
        } catch (IncorrectDataException e) {
            throw new RuntimeException(e);
        }
        //Then
        assertThat(resultMap.size()).isEqualTo(4);
        assertThat(resultMap.containsKey(0)).isTrue();
        assertThat(resultMap.containsValue(1234)).isTrue();
        assertThat(resultMap.get(5)).isEqualTo(1);
    }

    @Test
    @DisplayName("should handle incorrect data and return exception incorrect data")
    void should_handle_incorrect_data_and_return_exception_incorrect_data() {
        //Given
        String[] inpuData = {"3-a", "2b-5f", "5a-1", "car-1234d"};
        //When
        //Then
        assertThatThrownBy(() -> {
            new CustomerCheck().handle(inpuData);
        }).isInstanceOf(IncorrectDataException.class)
                .hasMessageContaining("Incorrect data present in the input array");
    }

    @Test
    @DisplayName("should handle incorrect data and return size 1")
    void should_handle_incorrect_data_and_return_size_1() {
        //Given
        String[] inpuData = {"card-12349"};
        //When
        Map<Integer, Integer> resultMap = null;
        try {
            resultMap = new CustomerCheck().handle(inpuData);
        } catch (IncorrectDataException e) {
            throw new RuntimeException(e);
        }
        //Then
        assertThat(resultMap.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("should handle null empty data and return null")
    void should_handle_null_empty_data_and_return_null() {
        //Given
        String[] inpuData = new String[]{};
        //When
        Map<Integer, Integer> resultMap1 = null;
        Map<Integer, Integer> resultMap2 = null;
        try {
            resultMap1 = new CustomerCheck().handle(inpuData);
            resultMap2 = new CustomerCheck().handle(null);
        } catch (IncorrectDataException e) {
            throw new RuntimeException(e);
        }
        //Then
        assertThat(resultMap1).isNull();
        assertThat(resultMap2).isNull();
    }
}
