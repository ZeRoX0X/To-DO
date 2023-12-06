package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.AppointmentDBHelper;
import com.example.todo.data.models.Agent;

// AgentDAO class
public class AgentDAO {

    // Database helper instance
    private final AppointmentDBHelper dbHelper;

    // Constructor
    public AgentDAO(Context context) {
        dbHelper = new AppointmentDBHelper(context);
    }

    // Method to insert a new agent
    public long insertAgent(Agent agent) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put("name", agent.getName());
        values.put("desc", agent.getDesc());
        values.put("phone_number", agent.getPhone_number());

        // Insert the row and return the id
        long id = db.insert("agent", null, values);

        // Close the database
        db.close();

        // Return the id
        return id;
    }

    // Method to update an existing agent
    public int updateAgent(Agent agent) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put("name", agent.getName());
        values.put("desc", agent.getDesc());
        values.put("phone_number", agent.getPhone_number());

        // Update the row and return the number of affected rows
        int rows = db.update("agent", values, "id = ?", new String[]{String.valueOf(agent.getId())});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Method to delete an existing agent
    public int deleteAgent(int id) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the row and return the number of affected rows
        int rows = db.delete("agent", "id = ?", new String[]{String.valueOf(id)});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Other query methods can go here
}
