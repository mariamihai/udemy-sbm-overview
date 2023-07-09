package guru.springframework.sbmbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sbmbeerservice.web.model.events.ValidateBeerOrderRequest;
import guru.springframework.sbmbeerservice.web.model.events.ValidateBeerOrderResult;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory";
    public static final String VALIDATE_ORDER_QUEUE = "validate-order";
    public static final String VALIDATE_ORDER_RESULT_QUEUE = "validate-order-result";

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
        typeIdMappings.put(ValidateBeerOrderRequest.class.getSimpleName(), ValidateBeerOrderRequest.class);
        typeIdMappings.put(ValidateBeerOrderResult.class.getSimpleName(), ValidateBeerOrderResult.class);

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