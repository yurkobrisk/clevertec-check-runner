package by.korziuk.check_app.factory;

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
        Map<Integer, Integer> resultMap = new CustomerCheck().handle(inpuData);
        //Then
        assertThat(resultMap.size()).isEqualTo(4);
        assertThat(resultMap.containsKey(0)).isTrue();
        assertThat(resultMap.containsValue(1234)).isTrue();
        assertThat(resultMap.get(5)).isEqualTo(1);
    }

    @Test
    void should_handle_incorrect_data_and_return_size_0() {
        //Given
        String[] inpuData = {"3-a", "2b-5f", "5a-1", "car-1234d"};
        //When
        Map<Integer, Integer> resultMap = new CustomerCheck().handle(inpuData);
        //Then
        assertThat(resultMap.size()).isEqualTo(0);
    }

    @Test
    void should_handle_incorrect_data_and_return_size_1() {
        //Given
        String[] inpuData = {"asd45", "adrg-", "51", "-", "card-12349"};
        //When
        Map<Integer, Integer> resultMap = new CustomerCheck().handle(inpuData);
        //Then
        assertThat(resultMap.size()).isEqualTo(1);
    }

    @Test
    void should_handle_null_data_and_return_null() {
        //Given
        String[] inpuData = null;
        //When
        Map<Integer, Integer> resultMap = new CustomerCheck().handle(inpuData);
        //Then
        assertThat(resultMap).isNull();
    }
}
