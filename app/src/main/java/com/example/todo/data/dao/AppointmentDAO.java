package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.DBHelper;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// AppointmentDAO class
public class AppointmentDAO {


    private DBHelper dbHelper;
  
    DependencyDAO dependencyDAO;
    AgentDAO agentDAO;
    // Database helper instance


    // Constructor
    public AppointmentDAO(Context context) {
        dbHelper = new DBHelper(context);

    }
    // Method to insert a new appointment
    public int insertAppointment(Appointment appointment, Agent agent,Dependency dependency, AgentDAO agentDAO, DependencyDAO dependencyDAO) {

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Begin a transaction
        db.beginTransaction();

        int id;
        try {


            // Create a content values object to store the values
            ContentValues appo_values = new ContentValues();


            // Insert the dependency data and get the id

            int dependency_id = dependencyDAO.insertDependency(dependency, db);
            // Insert the agent data and get the id


            int agent_id = agentDAO.insertAgent(agent, db);
            // Insert the appointment data with the foreign keys
            appo_values.put(DBHelper.APPOINTMENT_START_DATETIME, appointment.getStart_datetime());
            appo_values.put(DBHelper.APPOINTMENT_REMINDER_DATETIME, appointment.getReminder_datetime());
            appo_values.put(DBHelper.APPOINTMENT_STAT, appointment.getStat());
            appo_values.put(DBHelper.APPOINTMENT_DEPENDENCY_ID, dependency_id);
            appo_values.put(DBHelper.APPOINTMENT_AGENT_ID, agent_id);
            id = (int) db.insert(DBHelper.TABLE_APPOINTMENT, null, appo_values);

            // Set the transaction as successful
            db.setTransactionSuccessful();
        } finally {
            // End the transaction
            db.endTransaction();


        }


        // Close the database
        db.close();

        // Return the id
        return id;
    }
    // Method to update an existing appointment
    public Map<String, Integer> updateAppointment(Appointment appointment, Dependency dependency, Agent agent, DependencyDAO dependencyDAO, AgentDAO agentDAO) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();
        values.put(DBHelper.APPOINTMENT_START_DATETIME, appointment.getStart_datetime());
        values.put(DBHelper.APPOINTMENT_REMINDER_DATETIME, appointment.getReminder_datetime());
        values.put(DBHelper.APPOINTMENT_STAT, appointment.getStat());
        values.put(DBHelper.APPOINTMENT_DEPENDENCY_ID, appointment.getDependency_id());
        values.put(DBHelper.APPOINTMENT_AGENT_ID, appointment.getAgent_id());
        // Update the row and return the number of affected rows
        int appointment_rows = db.update(DBHelper.TABLE_APPOINTMENT, values,DBHelper.APPOINTMENT_ID + " = ?", new String[]{String.valueOf(appointment.getId())});
        int dependency_rows = dependencyDAO.updateDependency(dependency);
        int agent_rows = agentDAO.updateAgent(agent);

        // Close the database
        db.close();
        // Map all the returned affected rows in a K,V pairs
        Map<String, Integer> rows = new HashMap<>();

        rows.put("appointment_rows", appointment_rows);
        rows.put("dependency_rows", dependency_rows);
        rows.put("agent_rows", agent_rows);

