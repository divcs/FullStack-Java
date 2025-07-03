package com.example.setterdi;

import org.springframework.stereotype.Service;

@Service
public class GoodMorningService implements GreetingService {

    @Override
    public String greet() {
        return "Good Morning from Setter DI!";
    }
}
