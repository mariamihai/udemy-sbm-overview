package guru.springframework.sbmbrewery.services.v2;

import guru.springframework.sbmbrewery.web.model.v2.BeerDtoV2;

import java.util.UUID;

public interface BeerServiceV2 {

    BeerDtoV2 getBeerById(UUID beerId);

    BeerDtoV2 saveNewBeer(BeerDtoV2 beerDto);

    void update(UUID beerId, BeerDtoV2 beerDto);

    void deleteById(UUID beerId);
}
