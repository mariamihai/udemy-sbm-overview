package guru.springframework.sbmbeerservice.services.order;

import guru.springframework.sbmbeerservice.repositories.BeerRepository;
import guru.springframework.sbmbeerservice.web.model.events.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public boolean isValidOrder(BeerOrderDto beerOrderDto) {
        return beerOrderDto.getBeerOrderLines().stream().allMatch(line ->
                beerRepository.findByUpc(line.getUpc()).isPresent());
    }
}
