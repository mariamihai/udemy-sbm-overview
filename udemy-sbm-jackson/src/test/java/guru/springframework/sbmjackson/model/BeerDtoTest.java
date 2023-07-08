package guru.springframework.sbmjackson.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class BeerDtoTest extends BaseTest {

    @Test
    public void testSerializeDto() throws JsonProcessingException {
        BeerDto beerDto = getDto();

        String jsonString = objectMapper.writeValueAsString(beerDto);

        System.out.println(jsonString);
    }

    @Test
    public void testDeserializeToDto() throws JsonProcessingException {
        String jsonString = "{\"beerName\":\"Beer\",\"beerStyle\":\"ALE\",\"upc\":1234567890,\"price\":\"10.00\",\"createdDate\":\"2020-04-06T16:27:32+0200\",\"lastUpdatedDate\":\"2020-04-06T16:27:32.8873831+02:00\",\"myLocalDate\":\"20200406\",\"beerId\":\"a2a2879e-c717-486a-b654-f5ddfae2ee65\"}";

        BeerDto beerDto = objectMapper.readValue(jsonString, BeerDto.class);

        System.out.println(beerDto);
    }
}