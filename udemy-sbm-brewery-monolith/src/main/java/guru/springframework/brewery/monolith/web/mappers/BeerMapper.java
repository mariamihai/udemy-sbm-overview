package guru.springframework.brewery.monolith.web.mappers;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {DateMapper.class}, componentModel  = "spring")
@DecoratedWith(BeerMapperDecorator.class)
@Component
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}