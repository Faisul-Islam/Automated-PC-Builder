package com.apcb.demo.test;

import org.springframework.beans.factory.annotation.Autowired;

public class AnimalCaller {
    @Autowired
    Animal animal;



   public void bark(){
        animal.bark();
    }
}
