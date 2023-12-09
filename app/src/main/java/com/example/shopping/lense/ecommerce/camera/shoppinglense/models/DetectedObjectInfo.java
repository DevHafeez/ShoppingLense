package com.example.shopping.lense.ecommerce.camera.shoppinglense.models;

import java.util.List;

public class DetectedObjectInfo {
    private String name;
    private List<String> properties;
    private String color;

    public DetectedObjectInfo(String name, String color, List<String> properties) {
        this.name = name;
        this.color = color;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public List<String> getProperties() {
        return properties;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
