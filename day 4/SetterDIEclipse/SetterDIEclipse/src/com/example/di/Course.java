package com.example.di;

public class Course {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("Enrolled in: " + name);
    }
}
