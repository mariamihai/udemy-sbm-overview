package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId);

    BeerDto getBeerByName(String beerName);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    void deleteById(UUID beerId);
}
