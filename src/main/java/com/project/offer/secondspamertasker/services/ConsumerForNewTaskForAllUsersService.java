package com.project.offer.secondspamertasker.services;


import com.project.offer.secondspamertasker.entities.SpamTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsumerForNewTaskForAllUsersService {

    @Autowired
    private PostTaskService postTaskService;

    @KafkaListener(topics = "${spring.kafka.taskerTopic}", groupId = "${spring.kafka.groupId")
    public void receive(@Payload SpamTask spamTask) {
        postTaskService.setSpamTask(spamTask);
        postTaskService.sendTaskForAllUsers();
    }
}
