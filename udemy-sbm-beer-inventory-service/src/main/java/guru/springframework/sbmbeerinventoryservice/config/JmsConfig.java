package guru.springframework.sbmbeerinventoryservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sbmbeerinventoryservice.events.NewInventoryEvent;
import guru.springframework.sbmbeerinventoryservice.web.model.events.AllocateBeerOrderRequest;
import guru.springframework.sbmbeerinventoryservice.web.model.events.DeallocateBeerOrderRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfig {

    public static final String NEW_INVENTORY_QUEUE = "new-inventory";
    public static final String ALLOCATE_ORDER_QUEUE = "allocate-order";
    public static final String ALLOCATE_ORDER_RESPONSE_QUEUE = "allocate-order-response";
    public static final String DEALLOCATE_ORDER_QUEUE = "de-allocate-order";

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setTypeIdMappings(setClassMappings());

        converter.setObjectMapper(objectMapper);

        return converter;
    }

    private Map<String,Class<?>> setClassMappings() {
        Map<String,Class<?>> typeIdMappings = new HashMap<>();

        typeIdMappings.put(NewInventoryEvent.class.getSimpleName(), NewInventoryEvent.class);
        typeIdMappings.put(AllocateBeerOrderRequest.class.getSimpleName(), AllocateBeerOrderRequest.class);
        typeIdMappings.put(DeallocateBeerOrderRequest.class.getSimpleName(), DeallocateBeerOrderRequest.class);

        return typeIdMappings;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                          JmsErrorHandler errorHandler,
                                                                          MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setErrorHandler(errorHandler);

        return factory;
    }
}