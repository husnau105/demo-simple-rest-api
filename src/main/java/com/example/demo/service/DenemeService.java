package com.example.demo.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DenemeService {


    @PostConstruct
    public void deneme(){
        System.out.println("d2 is called");
    }
}
