package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.DBHelper;
import com.example.todo.data.models.Dependency;

// DependencyDAO class
public class DependencyDAO {


    // Database helper instance
    private final DBHelper dbHelper;

    // Constructor
    public DependencyDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Method to insert a new dependency
    public int insertDependency(Dependency dependency, SQLiteDatabase db) {


        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put(DBHelper.DEPENDENCY_NAME, dependency.getDependency_name());
        values.put(DBHelper.DEPENDENCY_CATEGORY, dependency.getCategory());

        // Insert the row and return the id
        int id = (int) db.insert(DBHelper.TABLE_DEPENDENCY, null, values);



        // Return the id
        return id;
    }

    // Method to update an existing dependency
    public int updateDependency(Dependency dependency, SQLiteDatabase db) {


        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put(DBHelper.DEPENDENCY_NAME, dependency.getDependency_name());
        values.put(DBHelper.DEPENDENCY_CATEGORY, dependency.getCategory());

        // Update the row and return the number of affected rows
        int rows = db.update(DBHelper.TABLE_DEPENDENCY, values, DBHelper.DEPENDENCY_ID + " = ?", new String[]{String.valueOf(dependency.getId())});



        // Return the number of affected rows
        return rows;
    }

    // Method to delete an existing dependency
    public int deleteDependency(int id,SQLiteDatabase db) {


        // Delete the row and return the number of affected rows
        int rows = db.delete(DBHelper.TABLE_DEPENDENCY, DBHelper.DEPENDENCY_ID + " = ?", new String[]{String.valueOf(id)});



        // Return the number of affected rows
        return rows;
    }


}

