package guru.springframework.brewery.monolith.web.mappers;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.domain.BeerInventory;
import guru.springframework.brewery.monolith.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BeerMapperDecoratorTest {

    private BeerMapper beerMapperMock;

    private BeerMapperDecorator classUnderTest;

    private Beer beer;
    private BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerMapperMock = mock(BeerMapper.class);
        classUnderTest = mock(BeerMapperDecorator.class, CALLS_REAL_METHODS);
        classUnderTest.setBeerMapper(beerMapperMock);

        beer = Beer.builder().build();
        beerDto = BeerDto.builder().build();
    }

    @Test
    void test_beerToBeerDto_nullInventory() {
        beer.setBeerInventory(null);

        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);
        doCallRealMethod().when(classUnderTest).beerToBeerDto(beer);

        BeerDto actual = classUnderTest.beerToBeerDto(beer);

        assertEquals(this.beerDto, actual);
    }

    @Test
    void test_beerToBeerDto_emptyInventory() {
        beer.setBeerInventory(new HashSet<>());

        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);
        doCallRealMethod().when(classUnderTest).beerToBeerDto(beer);

        BeerDto actual = classUnderTest.beerToBeerDto(beer);

        assertEquals(beerDto, actual);
    }

    @Test
    void test_beerToBeerDto_containsInventory() {
        Set<BeerInventory> beerInventory = new HashSet<>();
        beerInventory.add(BeerInventory.builder().beer(beer).quantityOnHand(1).build());
        beerInventory.add(BeerInventory.builder().beer(beer).quantityOnHand(2).build());
        beerInventory.add(BeerInventory.builder().beer(beer).quantityOnHand(3).build());
        beer.setBeerInventory(beerInventory);

        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);
        doCallRealMethod().when(classUnderTest).beerToBeerDto(beer);

        BeerDto actual = classUnderTest.beerToBeerDto(beer);

        // Verify the quantity on hand added in the inventory
        assertEquals(6, actual.getQuantityOnHand());
    }
}