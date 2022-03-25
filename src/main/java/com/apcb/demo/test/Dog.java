package com.apcb.demo.test;

import org.springframework.stereotype.Component;

@Component
public class Dog implements Animal{
    @Override
    public void bark() {
        System.out.println("Vouw vouw");
    }
}
