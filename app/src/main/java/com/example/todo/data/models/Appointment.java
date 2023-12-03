package com.example.todo.data.models;

// Appointment class
public class Appointment {
    // Fields
    private int id;
    private String start_datetime;
    private String end_datetime;
    private String reminder_datetime;
    private String stat;
    private int dependency_id;
    private int agent_id;

    // Constructor
    public Appointment(int id, String start_datetime, String end_datetime, String reminder_datetime, String stat, int dependency_id, int agent_id) {
        this.id = id;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.reminder_datetime = reminder_datetime;
        this.stat = stat;
        this.dependency_id = dependency_id;
        this.agent_id = agent_id;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public String getReminder_datetime() {
        return reminder_datetime;
    }

    public void setReminder_datetime(String reminder_datetime) {
        this.reminder_datetime = reminder_datetime;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public int getDependency_id() {
        return dependency_id;
    }

    public void setDependency_id(int dependency_id) {
        this.dependency_id = dependency_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }
}
