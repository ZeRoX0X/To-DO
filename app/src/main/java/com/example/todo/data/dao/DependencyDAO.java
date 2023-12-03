package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.AppointmentDBHelper;
import com.example.todo.data.models.Dependency;

// DependencyDAO class
public class DependencyDAO {

    // Database helper instance
    private AppointmentDBHelper dbHelper;

    // Constructor
    public DependencyDAO(Context context) {
        dbHelper = new AppointmentDBHelper(context);
    }

    // Method to insert a new dependency
    public long insertDependency(Dependency dependency) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put("place", dependency.getPlace());
        values.put("desc", dependency.getDesc());

        // Insert the row and return the id
        long id = db.insert("dependency", null, values);

        // Close the database
        db.close();

        // Return the id
        return id;
    }

    // Method to update an existing dependency
    public int updateDependency(Dependency dependency) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put("place", dependency.getPlace());
        values.put("desc", dependency.getDesc());

        // Update the row and return the number of affected rows
        int rows = db.update("dependency", values, "id = ?", new String[]{String.valueOf(dependency.getId())});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Method to delete an existing dependency
    public int deleteDependency(int id) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the row and return the number of affected rows
        int rows = db.delete("dependency", "id = ?", new String[]{String.valueOf(id)});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Other query methods can go here
}

