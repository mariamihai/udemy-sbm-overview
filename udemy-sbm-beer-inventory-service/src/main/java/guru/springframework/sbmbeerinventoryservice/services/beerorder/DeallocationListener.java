package guru.springframework.sbmbeerinventoryservice.services.beerorder;

import guru.springframework.sbmbeerinventoryservice.config.JmsConfig;
import guru.springframework.sbmbeerinventoryservice.services.DeallocationService;
import guru.springframework.sbmbeerinventoryservice.web.model.events.DeallocateBeerOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeallocationListener {

    private final DeallocationService deallocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateBeerOrderRequest request) {
        log.debug("Deallocation request received for " + request.getBeerOrderDto().getId());

        deallocationService.deallocateOrder(request.getBeerOrderDto());
    }
}
