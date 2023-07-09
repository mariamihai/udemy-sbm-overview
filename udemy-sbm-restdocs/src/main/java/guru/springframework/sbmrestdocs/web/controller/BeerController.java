package guru.springframework.sbmrestdocs.web.controller;

import guru.springframework.sbmrestdocs.domain.Beer;
import guru.springframework.sbmrestdocs.repositories.BeerRepository;
import guru.springframework.sbmrestdocs.web.mapper.BeerMapper;
import guru.springframework.sbmrestdocs.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
//        return new ResponseEntity<>(beerMapper.beerToBeerDto(beerRepository.findById(beerId).get()), HttpStatus.OK);

        Beer beer = Beer.builder()
                        .beerName("Beer")
                        .beerStyle("ALE")
                        .price(new BigDecimal("10.00"))
                        .upc(1234567890L)
                .build();
        return new ResponseEntity<>(beerMapper.beerToBeerDto(beer), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Valid BeerDto beerDto){
        beerRepository.save(beerMapper.beerDtoToBeer(beerDto));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Valid BeerDto beerDto){
        beerRepository.findById(beerId).ifPresent(beer -> {
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle().name());
            beer.setPrice(beerDto.getPrice());
            beer.setUpc(beerDto.getUpc());

            beerRepository.save(beer);
        });

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}