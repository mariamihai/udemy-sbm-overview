package guru.springframework.brewery.monolith.listeners;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.domain.BeerInventory;
import guru.springframework.brewery.monolith.events.BrewBeerEvent;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BeerRepository beerRepository;

    @Async
    @EventListener
    @Transactional
    public void listen(BrewBeerEvent event){
        log.debug("BREWING Beer for: " + event.getBeer().getBeerName());

        Beer beer = beerRepository.getOne(event.getBeer().getId());

        BeerInventory beerInventory = BeerInventory.builder()
                .beer(beer)
                .quantityOnHand(beer.getQuantityToBrew())
                .build();
        beer.getBeerInventory().add(beerInventory);

        Beer savedBeer = beerRepository.save(beer);
        log.debug("BREWED: " + savedBeer.getBeerName() + " - " + beer.getQuantityToBrew() + " more beers");

    }

}
