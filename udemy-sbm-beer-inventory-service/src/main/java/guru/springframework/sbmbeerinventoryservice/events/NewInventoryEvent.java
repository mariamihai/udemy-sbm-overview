package guru.springframework.sbmbeerinventoryservice.events;

import guru.springframework.sbmbeerinventoryservice.web.model.events.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    private static final long serialVersionUID = 1235372784963552101L;

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
