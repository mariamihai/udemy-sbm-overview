package guru.springframework.sbmbeerservice.web.mappers;

import guru.springframework.sbmbeerservice.domain.Beer;
import guru.springframework.sbmbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDtoWithInventory(Beer beer);
    BeerDto beerToBeerDto(Beer beer);
    Beer    beerDtoToBeer (BeerDto beerDto);
}
