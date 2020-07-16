package com.example.test.rabbit.config;

import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 설명 : XXXXXXXXXXX
 *
 * @author Groot(조민국) / dev.mingood@sk.com
 * @since 2020. 07. 14
 */
@Slf4j
@Configuration
@EnableRabbit
public class UserEventRabbitMQConfig implements RecoveryListener {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8202;
    private static final String VHOST = "/";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String EXCHANGE = "ha.user_event";

    /**
     * CASE 1 : Qualifier
     */
    @Bean
    public RabbitTemplate userEventRabbitTemplate(@Qualifier("userEventConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(EXCHANGE);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    @Qualifier("userEventConnectionFactory")
    public ConnectionFactory userEventConnectionFactory() {
        log.info("## userEventConnectionFactory 생성");
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setVirtualHost(VHOST);
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setRecoveryListener(this);
        return factory;
    }

    /**
     * CASE 2 : 메소드 이름을 다르게
     */
//    @Bean
//    public RabbitTemplate userEventRabbitTemplate(ConnectionFactory userEventConnectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(userEventConnectionFactory);
//        template.setExchange(EXCHANGE);
//        template.setMessageConverter(jsonMessageConverter());
//        return template;
//    }
//
//    @Bean
//    public ConnectionFactory userEventConnectionFactory() {
//        log.info("## userEventConnectionFactory 생성");
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setVirtualHost(VHOST);
//        factory.setHost(HOST);
//        factory.setPort(PORT);
//        factory.setUsername(USERNAME);
//        factory.setPassword(PASSWORD);
//        factory.setRecoveryListener(this);
//        return factory;
//    }

    /**
     * CASE 3 : 메소드 이름을 같게
     */
//    @Bean
//    public RabbitTemplate userEventRabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(newConnectionFactory());
//        template.setExchange(EXCHANGE);
//        template.setMessageConverter(jsonMessageConverter());
//        return template;
//    }
//
////    @Bean
//    public ConnectionFactory newConnectionFactory() {
//        log.info("## userEventConnectionFactory 생성");
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setVirtualHost(VHOST);
//        factory.setHost(HOST);
//        factory.setPort(PORT);
//        factory.setUsername(USERNAME);
//        factory.setPassword(PASSWORD);
//        factory.setRecoveryListener(this);
//        return factory;
//    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Override
    public void handleRecovery(Recoverable recoverable) {
        log.warn("UserEvent - RabbitMQ connection recovered");
    }

    @Override
    public void handleRecoveryStarted(Recoverable recoverable) {
        log.warn("UserEvent - RabbitMQ connection recovery started");
    }

}