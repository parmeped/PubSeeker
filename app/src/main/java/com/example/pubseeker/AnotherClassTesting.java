package com.example.pubseeker;

import com.example.pubseeker.Common.IDatabaseEntity;

import java.util.ArrayList;

public class AnotherClassTesting implements IDatabaseEntity {
    private String direction;
    private String something;

    public AnotherClassTesting() {

    }

    public AnotherClassTesting(String some, String thing) {
        this.direction = some;
        this.something = thing;
    }

    public ArrayList<String> getAttributes() {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(this.direction);
        attributes.add(this.something);
        return attributes;
    }
}
