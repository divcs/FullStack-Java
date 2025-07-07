package com.example.devladder.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Comment {

    private String id = UUID.randomUUID().toString();
    private String commenterId;
    private String commenterName;

    private String text;
    private LocalDateTime commentedAt = LocalDateTime.now();
    private List<Comment> replies = new ArrayList<>();

    // --- Getters and Setters ---
    public String getCommenterName() {
        return commenterName;
    }
    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(LocalDateTime commentedAt) {
        this.commentedAt = commentedAt;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
