package guru.springframework.sbmbeerservice.services.inventory;

import guru.springframework.sbmbeerservice.config.FeignClientConfig;
import guru.springframework.sbmbeerservice.web.model.events.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = InventoryServiceFeignClient.BEER_INVENTORY,
             fallback = InventoryServiceFeignClientFailover.class,
             configuration = FeignClientConfig.class)
public interface InventoryServiceFeignClient {

    String BEER_INVENTORY = "beer-inventory-service";

    @GetMapping(value = BeerInventoryService.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable UUID beerId);
}
