package com.example.di;

public class Student {
    private Course course;

    // Setter for DI
    public void setCourse(Course course) {
        this.course = course;
    }

    public void showCourse() {
        course.display();
    }
}
