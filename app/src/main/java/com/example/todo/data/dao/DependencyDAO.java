package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.AppointmentDBHelper;
import com.example.todo.data.models.Dependency;

// DependencyDAO class
public class DependencyDAO {

    public static final String DEPENDENCY_NAME = "name";
    public static final String CATEGORY = "category";
    public static final String DEPENDENCY = "dependency";
    // Database helper instance
    private final AppointmentDBHelper dbHelper;

    // Constructor
    public DependencyDAO(Context context) {
        dbHelper = new AppointmentDBHelper(context);
    }

    // Method to insert a new dependency
    public int insertDependency(Dependency dependency) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put(DEPENDENCY_NAME, dependency.getDependency_name());
        values.put(CATEGORY, dependency.getCategory());

        // Insert the row and return the id
        int id = (int) db.insert(DEPENDENCY, null, values);

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
        values.put(DEPENDENCY_NAME, dependency.getDependency_name());
        values.put(CATEGORY, dependency.getCategory());

        // Update the row and return the number of affected rows
        int rows = db.update(DEPENDENCY, values, "id = ?", new String[]{String.valueOf(dependency.getId())});

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
        int rows = db.delete(DEPENDENCY, "id = ?", new String[]{String.valueOf(id)});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }


}

