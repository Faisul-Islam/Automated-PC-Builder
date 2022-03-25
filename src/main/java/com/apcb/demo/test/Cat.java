package com.apcb.demo.test;

import org.springframework.stereotype.Component;

@Component
public class Cat implements Animal{

    @Override
    public void bark() {
        System.out.println("Meow meow");
    }
}
