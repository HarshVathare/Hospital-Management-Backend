package com.withHarsh.MediCore.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.email.queue.name}")
    private String queueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("appointment.status.*");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory
    ) {

        RabbitTemplate rabbitTemplate =
                new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory
    ) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);

        factory.setMessageConverter(jsonMessageConverter());

        return factory;
    }
}






// package com.withHarsh.MediCore.RabbitMQ;

// import org.springframework.amqp.core.*;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;

// //import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
// import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
// import org.springframework.amqp.support.converter.MessageConverter;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class RabbitMQConfig {

//     @Value("${rabbitmq.email.queue.name}")
//     private String emailqueue;

//     @Value("${rabbitmq.exchange.name}")
//     private String exchange;

//     @Bean
//     public Queue emailqueue() {
//         return new Queue(emailqueue);
//     }

//     @Bean
//     public TopicExchange exchange() {
//         return new TopicExchange(exchange);
//     }

//     @Bean
//     public Binding pendingroutingkey() {
//         return BindingBuilder
//                 .bind(emailqueue())
//                 .to(exchange())
//                 .with("appointment.status.*");
//     }

// //    @Bean
// //    public Binding confirmedBinding() {
// //        return BindingBuilder.bind(emailqueue())
// //                .to(exchange())
// //                .with("appointment.status.confirmed");
// //    }
// //
// //    @Bean
// //    public Binding cancelledBinding() {
// //        return BindingBuilder.bind(emailqueue())
// //                .to(exchange())
// //                .with("appointment.status.cancelled");
// //    }

//     @Bean
//     public MessageConverter converter () {
//         return new Jackson2JsonMessageConverter();
//     }

//     @Bean
//     public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//         RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//         rabbitTemplate.setMessageConverter(converter());
//         return rabbitTemplate;
//     }


// }
