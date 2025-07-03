package com.example.setterdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Greeter {

    private GreetingService greetingService;

    // SETTER injection
    @Autowired
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void showGreeting() {
        System.out.println(greetingService.greet());
    }
}
