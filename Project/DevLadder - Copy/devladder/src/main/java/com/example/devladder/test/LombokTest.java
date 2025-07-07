package com.example.devladder.test;

import com.example.devladder.models.Post;

public class LombokTest {
    public static void main(String[] args) {
        Post post = new Post();
        post.setContent("Lombok is working");
        post.setUserId("user123");
        System.out.println(post.getContent());
    }
}
