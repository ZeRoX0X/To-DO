package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.AppointmentDBHelper;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;

// AppointmentDAO class
public class AppointmentDAO {

    // Database helper instance
    private AppointmentDBHelper dbHelper;

    // Constructor
    public AppointmentDAO(Context context) {
        dbHelper = new AppointmentDBHelper(context);
    }

    // Method to insert a new appointment
    public long insertAppointment(Appointment appointment, Agent agent,Dependency dependency ) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Begin a transaction
        db.beginTransaction();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put("start_datetime", appointment.getStart_datetime());
        values.put("end_datetime", appointment.getEnd_datetime());
        values.put("reminder_datetime", appointment.getReminder_datetime());
        values.put("stat", appointment.getStat());

        // Insert the dependency data and get the id
        //Dependency dependency = appointment.getDependency();
        values.put("place", dependency.getPlace());
        values.put("desc", dependency.getDesc());
        long dependency_id = db.insert("dependency", null, values);

        // Insert the agent data and get the id
        //Agent agent = appointment.getAgent();
        values.put("name", agent.getName());
        values.put("desc", agent.getDesc());
        values.put("phone_number", agent.getPhone_number());
        long agent_id = db.insert("agent", null, values);

        // Insert the appointment data with the foreign keys
        values.put("dependency_id", dependency_id);
        values.put("agent_id", agent_id);
        long id = db.insert("appointment", null, values);

        // Set the transaction as successful
        db.setTransactionSuccessful();

        // End the transaction
        db.endTransaction();

        // Close the database
        db.close();

        // Return the id
        return id;
    }


    // Method to update an existing appointment
    public int updateAppointment(Appointment appointment) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put("start_datetime", appointment.getStart_datetime());
        values.put("end_datetime", appointment.getEnd_datetime());
        values.put("reminder_datetime", appointment.getReminder_datetime());
        values.put("stat", appointment.getStat());
        values.put("dependency_id", appointment.getDependency_id());
        values.put("agent_id", appointment.getAgent_id());

        // Update the row and return the number of affected rows
        int rows = db.update("appointment", values, "id = ?", new String[]{String.valueOf(appointment.getId())});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Method to delete an existing appointment
    public int deleteAppointment(int id) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the row and return the number of affected rows
        int rows = db.delete("appointment", "id = ?", new String[]{String.valueOf(id)});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Other query methods can go here
}