        // Return the number of affected rows
        return rows;
    }

    public int updateAppointmentStatus(int id, String status) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a content values object to store the values
        ContentValues values = new ContentValues();

        values.put(DBHelper.APPOINTMENT_STAT, status);

        // Update the row and return the number of affected rows
        int rows = db.update(DBHelper.TABLE_APPOINTMENT, values, DBHelper.APPOINTMENT_ID + " = ?", new String[]{String.valueOf(id)});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Method to delete an existing appointment
    public boolean deleteAppointment(int appointment_id, int agent_id, int dependency_id, DependencyDAO dependencyDAO, AgentDAO agentDAO) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Begin a transaction
        db.beginTransaction();
        int agent_rows = agentDAO.deleteAgent(agent_id, db);
        int dependency_rows = dependencyDAO.deleteDependency(dependency_id, db);

        // Delete the row and return the number of affected rows
        int appointment_rows = db.delete(DBHelper.TABLE_APPOINTMENT, DBHelper.APPOINTMENT_ID + " = ?", new String[]{String.valueOf(appointment_id)});

        // Set the transaction as successful
        db.setTransactionSuccessful();

        // End the transaction
        db.endTransaction();

        // Close the database
        db.close();
        return (agent_rows>=0) && (dependency_rows >= 0) && (appointment_rows >= 0);

        // Return the number of affected rows
    }

    // Method to get an appointment by its id
    public Appointment getAppointmentById(long id) {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns to select
        String[] columns = {
                DBHelper.APPOINTMENT_ID,
                DBHelper.APPOINTMENT_START_DATETIME,
                DBHelper.APPOINTMENT_REMINDER_DATETIME,
                DBHelper.APPOINTMENT_STAT,
                DBHelper.APPOINTMENT_DEPENDENCY_ID,
                DBHelper.APPOINTMENT_AGENT_ID
        };

        // Define the selection criteria
        String selection = DBHelper.APPOINTMENT_ID + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {String.valueOf(id)};

        // Query the database and get a cursor
        Cursor cursor = db.query(
                DBHelper.TABLE_APPOINTMENT, // The table name
                columns, // The columns to select
                selection, // The selection clause
                selectionArgs, // The selection arguments
                null, // The group by clause
                null, // The having clause
                null // The order by clause
        );

        // Check if the cursor is not null and has a row
        if (cursor != null && cursor.moveToFirst()) {
          // Check the value before passing it to the cursor.getString() method
            int idIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_ID);
            int start_datetimeIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_START_DATETIME);
            int reminder_datetimeIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_REMINDER_DATETIME);
            int statIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_STAT);
            int dependency_idIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_DEPENDENCY_ID);
            int agent_idIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_AGENT_ID);

            if (idIndex >= 0 && start_datetimeIndex >= 0 && reminder_datetimeIndex >= 0 && statIndex >= 0 && dependency_idIndex >= 0 && agent_idIndex >= 0) {
                // Create a new Appointment object from the cursor data
                Appointment appointment = new Appointment(
                        cursor.getInt(idIndex),
                        cursor.getString(start_datetimeIndex),
                        cursor.getString(reminder_datetimeIndex),
                        cursor.getString(statIndex),
                        cursor.getInt(dependency_idIndex),
                        cursor.getInt(agent_idIndex)
                );

                // Return the appointment object
                return appointment;

            }


            // Close the cursor
            cursor.close();


        }
        db.close();
        // Return null if the cursor is
        // null or empty
        return null;
    }


    // Query all appointments in this day with the agent and dependency data
    public List<Appointment> getAllAppointmentsToday() {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Create a query string with a join and a where clause
        String query = "SELECT * FROM " + DBHelper.TABLE_APPOINTMENT + " a " +
                "INNER JOIN " + DBHelper.TABLE_AGENT + " b ON a." + DBHelper.APPOINTMENT_AGENT_ID + " = b." + DBHelper.AGENT_ID + " " +
                "INNER JOIN " + DBHelper.TABLE_DEPENDENCY + " c ON a." + DBHelper.APPOINTMENT_DEPENDENCY_ID + " = c." + DBHelper.DEPENDENCY_ID + " " +
                "WHERE date("+ DBHelper.APPOINTMENT_START_DATETIME +") = date('now', 'localtime') AND a." + DBHelper.APPOINTMENT_STAT + " = 'pending'";

        // Execute the query and get the cursor'
        Cursor cursor = db.rawQuery(query, null);

        // Create a list of Appointment objects
        List<Appointment> appointments = new ArrayList<>();

        // Loop through the cursor and convert the data into Appointment objects
        if (cursor.moveToFirst()) {
            do {
                // Get the column index for each column name
                int idIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_ID);
                int datetimeIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_START_DATETIME);
                int reminderIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_REMINDER_DATETIME);
                int statIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_STAT);
                int agentIdIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_AGENT_ID);
                int agentNameIndex = cursor.getColumnIndex(DBHelper.AGENT_NAME);
                int agentCategoryIndex = cursor.getColumnIndex(DBHelper.AGENT_CATEGORY);
                int agentPhoneIndex = cursor.getColumnIndex(DBHelper.AGENT_PHONE_NUMBER);
                int dependencyIdIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_DEPENDENCY_ID);
                int dependencyNameIndex = cursor.getColumnIndex(DBHelper.DEPENDENCY_NAME);
                int dependencyCategoryIndex = cursor.getColumnIndex(DBHelper.DEPENDENCY_CATEGORY);

               // Declare the variables to store the values
                int id;
                String datetime;
                String reminder;
                String stat;
                int agent_id;
                String agent_name;
                String agent_category;
                String agent_phone;
                int dependency_id;
                String dependency_name;
                String dependency_category;
                 // Check if the columns exists
                if (idIndex >= 0 && datetimeIndex >= 0 && reminderIndex >= 0 && statIndex >= 0 && agentIdIndex >= 0 && agentNameIndex >= 0
                    && agentCategoryIndex >= 0 && agentPhoneIndex >= 0 && dependencyIdIndex >= 0  && dependencyNameIndex >= 0 && dependencyCategoryIndex >= 0 ) {

                    // Get the values from the cursor at index
                    id = cursor.getInt(idIndex);
                    datetime = cursor.getString(datetimeIndex);
                    reminder = cursor.getString(reminderIndex);
                    stat = cursor.getString(statIndex);
                    agent_id = cursor.getInt(agentIdIndex);
                    agent_name = cursor.getString(agentNameIndex);
                    agent_category = cursor.getString(agentCategoryIndex);
                    agent_phone = cursor.getString(agentPhoneIndex);
                    dependency_id = cursor.getInt(dependencyIdIndex);
                    dependency_name = cursor.getString(dependencyNameIndex);
                    dependency_category = cursor.getString(dependencyCategoryIndex);
                    // Create an Appointment object with the data
                    Appointment appointment = new Appointment(id,
                            datetime,
                            reminder,
                            stat,
                            agent_id,
                            agent_name,
                            agent_category,
                            agent_phone,
                            dependency_id,
                            dependency_name,
                            dependency_category);

                    // Add the Appointment object to the list
                    appointments.add(appointment);
                }







            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();
        db.close();

        // Return the list of Appointment objects
        return appointments;
    }


    public List<Appointment> getAppointmentsByStatus(String stat) {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a query string with a join and a where clause
        String query = "SELECT * FROM " + DBHelper.TABLE_APPOINTMENT + " a " +
                "INNER JOIN " + DBHelper.TABLE_AGENT + " b ON a." + DBHelper.APPOINTMENT_AGENT_ID + " = b." + DBHelper.AGENT_ID + " " +
                "INNER JOIN " + DBHelper.TABLE_DEPENDENCY + " c ON a." + DBHelper.APPOINTMENT_DEPENDENCY_ID + " = c." + DBHelper.DEPENDENCY_ID + " " +
                "WHERE  a." + DBHelper.APPOINTMENT_STAT + " = :stat";

        // Execute the query and get the cursor
        Cursor cursor = db.rawQuery(query, new String[]{stat});

        // Create a list of Appointment objects
        List<Appointment> appointments = new ArrayList<>();

        // Loop through the cursor and convert the data into Appointment objects
        if (cursor.moveToFirst()) {
            do {
                // Get the column index for each column name
                int idIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_ID);
                int datetimeIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_START_DATETIME);
                int reminderIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_REMINDER_DATETIME);
                int statIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_STAT);
                int agentIdIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_AGENT_ID);
                int agentNameIndex = cursor.getColumnIndex(DBHelper.AGENT_NAME);
                int agentCategoryIndex = cursor.getColumnIndex(DBHelper.AGENT_CATEGORY);
                int agentPhoneIndex = cursor.getColumnIndex(DBHelper.AGENT_PHONE_NUMBER);
                int dependencyIdIndex = cursor.getColumnIndex(DBHelper.APPOINTMENT_DEPENDENCY_ID);
                int dependencyNameIndex = cursor.getColumnIndex(DBHelper.DEPENDENCY_NAME);
                int dependencyCategoryIndex = cursor.getColumnIndex(DBHelper.DEPENDENCY_CATEGORY);

                // Declare the variables to store the values
                int id;
                String datetime;
                String reminder;
                String status;
                int agent_id;
                String agent_name;
                String agent_category;
                String agent_phone;
                int dependency_id;
                String dependency_name;
                String dependency_category;

                if (idIndex >= 0 && datetimeIndex >= 0 && reminderIndex >= 0 && statIndex >= 0 && agentIdIndex >= 0 && agentNameIndex >= 0
                        && agentCategoryIndex >= 0 && agentPhoneIndex >= 0 && dependencyIdIndex >= 0  && dependencyNameIndex >= 0 && dependencyCategoryIndex >= 0 ) {
                    //get the data from the cursor at index

                    id = cursor.getInt(idIndex);
                    datetime = cursor.getString(datetimeIndex);
                    reminder = cursor.getString(reminderIndex);
                    stat = cursor.getString(statIndex);
                    agent_id = cursor.getInt(agentIdIndex);
                    agent_name = cursor.getString(agentNameIndex);
                    agent_category = cursor.getString(agentCategoryIndex);
                    agent_phone = cursor.getString(agentPhoneIndex);
                    dependency_id = cursor.getInt(dependencyIdIndex);
                    dependency_name = cursor.getString(dependencyNameIndex);
                    dependency_category = cursor.getString(dependencyCategoryIndex);

                    // Create an Appointment object with the data
                    Appointment appointment = new Appointment(id,
                            datetime,
                            reminder,
                            stat,
                            agent_id,
                            agent_name,
                            agent_category,
                            agent_phone,
                            dependency_id,
                            dependency_name,
                            dependency_category);

                    // Add the Appointment object to the list
                    appointments.add(appointment);
                }


            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();
        //Close the database
        db.close();

        // Return the list of Appointment objects
        return appointments;
    }




}
