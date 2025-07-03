package com.example.springaopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.springaopdemo.service.MyService.*(..))")
    public void beforeAdvice() {
        System.out.println("[AOP] --> Before calling the method");
    }

    @After("execution(* com.example.springaopdemo.service.MyService.*(..))")
    public void afterAdvice() {
        System.out.println("[AOP] --> After calling the method");
    }
}
