package com.example.devladder;

import com.example.devladder.models.Post;
import com.example.devladder.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
@SpringBootApplication
public class DevladderApplication implements CommandLineRunner {

    @Autowired
    private PostRepository postRepo;

    public static void main(String[] args) {
        SpringApplication.run(DevladderApplication.class, args);
    }

    @Value("${sample.data.load:false}")
    private boolean loadSampleData;

    @Override
    public void run(String... args) {
        if (loadSampleData) {
            Post post = new Post();
            post.setUserId("testUser");
            post.setContent("Sample post");
            post.setTags(Arrays.asList("Java", "Spring"));
            postRepo.save(post);
        }
    }

}
