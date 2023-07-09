package guru.springframework.sbmbeerservice.events;

import guru.springframework.sbmbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    private static final long serialVersionUID = -6941560574044122662L;

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
