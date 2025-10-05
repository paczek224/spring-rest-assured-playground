package com.paczek.demo.app.kaffka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void consumeMessage(String message) {
        System.out.println("Odebrano wiadomość: " + message);
    }
}
