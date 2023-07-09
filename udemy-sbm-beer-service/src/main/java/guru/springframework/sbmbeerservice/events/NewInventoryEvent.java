package guru.springframework.sbmbeerservice.events;

import guru.springframework.sbmbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    private static final long serialVersionUID = 1235372784963552101L;

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
