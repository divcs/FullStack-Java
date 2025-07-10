package com.example.devladder.controllers;

import com.example.devladder.models.Comment;
import com.example.devladder.models.Post;
import com.example.devladder.models.User;
import com.example.devladder.repositories.PostRepository;
import com.example.devladder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        post.setUsername(user.getName());
        post.setTimestamp(LocalDateTime.now());

        return postRepo.save(post);
    }

    // 🔹 Get all posts by user
    @GetMapping("/users/{userId}/posts")
    public List<Post> getPostsByUser(@PathVariable String userId) {
        return postRepo.findByUserId(userId);
    }

    // 🔹 Global feed
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    // 🔹 Get post by ID
    @GetMapping("/posts/{postId}")
    public Post getPostById(@PathVariable String postId) {
        return postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // ✅ Update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable String postId, @RequestBody Post updatedPost) {
        return postRepo.findById(postId)
            .map(post -> {
                post.setContent(updatedPost.getContent());
                post.setTags(updatedPost.getTags());
                return ResponseEntity.ok(postRepo.save(post));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        if (postRepo.existsById(postId)) {
            postRepo.deleteById(postId);
            return ResponseEntity.ok("Post deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Post not found.");
        }
    }

    @PostMapping("/posts/{postId}/comments")
    public Post addComment(@PathVariable String postId, @RequestBody Comment comment) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        System.out.println("🔹 Post found with ID: " + postId);
        System.out.println("🔸 Incoming commenterId: " + comment.getCommenterId());

        // DEBUG: Try fetching user directly
        Optional<User> userOpt = userRepo.findById(comment.getCommenterId());
        if (userOpt.isEmpty()) {
            System.out.println("❌ No user found with ID: " + comment.getCommenterId());
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        System.out.println("✅ User found: " + user.getName());

        comment.setCommenterName(user.getName());

        if (post.getComments() == null) {
            post.setComments(new ArrayList<>());
        }

        post.getComments().add(comment);
        return postRepo.save(post);
    }


    // ✅ Update a comment (top-level only)
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Post> updateComment(@PathVariable String postId, @PathVariable String commentId, @RequestBody Comment updatedComment) {
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) return ResponseEntity.notFound().build();

        boolean updated = false;
        for (Comment comment : post.getComments()) {
            if (comment.getId().equals(commentId)) {
                comment.setText(updatedComment.getText());
                updated = true;
                break;
            }
        }

        return updated ? ResponseEntity.ok(postRepo.save(post)) : ResponseEntity.notFound().build();
    }

    // ✅ Delete a comment (top-level only)
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Post> deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) return ResponseEntity.notFound().build();

        boolean removed = post.getComments().removeIf(comment -> comment.getId().equals(commentId));

        return removed ? ResponseEntity.ok(postRepo.save(post)) : ResponseEntity.notFound().build();
    }

    // 🔹 Add reply to a comment
    @PostMapping("/posts/{postId}/comments/{parentCommentId}/reply")
    public Post addReply(@PathVariable String postId, @PathVariable String parentCommentId, @RequestBody Comment reply) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean added = addReplyRecursive(post.getComments(), parentCommentId, reply);
        if (!added) {
            throw new RuntimeException("Parent comment not found");
        }

        return postRepo.save(post);
    }

    // Recursively find comment and add reply
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
            if (addReplyRecursive(c.getReplies(), parentId, reply)) {
                return true;
            }
        }
        return false;
    }
}
