package com.example.pubseeker;

import com.example.pubseeker.Common.IDatabaseEntity;

import java.util.ArrayList;


public class User implements IDatabaseEntity {
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

    public ArrayList<String> getAttributes() {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(this.name);
        attributes.add(this.email);
        attributes.add(this.key);
        return attributes;
    }
}
