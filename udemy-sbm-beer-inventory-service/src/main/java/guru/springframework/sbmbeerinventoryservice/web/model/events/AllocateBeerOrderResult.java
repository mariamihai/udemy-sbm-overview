package guru.springframework.sbmbeerinventoryservice.web.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateBeerOrderResult {

    private BeerOrderDto beerOrderDto;
    private boolean allocationError = false;
    private boolean pendingInventory = false;
}
