package com.example.todo.data.models;

// Agent class
public class Agent {
    // Fields
    private int id;
    private String agent_name;
    private String category;
    private String phone_number;

    // Constructor
    public Agent(int id, String agent_name, String category, String phone_number) {
        this.id = id;
        this.agent_name = agent_name;
        this.category = category;
        this.phone_number = phone_number;
    }

    public Agent(String agent_name, String category, String phone_number) {
        this.agent_name = agent_name;
        this.category = category;
        this.phone_number = phone_number;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
