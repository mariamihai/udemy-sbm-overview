package guru.springframework.sbmbreweryclient.web.client;

import guru.springframework.sbmbreweryclient.web.model.BeerDto;
import guru.springframework.sbmbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void test_getBeerById() {
        BeerDto beerDto = client.getBeerById(UUID.randomUUID());

        assertNotNull(beerDto);
    }

    @Test
    void test_saveNewBeer() {
        BeerDto beerDto = BeerDto.builder().build();

        URI uri = client.saveNewBeer(beerDto);

        assertNotNull(uri);
    }

    @Test
    void test_updateBeer() {
        BeerDto beerDto = BeerDto.builder().build();

        client.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void test_deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }

    @Test
    void test_getCustomerById() {
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());

        assertNotNull(customerDto);
    }

    @Test
    void test_saveNewCustomer() {
        CustomerDto customerDto = CustomerDto.builder().build();

        URI uri = client.saveNewCustomer(customerDto);

        assertNotNull(uri);
    }

    @Test
    void test_updateCustomer() {
        CustomerDto customerDto = CustomerDto.builder().build();

        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void test_deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }
}