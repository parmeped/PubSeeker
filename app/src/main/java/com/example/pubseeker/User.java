package com.example.pubseeker;

public class User {
    public String name;
    public String email;
    public String key;

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

    public String showUserDataAsJson() {
        return "name: " + this.name + "," +
                "email: " + this.email + "," +
                "key: " + this.key +
                "}";
    }
}
