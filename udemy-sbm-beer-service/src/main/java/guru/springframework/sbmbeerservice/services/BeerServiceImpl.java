package guru.springframework.sbmbeerservice.services;

import guru.springframework.sbmbeerservice.domain.Beer;
import guru.springframework.sbmbeerservice.repositories.BeerRepository;
import guru.springframework.sbmbeerservice.web.controller.NotFoundException;
import guru.springframework.sbmbeerservice.web.mappers.BeerMapper;
import guru.springframework.sbmbeerservice.web.model.BeerDto;
import guru.springframework.sbmbeerservice.web.model.BeerPagedList;
import guru.springframework.sbmbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        if(showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beer);
        }

        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    @Cacheable(cacheNames = "beerCacheByUpc", key = "#upc", condition = "#showInventoryOnHand == false")
    public BeerDto getByUpc(String upc, boolean showInventoryOnHand) {
        Beer beer = beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new);

        if(showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beer);
        }

        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer toSave = beerMapper.beerDtoToBeer(beerDto);
        Beer saved = beerRepository.save(toSave);

        return beerMapper.beerToBeerDtoWithInventory(saved);
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDtoWithInventory(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle.toString(), pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle.toString(), pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        return new BeerPagedList(
                getBeerDtos(beerPage, showInventoryOnHand),
                PageRequest.of(beerPage.getPageable().getPageNumber(),
                               beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());
    }

    private List<BeerDto> getBeerDtos(Page<Beer> beerPage, boolean showInventoryOnHand) {
        if(showInventoryOnHand) {
            return beerPage.getContent().stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList());
        }

        return beerPage.getContent().stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList());
    }
}
