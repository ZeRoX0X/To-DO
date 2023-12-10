package com.example.todo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todo.data.AppointmentDBHelper;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
@RunWith(AndroidJUnit4.class)
public class AppointmentDAOTest {

    // Declare the DAO and the database objects
    private AppointmentDAO dao;
    private SQLiteDatabase db;

    // Create and close the database before and after each test
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Use the AppointmentDBHelper class to create the in-memory database
        AppointmentDBHelper dbHelper = new AppointmentDBHelper(context);
        db = dbHelper.getWritableDatabase();
        dao = new AppointmentDAO(context);
    }

    @After
    public void closeDb() {
        db.close();
    }

    // Test the insertAppointment method
    @Test
    public void testInsertAppointment() throws Exception {
        // Create some sample data
        Appointment appointment = new Appointment("2021-12-09 10:00:00", "2021-12-09 09:00:00", "pending");
        Agent agent = new Agent("John", "sales", "1234567890");
        Dependency dependency = new Dependency("ABC", "retail");

        // Insert the data into the database
        long id =  dao.insertAppointment(appointment, agent, dependency);

        // Query the database for the inserted data
        Appointment result = dao.getAppointmentById(id);
        // Assert that the result is not null and matches the expected data
        assertNotNull(result);
        assertEquals(1,result.getId());
        assertEquals(appointment.getStart_datetime(), result.getStart_datetime());
        assertEquals(appointment.getReminder_datetime(), result.getReminder_datetime());
        assertEquals(appointment.getStat(), result.getStat());
        assertEquals(1,result.getDependency_id());
        assertEquals(1,result.getAgent_id());
    }
}




