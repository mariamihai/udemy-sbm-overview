package guru.springframework.brewery.monolith.listeners;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.events.BrewBeerEvent;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class BrewBeerListenerTest {

    @Mock
    private BeerRepository beerRepository;
    @InjectMocks
    private BrewBeerListener classUnderTest;

    @Test
    void test_listen() {
        Beer beer = Beer.builder().build();
        UUID beerId = UUID.randomUUID();
        String beerName = "Dummy Beer";
        Integer quantityToBrew = 5;
        beer.setBeerName(beerName);
        beer.setId(beerId);
        beer.setQuantityToBrew(quantityToBrew);
        beer.setBeerInventory(new HashSet<>());

        BrewBeerEvent brewBeerEvent = new BrewBeerEvent(beer);

        when(beerRepository.getOne(beerId)).thenReturn(beer);
        when(beerRepository.save(any())).thenReturn(beer);

        classUnderTest.listen(brewBeerEvent);

        assertEquals(1, beer.getBeerInventory().size());
    }
}