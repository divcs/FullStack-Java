package com.example.devladder.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;

    // Optional profile fields
    private String bio;
    private List<String> socialLinks; // LinkedIn, GitHub, etc.

    // Basic post interaction tracking
    private List<String> bookmarkedPostIds;

    // Getters and Setters (all below)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(List<String> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public List<String> getBookmarkedPostIds() {
        return bookmarkedPostIds;
    }

    public void setBookmarkedPostIds(List<String> bookmarkedPostIds) {
        this.bookmarkedPostIds = bookmarkedPostIds;
    }
}
