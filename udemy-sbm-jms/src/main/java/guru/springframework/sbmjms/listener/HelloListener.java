package guru.springframework.sbmjms.listener;

import guru.springframework.sbmjms.config.JmsConfig;
import guru.springframework.sbmjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloListener {

    private final JmsTemplate jmsTemplate;

//    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen (@Payload HelloWorldMessage helloWorldMessage,
                        @Headers MessageHeaders headers) {
        log.debug(headers.toString());
        log.debug("Headers: " + helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenAndReply (@Payload HelloWorldMessage helloWorldMessage,
                                Message message) throws JMSException {
        log.debug("Listened for: " + helloWorldMessage);

        HelloWorldMessage replyMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("I am replying.")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
    }
}
