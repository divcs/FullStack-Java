package com.example.devladder.repositories;

import com.example.devladder.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(String userId);  // Custom method to get posts by userId
}
