package com.example.pubseeker;

public class User {
    private String name;
    private String email;
    private String key;

    public  User() {

    }

    public User(String key, String name, String email) {
        this.key = key;
        this.name = name;
        this.email = email;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String showUserDataAsJSON() {
        return  "{" +
                "name: " + this.name + "," +
                "email: " + this.email + "," +
                "key: " + this.key +
                "}";
    }
}
