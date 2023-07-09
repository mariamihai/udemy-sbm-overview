package guru.springframework.sbmbeerservice.web.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateBeerOrderResult {

    private UUID beerOrderId;
    private boolean valid;
}
