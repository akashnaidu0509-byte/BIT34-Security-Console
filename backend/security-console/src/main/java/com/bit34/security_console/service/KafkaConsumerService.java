package com.bit34.security_console.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(
            topics = "security-events",
            groupId = "security-console-group"
    )
    public void consume(String message) {

        System.out.println(
                "KAFKA EVENT RECEIVED: " + message
        );
    }
}