package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.events.BrewBeerEvent;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@NoArgsConstructor
public class InventoryService {

    private BeerRepository beerRepository;
    private ApplicationEventPublisher publisher;

    @Autowired
    public void setBeerRepository(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Transactional
    @Scheduled(fixedRate = 5000)
    public void checkInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(this::brewBeer);
    }

    protected void brewBeer(Beer beer) {
        AtomicInteger inventory = new AtomicInteger();

        beer.getBeerInventory().forEach(inv -> inventory.addAndGet(inv.getQuantityOnHand()));
        log.debug("BEER: " + beer.getBeerName() + " : quantity = " + inventory.get());

        if(beer.getMinOnHand() >= inventory.get()) {
            publisher.publishEvent(new BrewBeerEvent(beer));
        }
    }
}
