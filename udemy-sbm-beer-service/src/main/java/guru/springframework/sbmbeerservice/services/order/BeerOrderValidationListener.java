package guru.springframework.sbmbeerservice.services.order;

import guru.springframework.sbmbeerservice.config.JmsConfig;
import guru.springframework.sbmbeerservice.services.JmsMessageService;
import guru.springframework.sbmbeerservice.web.model.events.BeerOrderDto;
import guru.springframework.sbmbeerservice.web.model.events.ValidateBeerOrderRequest;
import guru.springframework.sbmbeerservice.web.model.events.ValidateBeerOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static guru.springframework.sbmbeerservice.config.JmsConfig.VALIDATE_ORDER_RESULT_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final BeerOrderValidator validator;
    private final JmsMessageService jmsMessageService;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest request) {
        BeerOrderDto beerOrderDto = request.getBeerOrderDto();

        ValidateBeerOrderResult result = ValidateBeerOrderResult.builder()
                .beerOrderId(beerOrderDto.getId())
                .valid(validator.isValidOrder(beerOrderDto))
                .build();

        jmsMessageService.sendJmsMessage(VALIDATE_ORDER_RESULT_QUEUE, result, ValidateBeerOrderResult.class.getSimpleName());
    }

}
