package com.project.offer.secondspamertasker.services;


import com.project.offer.secondspamertasker.entities.SpamTaskWithEmailAndNumberOfSpamerSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Value("${spring.kafka.senderTopic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, SpamTaskWithEmailAndNumberOfSpamerSender> kafkaTemplate;

    public void send(SpamTaskWithEmailAndNumberOfSpamerSender spamTaskWithEmailAndNumberOfSpamerSender){
        kafkaTemplate.send(topic, spamTaskWithEmailAndNumberOfSpamerSender);
    }
}
