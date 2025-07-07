package com.example.devladder.controllers;

import com.example.devladder.models.Comment;
import com.example.devladder.models.User;
import com.example.devladder.models.Post;
import com.example.devladder.repositories.PostRepository;
import com.example.devladder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

 // 🔹 Create a post for a user
    @PostMapping("/users/{userId}/posts")
    public Post createPostForUser(@PathVariable String userId, @RequestBody Post post) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUserId(user.getId());
        post.setUsername(user.getName());  // ✅ Use getName() instead // ✅ Set the username
        post.setTimestamp(LocalDateTime.now());

        return postRepo.save(post);
    }


    // 🔹 Get all posts for a user
    @GetMapping("/users/{userId}/posts")
    public List<Post> getPostsByUser(@PathVariable String userId) {
        return postRepo.findByUserId(userId);
    }

    // 🔹 Global feed (optional)
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    // 🔹 View a single post by ID
    @GetMapping("/posts/{postId}")
    public Post getPostById(@PathVariable String postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

 // 🔹 Add top-level comment to post 
    @PostMapping("/posts/{postId}/comments")
    public Post addComment(@PathVariable String postId, @RequestBody Comment comment) {
        // ✅ Step 1: Fetch post
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // ✅ Step 2: Fetch user to get commenter name
        User user = userRepo.findById(comment.getCommenterId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Step 3: Set commenterName using the user
        comment.setCommenterName(user.getName());  // or user.getUsername() if that field exists
       

        // ✅ Step 4: Initialize comments list if null
        if (post.getComments() == null) {
            post.setComments(new ArrayList<>());
        }

        // ✅ Step 5: Add comment to post
        post.getComments().add(comment);

        // ✅ Step 6: Save post with the new comment
        return postRepo.save(post);
    }

    // 🔹 Add a reply to a comment
    @PostMapping("/posts/{postId}/comments/{parentCommentId}/reply")
    public Post addReply(@PathVariable String postId,
                         @PathVariable String parentCommentId,
                         @RequestBody Comment reply) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean added = addReplyRecursive(post.getComments(), parentCommentId, reply);
        if (!added) {
            throw new RuntimeException("Parent comment not found");
        }

        return postRepo.save(post);
    }

    // Recursive function to find and reply to a comment
    private boolean addReplyRecursive(List<Comment> comments, String parentId, Comment reply) {
        if (comments == null) return false;

        for (Comment c : comments) {
            if (Objects.equals(c.getId(), parentId)) {
                if (c.getReplies() == null) {
                    c.setReplies(new ArrayList<>());
                }
                c.getReplies().add(reply);
                return true;
            }
            // Recurse deeper
            if (addReplyRecursive(c.getReplies(), parentId, reply)) {
                return true;
            }
        }
        return false;
    }
}
