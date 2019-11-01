package com.example.pubseeker;

public class AnotherClassTesting {
    private String someAttribute;
    private String someInteger;
    private String key;

    public AnotherClassTesting() {

    }

    public AnotherClassTesting(String key, String attri1, String someInteger) {
        this.someAttribute = attri1;
        this.someInteger = someInteger;
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String showDataAsJSON() {
        return  "{" +
                "string: " + this.someAttribute + "," +
                "int: " + this.someInteger + "," +
                "key: " + this.key +
                "}";
    }
}
