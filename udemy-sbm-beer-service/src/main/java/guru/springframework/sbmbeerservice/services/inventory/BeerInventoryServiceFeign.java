package guru.springframework.sbmbeerservice.services.inventory;

import guru.springframework.sbmbeerservice.web.model.events.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Profile("local-discovery")
@Service
@RequiredArgsConstructor
public class BeerInventoryServiceFeign implements BeerInventoryService {

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Optional<Integer> getOnHandInventory(UUID beerId) {
        log.debug("Calling Inventory Service for " + beerId);

        try {
            ResponseEntity<List<BeerInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnHandInventory(beerId);

            return Optional.of(Objects.requireNonNull(responseEntity.getBody())
                    .stream()
                    .mapToInt(BeerInventoryDto::getQuantityOnHand)
                    .sum());
        } catch(Exception e) {
            log.error("Cannot connect to the Inventory Service", e);
            return Optional.empty();
        }
    }
}
