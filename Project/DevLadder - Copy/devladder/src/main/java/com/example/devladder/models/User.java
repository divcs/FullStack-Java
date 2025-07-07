package com.example.devladder.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username; // ✅ New
    private String name;
    private String email;
    private String password;

    private String role = "USER"; // ✅ Default role

    // Optional profile fields
    private String bio;
    private List<String> socialLinks; // LinkedIn, GitHub, etc.

    private List<String> bookmarkedPostIds;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
