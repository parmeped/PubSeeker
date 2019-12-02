package com.esri.android.nearbyplaces.Entities;

import com.esri.android.nearbyplaces.Common.IEntity;

public class Bar implements IEntity {
    private String _name;
    private String _gps;
    private String _barId;

    public Bar() {

    }

    public Bar(String name, String gps) {
        this._name = name;
        this._gps = gps;
    }

    public String getName() {
        return this._name;
    }

    public String getGps() {
        return this._gps;
    }

    public String getId() {
        return this._barId;
    }

    public void setId(String id) {
        this._barId = id;
    }

    public void setName(String name) {this._name = name;}

    public void setGps(String gps) {this._gps = gps;}
}
