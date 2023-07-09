package guru.springframework.brewery.monolith.web.mappers;

import guru.springframework.brewery.monolith.domain.BeerOrder;
import guru.springframework.brewery.monolith.web.model.BeerOrderDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
@Component
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}

