package com.example.todo.data.models;

// Appointment class
public class Appointment {
    // Fields
    private int id;
    private String start_datetime;

    private String reminder_datetime;
    private String stat;

    private int agent_id;
    private String agent_name;
    private String agent_category;
    private String agent_phone;
    private int dependency_id;
    private String dependency_name;


    private String dependency_category;


    // Constructors

    public Appointment(int id, String start_datetime, String reminder_datetime, String stat, int agent_id, String agent_name, String agent_category, String agent_phone, int dependency_id, String dependency_name, String dependency_category) {
        this.id = id;
        this.start_datetime = start_datetime;
        this.reminder_datetime = reminder_datetime;
        this.stat = stat;

        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.agent_category = agent_category;
        this.agent_phone = agent_phone;
        this.dependency_id = dependency_id;
        this.dependency_name = dependency_name;
        this.dependency_category = dependency_category;
    }


    public Appointment(int id, String start_datetime, String reminder_datetime, String stat, int dependency_id, int agent_id) {
        this.id = id;
        this.start_datetime = start_datetime;
        this.reminder_datetime = reminder_datetime;
        this.stat = stat;
        this.dependency_id = dependency_id;
        this.agent_id = agent_id;
    }

    public Appointment(String start_datetime, String reminder_datetime, String stat) {
        this.start_datetime = start_datetime;
        this.reminder_datetime = reminder_datetime;
        this.stat = stat;

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

    public String getAgent_name() {
        return agent_name;
    }

    public String getAgent_category() {
        return agent_category;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public String getDependency_name() {
        return dependency_name;
    }

    public String getDependency_category() {
        return dependency_category;
    }
}