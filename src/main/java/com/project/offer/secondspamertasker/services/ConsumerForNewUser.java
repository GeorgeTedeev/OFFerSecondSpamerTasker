package com.project.offer.secondspamertasker.services;


import com.project.offer.secondspamertasker.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsumerForNewUser {

    @Autowired
    private PostTaskService postTaskService;

    @KafkaListener(topics = "${spring.kafka.newUserTopic}", groupId = "${spring.kafka.groupId")
    public void receive(@Payload User user) {
        postTaskService.sendTaskForOneUser(user);
    }

}
