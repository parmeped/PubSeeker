package com.example.pubseeker;

import com.example.pubseeker.Common.IDatabaseEntity;

import org.json.JSONObject;

import java.util.ArrayList;


public class User implements IDatabaseEntity {
    private String name;
    private String email;
    private String key;
    private ArrayList<String> bars;

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

    public JSONObject getAttributes() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", this.name);
            jsonObject.put("email", this.email);
            jsonObject.put("userId", this.key);
            jsonObject.put("bars", this.bars);
        } catch(Exception e) {
            //TODO
        }
        return jsonObject;
    }
}
