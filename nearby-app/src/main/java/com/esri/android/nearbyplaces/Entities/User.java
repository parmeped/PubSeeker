package com.esri.android.nearbyplaces.Entities;


import com.esri.android.nearbyplaces.Common.IEntity;
import com.esri.android.nearbyplaces.data.Place;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import java.util.ArrayList;


public class User implements IEntity {
    private String _name;
    private String _email;
    private String _userId;
    private ArrayList<Bar> _bars;

    public User(){}

    public User(String id, String name, String email, ArrayList<Bar> bars) {
        this._userId = id;
        this._name = name;
        this._email = email;
        if (bars == null) {
            this._bars = new ArrayList<>();
        }
        else {
            this._bars = bars;
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

    public void setName(String name) {
        this._name = name;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public void setId(String userId) {
        this._userId = userId;
    }

    public void setBars(ArrayList<Bar> bars) {
        this._bars = bars;
    }

    public void addBar(Bar aBar) {
        if (this._bars != null) {
            this._bars.add(aBar);
        } else {
            this._bars = new ArrayList<>();
            this._bars.add(aBar);
        }
    }

    public ArrayList<Bar> getBars() { if (this._bars != null) { return this._bars; } return null; }

    public void removeBar(Bar bar) {
        //TODO
    }

}
