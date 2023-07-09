package guru.springframework.sbmbeerservice.services.inventory;

import java.util.Optional;
import java.util.UUID;

public interface BeerInventoryService {

    String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

    Optional<Integer> getOnHandInventory(UUID beerId);
}
