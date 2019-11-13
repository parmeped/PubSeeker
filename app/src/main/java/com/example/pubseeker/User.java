package com.example.pubseeker;

import java.util.ArrayList;


public class User {
    private String _name;
    private String _email;
    private String _userId;
    private ArrayList<Integer> _barsId;


    public User(){};

    public User(String userId, String name, String email, ArrayList<Integer> barsId) {
        this._userId = userId;
        this._name = name;
        this._email = email;
        this._barsId = barsId;
    }

    public String getName() {
        return this._name;
    }

    public String getEmail() {
        return this._email;
    }

    public String getId() {
        return this._userId;
    }

    public ArrayList<Integer> getBars() {
        return this._barsId;
    }

}
