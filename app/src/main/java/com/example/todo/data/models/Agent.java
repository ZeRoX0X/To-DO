package com.example.todo.data.models;

// Agent class
public class Agent {
    // Fields
    private int id;
    private String name;
    private String desc;
    private String phone_number;

    // Constructor
    public Agent(int id, String name, String desc, String phone_number) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.phone_number = phone_number;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
