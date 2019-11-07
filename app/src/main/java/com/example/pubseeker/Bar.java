package com.example.pubseeker;

import com.example.pubseeker.Common.IDatabaseEntity;

import org.json.JSONObject;

public class Bar implements IDatabaseEntity {
    private String name;
    private String gps;
    private String barId;

    public Bar() {

    }

    public Bar(String name, String gps, String barId) {
        this.name = name;
        this.gps = gps;
        this.barId = barId;
    }

    public JSONObject getAttributes() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", this.name);
            jsonObject.put("gps", this.gps);
            jsonObject.put("barId", this.barId);
        } catch(Exception e) {
            //TODO
        }
        return jsonObject;
    }
}
