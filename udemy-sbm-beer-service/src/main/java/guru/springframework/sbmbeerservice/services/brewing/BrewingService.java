package guru.springframework.sbmbeerservice.services.brewing;

import guru.springframework.sbmbeerservice.config.JmsConfig;
import guru.springframework.sbmbeerservice.domain.Beer;
import guru.springframework.sbmbeerservice.events.BrewBeerEvent;
import guru.springframework.sbmbeerservice.repositories.BeerRepository;
import guru.springframework.sbmbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.sbmbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 10000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Optional<Integer> optionalOnHandInventory = beerInventoryService.getOnHandInventory(beer.getId());

            optionalOnHandInventory.ifPresent(onHandInventory -> {
                log.debug("Min. on hand should be: " + beer.getMinOnHand());
                log.debug("Current inventory is: " + onHandInventory);

                if(isLowOnBeer(beer.getMinOnHand(), onHandInventory)) {
                    brewMoreInventory(beer);
                }
            });
        });
    }

    private boolean isLowOnBeer(Integer minOnHand, Integer onHandInventory) {
        return minOnHand >= onHandInventory;
    }

    private void brewMoreInventory(Beer beer) {
        jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
    }
}
