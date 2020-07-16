package com.example.test.rabbit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 설명 : XXXXXXXXXXX
 *
 * @author Groot(조민국) / dev.mingood@sk.com
 * @since 2020. 07. 14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("rabbit")
public class TestController {

    private final RabbitTemplate listenLogRabbitTemplate;
    private final RabbitTemplate userEventRabbitTemplate;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage() {
//        listenLogRabbitTemplate.convertAndSend("TRACK_LISTEN");
        log.info("## listenLogRabbitTemplate {} / {} / {}",
                listenLogRabbitTemplate.getConnectionFactory().getHost(),
                listenLogRabbitTemplate.getConnectionFactory().getPort(),
                listenLogRabbitTemplate.getExchange()
        );

//        userEventRabbitTemplate.convertAndSend("USER_EVENT");
        log.info("## userEventRabbitTemplate {} / {} / {}",
                userEventRabbitTemplate.getConnectionFactory().getHost(),
                userEventRabbitTemplate.getConnectionFactory().getPort(),
                userEventRabbitTemplate.getExchange()
        );
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
