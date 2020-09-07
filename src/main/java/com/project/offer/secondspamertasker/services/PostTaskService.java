package com.project.offer.secondspamertasker.services;

import com.project.offer.secondspamertasker.entities.SpamTask;
import com.project.offer.secondspamertasker.entities.SpamTaskWithEmailAndNumberOfSpamerSender;
import com.project.offer.secondspamertasker.entities.User;
import com.project.offer.secondspamertasker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PostTaskService {

    private SpamTask spamTask;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private BalancerService balancerService;

    private static Logger log = Logger.getLogger(PostTaskService.class.getName());

    public void sendTaskForAllUsers(){
        List<User> allUsers = readAllUsers();
        Optional<List<User>> allUserOptional = Optional.of(allUsers);


        allUserOptional.ifPresent(users->users.forEach((user)->{
            int numberOfSpamerSender;
            try {
                userRepository.save(user);
                numberOfSpamerSender = balancerService.getNumberOfSpamerSender(user.getId());
                producerService.send(new SpamTaskWithEmailAndNumberOfSpamerSender(spamTask, user.getLogin(), numberOfSpamerSender));
            }
            catch(OptimisticLockException e){
                log.info("Another service has changed user`s version in database");
            }
        }));
    }

    public void sendTaskForOneUser(User user){
        int numberOfSpamerSender;

        try {
            userRepository.save(user);
            numberOfSpamerSender = balancerService.getNumberOfSpamerSender(user.getId());
            producerService.send(new SpamTaskWithEmailAndNumberOfSpamerSender(spamTask, user.getLogin(), numberOfSpamerSender));
        }
        catch(OptimisticLockException e){
            log.info("Another service has changed user`s version in database");
        }
    }


    public List<User> readAllUsers(){
        List<User> allUsers = userRepository.findAll();

        return allUsers;
    }

    public SpamTask getSpamTask() {
        return spamTask;
    }

    public void setSpamTask(SpamTask spamTask) {
        this.spamTask = spamTask;
    }
}
