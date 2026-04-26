package com.withHarsh.MediCore.RabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key.pending}")
    private String pendingKey;

    @Value("${rabbitmq.routing.key.confirmed}")
    private String confirmedKey;

    @Value("${rabbitmq.routing.key.cancelled}")
    private String cancelledKey;

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(AppointmentEmailEventDTO event) {

        String routingKey = switch (event.getStatus()) {
            case "CONFIRMED" -> confirmedKey;
            case "CANCELLED" -> cancelledKey;
            default -> pendingKey;
        };

        log.info("Sending message -> {}", event);

        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}