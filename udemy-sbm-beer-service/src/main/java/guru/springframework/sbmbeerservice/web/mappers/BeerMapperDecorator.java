package guru.springframework.sbmbeerservice.web.mappers;

import guru.springframework.sbmbeerservice.domain.Beer;
import guru.springframework.sbmbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.sbmbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = mapper.beerToBeerDtoWithInventory(beer);

        beerInventoryService.getOnHandInventory(beer.getId()).ifPresent(dto::setQuantityOnHand);

        return dto;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return mapper.beerToBeerDtoWithInventory(beer);
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }
}