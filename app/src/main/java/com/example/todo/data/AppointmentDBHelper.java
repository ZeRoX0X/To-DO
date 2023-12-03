package com.example.todo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AppointmentDBHelper  extends SQLiteOpenHelper {

    // Define the database name and version
    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABASE_VERSION = 1;

    // Define the path to the assets folder
    public static final String ASSETS_PATH = "databases";
    private final Context context;

    // Create a constructor that takes a context as a parameter
    public AppointmentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Assign the context to the field
        this.context = context;
    }

    // Override the onCreate method to copy the database from the assets folder
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Copy the database file from the assets folder
        try {
            copyDatabaseFromAssets(db);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Override the onUpgrade method to drop and recreate the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete the existing database file
        deleteDatabaseFile(db);
        // Copy the database file from the assets folder
        try {
            copyDatabaseFromAssets(db);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // A helper method to copy the database file from the assets folder
    // A helper method to copy the database file from the assets folder
    private void copyDatabaseFromAssets(SQLiteDatabase db) throws IOException {
        // Get the file object of the database file in the internal storage
        File file = context.getDatabasePath(DATABASE_NAME);
        // Check if the file does not exist
        if (!file.exists()) {
            // Get the input stream of the database file from the assets folder
            InputStream inputStream = context.getAssets().open(ASSETS_PATH + File.separator + DATABASE_NAME);
            // Get the output stream of the database file in the internal storage
            OutputStream outputStream = new FileOutputStream(db.getPath());
            // Copy the bytes from the input stream to the output stream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            // Close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }


    // A helper method to delete the database file in the internal storage
    private void deleteDatabaseFile(SQLiteDatabase db) {

        // Get the file object of the database file
        File file = context.getDatabasePath(DATABASE_NAME);
        // Delete the file if it exists
        if (file.exists()) {
            file.delete();
        }
    }
}
