package guru.springframework.sbmbeerservice.services.inventory;

import guru.springframework.sbmbeerservice.web.model.events.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static guru.springframework.sbmbeerservice.services.inventory.InventoryFailoverFeignClient.BEER_INVENTORY_FAILOVER;

@FeignClient(name = BEER_INVENTORY_FAILOVER)
public interface InventoryFailoverFeignClient {
    
    String BEER_INVENTORY_FAILOVER = "beer-inventory-failover";
    String BEER_INVENTORY_FAILOVER_PATH = "/inventory-failover";

    @GetMapping(value = BEER_INVENTORY_FAILOVER_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();
}
