package guru.springframework.sbmbeerinventoryservice.services;

import guru.springframework.sbmbeerinventoryservice.domain.BeerInventory;
import guru.springframework.sbmbeerinventoryservice.repositories.BeerInventoryRepository;
import guru.springframework.sbmbeerinventoryservice.web.model.events.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeallocationServiceImpl implements DeallocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
                    .build();

            BeerInventory savedInventory = beerInventoryRepository.save(beerInventory);

            log.debug("Saved Inventory from deallocation for beer upc: " + savedInventory.getUpc() +
                    " inventory id: " + savedInventory.getId());
        });
    }
}
