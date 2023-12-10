package com.example.todo.data.models;

// Dependency class
public class Dependency {
    // Fields
    private int id;
    private String dependency_name;
    private String category;

    // Constructor
    public Dependency(int id, String dependency_name, String category) {
        this.id = id;
        this.dependency_name = dependency_name;
        this.category = category;
    }

    public Dependency(String dependency_name, String category) {
        this.dependency_name = dependency_name;
        this.category = category;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDependency_name() {
        return dependency_name;
    }

    public void setDependency_name(String dependency_name) {
        this.dependency_name = dependency_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

