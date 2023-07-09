package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import guru.springframework.brewery.monolith.web.controllers.NotFoundException;
import guru.springframework.brewery.monolith.web.mappers.BeerMapper;
import guru.springframework.brewery.monolith.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {


    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID beerId) {
        Beer beer = validatedBeerById(beerId);

        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto getBeerByName(String beerName) {
        Beer beer = beerRepository.findByBeerName(beerName);

        if(beer == null) throw new NotFoundException();

        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer toSave = beerMapper.beerDtoToBeer(beerDto);
        Beer saved = beerRepository.save(toSave);

        return beerMapper.beerToBeerDto(saved);
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = validatedBeerById(beerId);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteById(UUID beerId) {
        validatedBeerById(beerId);

        beerRepository.deleteById(beerId);
    }

    protected Beer validatedBeerById(UUID beerId) {
        return beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
    }
}
