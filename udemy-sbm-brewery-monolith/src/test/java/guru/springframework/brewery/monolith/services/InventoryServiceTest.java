package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.domain.BeerInventory;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class InventoryServiceTest {

    @Mock
    private BeerRepository beerRepositoryMock;
    @Mock
    private ApplicationEventPublisher publisherMock;

    @Spy
    private InventoryService classUnderTest;

    private Beer beer;
    private BeerInventory beerInventory;

    @BeforeEach
    void setUp() {
        beer = Beer.builder()
                .beerName("Dummy beer")
                .build();

        beerInventory = BeerInventory.builder()
                .beer(beer)
                .quantityOnHand(10)
                .build();
        beer.setBeerInventory(Set.of(beerInventory));

        classUnderTest.setBeerRepository(beerRepositoryMock);
        classUnderTest.setPublisher(publisherMock);
    }

    @Test
    void test_checkInventory() {
        List<Beer> beers = new ArrayList<>();
        beers.add(beer);
        beers.add(beer);

        when(beerRepositoryMock.findAll()).thenReturn(beers);
        doNothing().when(classUnderTest).brewBeer(beer);

        classUnderTest.checkInventory();

        verify(classUnderTest, times(2)).brewBeer(beer);
    }

    @Test
    void test_brewBeer_withoutPublishingOfEvent() {
        beer.setMinOnHand(5);
        doNothing().when(publisherMock).publishEvent(any());

        classUnderTest.brewBeer(beer);

        verify(publisherMock, never()).publishEvent(any());
    }

    @Test
    void test_brewBeer_withPublishingOfEvent() {
        beer.setMinOnHand(15);
        doNothing().when(publisherMock).publishEvent(any());

        classUnderTest.brewBeer(beer);

        verify(publisherMock, times(1)).publishEvent(any());
    }
}