package guru.springframework.sbmbeerinventoryservice.services;

import guru.springframework.sbmbeerinventoryservice.web.model.events.BeerOrderDto;

public interface DeallocationService {
    
    void deallocateOrder(BeerOrderDto beerOrderDto);
}
