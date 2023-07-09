package guru.springframework.sbmrestdocs.web.mapper;

import guru.springframework.sbmrestdocs.domain.Beer;
import guru.springframework.sbmrestdocs.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto (Beer beer);
    Beer    beerDtoToBeer (BeerDto beerDto);
}

