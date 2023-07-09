package guru.springframework.sbmbeerservice.services;

import guru.springframework.sbmbeerservice.web.model.BeerDto;
import guru.springframework.sbmbeerservice.web.model.BeerPagedList;
import guru.springframework.sbmbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID beerId, boolean showInventoryOnHand);

    BeerDto getByUpc(String upc, boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, boolean showInventoryOnHand);

}
