package com.esri.android.nearbyplaces.Entities;

import com.esri.android.nearbyplaces.Common.IEntity;

import java.util.ArrayList;


public class User implements IEntity {
    private String _name;
    private String _email;
    private String _userId;
    private ArrayList<Integer> _barsId;


    public User(){};

    public User(String userId, String name, String email, ArrayList<Integer> barsId) {
        this._userId = userId;
        this._name = name;
        this._email = email;
        if (barsId == null) {
            this._barsId = new ArrayList<>();
        }
        else {
            this._barsId = barsId;
        }
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

    public void setName(String name) {
        this._name = name;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public void setId(String userId) {
        this._userId = userId;
    }

    public void setBars(ArrayList<Integer> bars) {
        this._barsId = bars;
    }

    public void addBar(int barId) {
        if (this._barsId != null) {
            this._barsId.add(barId);
        }
    }

    public void removeBar(int barId) {
        //TODO
    }
}
