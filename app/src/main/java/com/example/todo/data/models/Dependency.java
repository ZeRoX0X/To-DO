package com.example.todo.data.models;

// Dependency class
public class Dependency {
    // Fields
    private int id;
    private String place;
    private String desc;

    // Constructor
    public Dependency(int id, String place, String desc) {
        this.id = id;
        this.place = place;
        this.desc = desc;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

