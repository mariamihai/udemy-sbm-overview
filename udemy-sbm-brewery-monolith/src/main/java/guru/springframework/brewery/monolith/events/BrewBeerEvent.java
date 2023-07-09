package guru.springframework.brewery.monolith.events;

import guru.springframework.brewery.monolith.domain.Beer;
import org.springframework.context.ApplicationEvent;

public class BrewBeerEvent extends ApplicationEvent {

    public BrewBeerEvent(Beer source) {
        super(source);
    }

    public Beer getBeer(){
        return (Beer) this.source;
    }

}