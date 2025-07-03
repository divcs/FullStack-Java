package com.example.setterdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SetterDiExampleApplication implements CommandLineRunner {

    @Autowired
    private Greeter greeter;

    public static void main(String[] args) {
        SpringApplication.run(SetterDiExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        greeter.showGreeting();
    }
}
