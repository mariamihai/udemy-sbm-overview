package guru.springframework.sbmbeerinventoryservice.services.beerorder;

import guru.springframework.sbmbeerinventoryservice.config.JmsConfig;
import guru.springframework.sbmbeerinventoryservice.services.AllocationService;
import guru.springframework.sbmbeerinventoryservice.services.JmsMessageService;
import guru.springframework.sbmbeerinventoryservice.web.model.events.AllocateBeerOrderRequest;
import guru.springframework.sbmbeerinventoryservice.web.model.events.AllocateBeerOrderResult;
import guru.springframework.sbmbeerinventoryservice.web.model.events.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static guru.springframework.sbmbeerinventoryservice.config.JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocationListener {

    private final JmsMessageService jmsMessageService;
    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateBeerOrderRequest request) {
        BeerOrderDto beerOrderDto = request.getBeerOrderDto();
        AllocateBeerOrderResult.AllocateBeerOrderResultBuilder builder = AllocateBeerOrderResult.builder();
        builder.beerOrderDto(request.getBeerOrderDto());

        log.debug("Allocation request received for " + beerOrderDto.getId());

        try {
            boolean pendingInventory = allocationService.allocateOrder(beerOrderDto);
            if(!pendingInventory) {
                builder.pendingInventory(true);
            }
        } catch(Exception e) {
            log.error("Allocation failed for orderId: " + beerOrderDto.getId());
            builder.allocationError(true);
        }

        AllocateBeerOrderResult result = builder.build();

        jmsMessageService.sendJmsMessage(ALLOCATE_ORDER_RESPONSE_QUEUE, result, AllocateBeerOrderResult.class.getSimpleName());
    }
}
