package com.example.pubseeker;

public class Bar {
    private String _name;
    private String _gps;
    private String _barId;

    public Bar() {

    }

    public Bar(String name, String gps, String barId) {
        this._name = name;
        this._gps = gps;
        this._barId = barId;
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
}
