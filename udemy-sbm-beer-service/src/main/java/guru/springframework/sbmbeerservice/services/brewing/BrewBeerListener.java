package guru.springframework.sbmbeerservice.services.brewing;

import guru.springframework.sbmbeerservice.config.JmsConfig;
import guru.springframework.sbmbeerservice.events.BrewBeerEvent;
import guru.springframework.sbmbeerservice.events.NewInventoryEvent;
import guru.springframework.sbmbeerservice.repositories.BeerRepository;
import guru.springframework.sbmbeerservice.services.JmsMessageService;
import guru.springframework.sbmbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsMessageService jmsMessageService;

    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent) {
        BeerDto beerDto = brewBeerEvent.getBeerDto();

        beerRepository.findById(beerDto.getId()).ifPresentOrElse(beer -> {
            beerDto.setQuantityOnHand(beer.getQuantityToBrew());

            log.debug("Brewed " + beer.getQuantityToBrew() + " of beer " + beerDto.getId());

            jmsMessageService.sendJmsMessage(JmsConfig.NEW_INVENTORY_QUEUE, new NewInventoryEvent(beerDto), NewInventoryEvent.class.getSimpleName());

        }, () -> log.error("Couldn't find beer " + beerDto.getId()));
    }
}
