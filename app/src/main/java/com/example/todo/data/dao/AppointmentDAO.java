package com.example.todo.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.AppointmentDBHelper;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;

import java.util.List;

// AppointmentDAO class
public class AppointmentDAO {
    //constants for the table names and column names

    public static final String TABLE_DEPENDENCY = "dependency";

    public static final String DEPENDENCY_ID = "dependency_id";
    private static final String DEPENDENCY_NAME ="dependency_name" ;
    private static final String DEPENDENCY_CATEGORY ="dependency_category" ;
    public static final String TABLE_AGENT = "agent";


    private static final String AGENT_ID = "agent_id";
    private static final String AGENT_NAME = "agent_name";
    private static final String AGENT_CATEGORY = "agent_category";
    private static final String AGENT_PHONE_NUMBER = "agent_phone_number";


    public static final String TABLE_APPOINTMENT = "appointment";

    private static final String APPOINTMENT_ID = "appointment_id";
    public static final String APPOINTMENT_START_DATETIME = "start_datetime";
    public static final String APPOINTMENT_REMINDER_DATETIME = "reminder_datetime";
    public static final String APPOINTMENT_STAT = "stat";
    public static final String APPOINTMENT_DEPENDENCY_ID = "dependency_id";
    public static final String APPOINTMENT_AGENT_ID = "agent_id";

    DependencyDAO dependencyDAO;
    AgentDAO agentDAO;
    // Database helper instance
    private AppointmentDBHelper dbHelper;

    // Constructor
    public AppointmentDAO(Context context) {
        dbHelper = new AppointmentDBHelper(context);

    }
    // Method to insert a new appointment
    public int insertAppointment(Appointment appointment, Agent agent,Dependency dependency ) {

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Begin a transaction
        db.beginTransaction();

        // Create a content values object to store the values
        ContentValues appo_values = new ContentValues();
        ContentValues dep_values = new ContentValues();
        ContentValues agent_values = new ContentValues();


        // Insert the dependency data and get the id

        dep_values.put(DEPENDENCY_NAME, dependency.getDependency_name());
        dep_values.put(DEPENDENCY_CATEGORY, dependency.getCategory());
        int dependency_id = (int) db.insert(TABLE_DEPENDENCY, null, dep_values);

        // Insert the agent data and get the id

        agent_values.put(AGENT_NAME, agent.getAgent_name());
        agent_values.put(AGENT_CATEGORY, agent.getCategory());
        agent_values.put(AGENT_PHONE_NUMBER, agent.getPhone_number());
        int agent_id = (int) db.insert(TABLE_AGENT, null, agent_values);

        // Insert the appointment data with the foreign keys
        appo_values.put(APPOINTMENT_START_DATETIME, appointment.getStart_datetime());
        appo_values.put(APPOINTMENT_REMINDER_DATETIME, appointment.getReminder_datetime());
        appo_values.put(APPOINTMENT_STAT, appointment.getStat());
        appo_values.put(DEPENDENCY_ID, dependency_id);
        appo_values.put(AGENT_ID, agent_id);
        int id = (int) db.insert(TABLE_APPOINTMENT, null, appo_values);

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
        values.put(APPOINTMENT_START_DATETIME, appointment.getStart_datetime());
        values.put(APPOINTMENT_REMINDER_DATETIME, appointment.getReminder_datetime());
        values.put(APPOINTMENT_STAT, appointment.getStat());
        values.put(DEPENDENCY_ID, appointment.getDependency_id());
        values.put(AGENT_ID, appointment.getAgent_id());
        // Update the row and return the number of affected rows
        int rows = db.update(TABLE_APPOINTMENT, values, "id = ?", new String[]{String.valueOf(appointment.getId())});

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
        int rows = db.delete(TABLE_APPOINTMENT, "appointment_id = ?", new String[]{String.valueOf(id)});

        // Close the database
        db.close();

        // Return the number of affected rows
        return rows;
    }

    // Method to get an appointment by its id
    public Appointment getAppointmentById(long id) {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns to select
        String[] columns = {
                AppointmentDAO.APPOINTMENT_ID,
                AppointmentDAO.APPOINTMENT_START_DATETIME,
                AppointmentDAO.APPOINTMENT_REMINDER_DATETIME,
                AppointmentDAO.APPOINTMENT_STAT,
                AppointmentDAO.APPOINTMENT_DEPENDENCY_ID,
                AppointmentDAO.APPOINTMENT_AGENT_ID
        };

        // Define the selection criteria
        String selection = AppointmentDAO.APPOINTMENT_ID + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {String.valueOf(id)};

        // Query the database and get a cursor
        Cursor cursor = db.query(
                AppointmentDAO.TABLE_APPOINTMENT, // The table name
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
            int idIndex = cursor.getColumnIndex(AppointmentDAO.APPOINTMENT_ID);
            int start_datetimeIndex = cursor.getColumnIndex(AppointmentDAO.APPOINTMENT_START_DATETIME);
            int reminder_datetimeIndex = cursor.getColumnIndex(AppointmentDAO.APPOINTMENT_REMINDER_DATETIME);
            int statIndex = cursor.getColumnIndex(AppointmentDAO.APPOINTMENT_STAT);
            int dependency_idIndex = cursor.getColumnIndex(AppointmentDAO.APPOINTMENT_DEPENDENCY_ID);
            int agent_idIndex = cursor.getColumnIndex(AppointmentDAO.APPOINTMENT_AGENT_ID);

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
    public List<Appointment> getAllAppointmentsInThisDay() {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a query string with a join and a where clause
        String query = "SELECT * FROM " + TABLE_APPOINTMENT + " a " +
                "INNER JOIN " + TABLE_AGENT + " b ON a." + APPOINTMENT_AGENT_ID + " = b." + AGENT_ID + " " +
                "INNER JOIN " + TABLE_DEPENDENCY + " c ON a." + DEPENDENCY_ID + " = c." + DEPENDENCY_ID + " " +
                "WHERE date(" + APPOINTMENT_START_DATETIME + ") = date('now') AND a." + APPOINTMENT_STAT + " = 'pending'";

        // Execute the query and return the cursor
        return db.rawQuery(query, null);
    }


    public List<Appointment> getAllAppointmentsWithStat(String stat) {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a query string with a join and a where clause
        String query = "SELECT * FROM " + TABLE_APPOINTMENT + " a " +
                "INNER JOIN " + TABLE_AGENT + " b ON a." + APPOINTMENT_AGENT_ID + " = b." + AGENT_ID + " " +
                "INNER JOIN " + TABLE_DEPENDENCY + " c ON a." + DEPENDENCY_ID + " = c." + DEPENDENCY_ID + " " +
                "WHERE  a." + APPOINTMENT_STAT + " = :stat";

        // Execute the query and return the cursor
        return db.rawQuery(query, new String[]{stat});
    }



}
