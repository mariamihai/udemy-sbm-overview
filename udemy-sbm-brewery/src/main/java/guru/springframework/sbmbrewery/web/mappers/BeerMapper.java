package guru.springframework.sbmbrewery.web.mappers;

import guru.springframework.sbmbrewery.domain.Beer;
import guru.springframework.sbmbrewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDto beerDto);
}
