package com.example.todo.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBHelper extends SQLiteOpenHelper {

    //  database name and version
    public static final String DATABASE_NAME = "events.db";
    public static final int DATABASE_VERSION = 1;

    //constants for the table names and column names

    public static final String TABLE_DEPENDENCY = "dependency";

    public static final String DEPENDENCY_ID = "dependency_id";
    public static final String DEPENDENCY_NAME ="dependency_name" ;
    public static final String DEPENDENCY_CATEGORY ="dependency_category" ;
    public static final String TABLE_AGENT = "agent";


    public static final String AGENT_ID = "agent_id";
    public static final String AGENT_NAME = "agent_name";
    public static final String AGENT_CATEGORY = "agent_category";
    public static final String AGENT_PHONE_NUMBER = "agent_phone_number";


    public static final String TABLE_APPOINTMENT = "appointment";

    public static final String APPOINTMENT_ID = "appointment_id";
    public static final String APPOINTMENT_START_DATETIME = "start_datetime";
    public static final String APPOINTMENT_REMINDER_DATETIME = "reminder_datetime";
    public static final String APPOINTMENT_STAT = "status";
    public static final String APPOINTMENT_DEPENDENCY_ID = "appointment_dependency_id";
    public static final String APPOINTMENT_AGENT_ID = "appointment_agent_id";

    // Public constructor
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Override the onCreate method to create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the dependency table
        String CREATE_TABLE_DEPENDENCY = "CREATE TABLE IF NOT EXISTS " + TABLE_DEPENDENCY + " (" +
                DEPENDENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DEPENDENCY_NAME + " TEXT NOT NULL, " +
                DEPENDENCY_CATEGORY + " TEXT NOT NULL" +
                ");";

        // Create the agent table
        String CREATE_TABLE_AGENT = "CREATE TABLE IF NOT EXISTS " + TABLE_AGENT + " (" +
                AGENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AGENT_NAME + " TEXT NOT NULL, " +
                AGENT_CATEGORY + " TEXT NOT NULL , " +
                AGENT_PHONE_NUMBER + " TEXT NOT NULL" +
                ");";

        // Create the appointment table
        String CREATE_TABLE_APPOINTMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_APPOINTMENT + " (" +
                APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                APPOINTMENT_START_DATETIME + " TEXT NOT NULL, " +
                APPOINTMENT_REMINDER_DATETIME + " TEXT, " +
                APPOINTMENT_STAT + " TEXT NOT NULL, " +
                APPOINTMENT_DEPENDENCY_ID + " INTEGER NOT NULL, " +
                APPOINTMENT_AGENT_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + APPOINTMENT_DEPENDENCY_ID + ") REFERENCES " + TABLE_DEPENDENCY + " (" + DEPENDENCY_ID + "), " +
                "FOREIGN KEY (" + APPOINTMENT_AGENT_ID + ") REFERENCES " + TABLE_AGENT + " (" + DEPENDENCY_ID + ")" +
                ");";

        // Execute the SQL statements
        db.execSQL(CREATE_TABLE_DEPENDENCY);
        db.execSQL(CREATE_TABLE_AGENT);
        db.execSQL(CREATE_TABLE_APPOINTMENT);

        // Insert some data into the dependency table

        db.execSQL("INSERT INTO " + TABLE_DEPENDENCY + " (" + DEPENDENCY_NAME + ", " + DEPENDENCY_CATEGORY + ") " +
                "VALUES ('MayoClinics', 'Hospital'), " +
                "('SVU', 'School'), " +
                "('FIVE GUYS', 'Restaurant'), " +
                "('WalMart', 'Mall'), " +
                "('Starbucks', 'Cafe'), " +
                "('Chase', 'Bank'), " +
                "('Planet Fitness', 'Gym'), " +
                "('Smith & Jones', 'Lawyer'), " +
                "('Green Park', 'Park'), " +
                "('PetSmart', 'Pet Store'), " +
                "('Netflix', 'Entertainment'), " +
                "('Amazon', 'Online Shopping'), " +
                "('Apple', 'Technology'), " +
                "('McDonalds', 'Fast Food'), " +
                "('Target', 'Retail'), " +
                "('CVS', 'Pharmacy');");


        // Insert some data into the agent table
        db.execSQL("INSERT INTO " + TABLE_AGENT + " (" + AGENT_NAME + ", " + AGENT_CATEGORY + ", " + AGENT_PHONE_NUMBER + ") " +
                "VALUES    ('Anna', 'Hospital Receptionist', '123-456-7892'), " +
                "('Ben', 'Colleague', '234-567-8903'), " +
                "('Cathy', 'Restaurant Waitress', '345-678-9014'), " +
                "('Dan', 'Mall Security Guard', '456-789-0125'), " +
                "('Emma', 'Cafe Owner', '567-890-1236'), " +
                "('Fred', 'Bank Teller', '678-901-2347'), " +
                "('Gina', 'Gym Member', '789-012-3458'), " +
                "('Henry', 'Lawyer Assistant', '890-123-4569'), " +
                "('Isabel', 'Park Ranger', '901-234-5670'), " +
                "('Jake', 'Pet Store Clerk', '012-345-6781'), " +
                "('Kim', 'Entertainment Critic', '123-456-7893'), " +
                "('Liam', 'Online Shopper', '234-567-8904'), " +
                "('Mia', 'Technology Blogger', '345-678-9015'), " +
                "('Noah', 'Fast Food Worker', '456-789-0126'), " +
                "('Oliver', 'Retail Manager', '567-890-1237'), " +
                "('Penny', 'Pharmacy Technician', '678-901-2348');");

        // Create a LocalDate object
        LocalDate date = LocalDate.now();

        // Create a DateTimeFormatter object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the date
        String nowDate = date.format(formatter);
        String datetime1 = String.format("%s 17:00:00", nowDate);
        String datetime2 = String.format("%s 18:00:00", nowDate);
        String datetime3 = String.format("%s 19:00:00", nowDate);
        String datetime4 = String.format("%s 20:00:00", nowDate);

        // Insert 4 pending appointments
        db.execSQL("INSERT INTO " + TABLE_APPOINTMENT + " (" + APPOINTMENT_START_DATETIME + ", " + APPOINTMENT_REMINDER_DATETIME + ", " + APPOINTMENT_STAT + ", " + APPOINTMENT_DEPENDENCY_ID + ", " + APPOINTMENT_AGENT_ID + ") " +
                "VALUES ('"+datetime1+"', '2023-12-14 17:00:00', 'pending', 1, 1), " +
                "('"+datetime2+"', '2023-12-14 18:00:00', 'pending', 2, 2), " +
                "('"+datetime3+"', '2023-12-14 19:00:00', 'pending', 3, 3), " +
                "('"+datetime4+"', '2023-12-14 20:00:00', 'pending', 4, 4);");

        // Insert 4 completed appointments
        db.execSQL("INSERT INTO " + TABLE_APPOINTMENT + " (" + APPOINTMENT_START_DATETIME + ", " + APPOINTMENT_REMINDER_DATETIME + ", " + APPOINTMENT_STAT + ", " + APPOINTMENT_DEPENDENCY_ID + ", " + APPOINTMENT_AGENT_ID + ") " +
                "VALUES ('2023-12-15 09:00:00', '2023-12-14 10:00:00', 'completed', 5, 5), " +
                "('2023-12-15 10:00:00', '2023-12-14 09:00:00', 'completed', 6, 6), " +
                "('2023-12-15 11:00:00', '2023-12-14 12:00:00', 'completed', 7, 7), " +
                "('2023-12-15 12:00:00', '2023-12-14 11:00:00', 'completed', 8, 8);");

        // Insert 4 canceled appointments
        db.execSQL("INSERT INTO " + TABLE_APPOINTMENT + " (" + APPOINTMENT_START_DATETIME + ", " + APPOINTMENT_REMINDER_DATETIME + ", " + APPOINTMENT_STAT + ", " + APPOINTMENT_DEPENDENCY_ID + ", " + APPOINTMENT_AGENT_ID + ") " +
                "VALUES ('2023-12-15 13:00:00', '2023-12-14 14:00:00', 'canceled', 9, 9), " +
                "('2023-12-15 14:00:00', '2023-12-14 13:00:00', 'canceled', 10, 10), " +
                "('2023-12-15 15:00:00', '2023-12-14 16:00:00', 'canceled', 11, 11), " +
                "('2023-12-15 16:00:00', '2023-12-14 15:00:00', 'canceled', 12, 12);");




       // Insert 4 delayed appointments
        db.execSQL("INSERT INTO " + TABLE_APPOINTMENT + " (" + APPOINTMENT_START_DATETIME + ", " + APPOINTMENT_REMINDER_DATETIME + ", " + APPOINTMENT_STAT + ", " + APPOINTMENT_DEPENDENCY_ID + ", " + APPOINTMENT_AGENT_ID + ") " +
                "VALUES ('2023-12-15 21:00:00', '2023-12-14 21:00:00', 'delayed', 13, 13), " +
                "('2023-12-15 22:00:00', '2023-12-14 22:00:00', 'delayed', 14, 14), " +
                "('2023-12-15 23:00:00', '2023-12-14 23:00:00', 'delayed', 15, 15), " +
                "('2023-12-16 00:00:00', '2023-12-15 00:00:00', 'delayed', 16, 16);");


    }

    // Override the onUpgrade method to drop and recreate the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPENDENCY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);

        // Recreate the tables
        onCreate(db);
    }
}
