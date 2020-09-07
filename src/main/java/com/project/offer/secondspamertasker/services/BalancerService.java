package com.project.offer.secondspamertasker.services;


import org.springframework.stereotype.Service;

@Service
public class BalancerService {

    public int getNumberOfSpamerSender(Long id){
        if(id % 2 == 0) return 1;
        else return 2;
    }
}
