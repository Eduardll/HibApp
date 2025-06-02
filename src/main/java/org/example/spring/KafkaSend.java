package org.example.spring;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSend {
    private final KafkaTemplate<String, UserDTOKafka> kafkaTemplate;

    public KafkaSend(KafkaTemplate<String, UserDTOKafka> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(UserDTOKafka event) {
        kafkaTemplate.send("user-created", event.getEmail(), event);
    }

    public void sendUserDeletedEvent(UserDTOKafka event) {
        kafkaTemplate.send("user-deleted", event.getEmail(), event);
    }
    public void sendUserUpdateEvent(UserDTOKafka event) {
        kafkaTemplate.send("user-updated", event.getEmail(), event);
    }
}
