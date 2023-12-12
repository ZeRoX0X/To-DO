package com.example.todo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.data.DBHelper;
import com.example.todo.data.dao.AgentDAO;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.dao.DependencyDAO;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AppointmentDAOTest {

    // Declare the DAO and the database objects
    private AppointmentDAO appo_dao;
    private AgentDAO agent_dao;
    private DependencyDAO dep_dao;
    private SQLiteDatabase db;

    // Create and close the database before and after each test
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Use the DBHelper class to create the in-memory database
        DBHelper dbHelper = new DBHelper(context);
        //db = dbHelper.getWritableDatabase();
        appo_dao = new AppointmentDAO(context);
        dep_dao = new DependencyDAO(context);
        agent_dao = new AgentDAO(context);
    }

    @After
    public void closeDb() {
       // db.close();
    }

    // Test the insertAppointment method
    @Test
    public void testInsertAppointment() throws Exception {
        appo_dao.deleteAppointment(1,1,1,dep_dao,agent_dao);
        // Create some sample data
        Appointment appointment = new Appointment("2021-12-09 10:00:00", "2021-12-09 09:00:00", "pending");
        Agent agent = new Agent("John", "sales", "1234567890");
        Dependency dependency = new Dependency("ABC", "retail");

        // Insert the data into the database
        long id =  appo_dao.insertAppointment(appointment, agent, dependency, agent_dao,dep_dao);

        // Query the database for the inserted data
        Appointment result = appo_dao.getAppointmentById(id);
        boolean deleted = appo_dao.deleteAppointment(1,1,1,dep_dao,agent_dao);
        // Assert that the result is not null and matches the expected data
        assertTrue(deleted);
        assertNotNull(result);
        assertEquals(1,result.getId());
        assertEquals(appointment.getStart_datetime(), result.getStart_datetime());
        assertEquals(appointment.getReminder_datetime(), result.getReminder_datetime());
        assertEquals(appointment.getStat(), result.getStat());
        assertEquals(1,result.getDependency_id());
        assertEquals(1,result.getAgent_id());


    }

    // Test the getAllAppointmentsInThisDay method
    @Test
    public void testGetAllAppointmentsInThisDay() throws Exception {
            // Create some sample data
            Appointment appointment1 = new Appointment("2023-12-12 11:00:00", "2021-12-09 09:00:00", "pending");
            Appointment appointment2 = new Appointment("2023-12-12 09:00:00", "2021-12-09 10:00:00", "pending");
            Appointment appointment3 = new Appointment("2021-12-11 12:00:00", "2021-12-10 11:00:00", "done");
            Agent agent = new Agent("John", "sales", "1234567890");
            Dependency dependency = new Dependency("ABC", "retail");

            // Insert the data into the database
            appo_dao.insertAppointment(appointment1, agent, dependency, agent_dao, dep_dao);
            appo_dao.insertAppointment(appointment2, agent, dependency, agent_dao, dep_dao);
            appo_dao.insertAppointment(appointment3, agent, dependency, agent_dao, dep_dao);
        Appointment appointment1e = new Appointment(1,"2023-12-12 11:00:00", "2021-12-09 09:00:00", "pending",1,"John", "sales", "1234567890",1,"ABC", "retail");
        Appointment appointment2e = new Appointment(2,"2023-12-12 09:00:00", "2021-12-09 10:00:00", "pending",2,"John", "sales", "1234567890",2,"ABC", "retail");
        Appointment appointment3e = new Appointment(3,"2021-12-11 11:00:00", "2021-12-10 11:00:00", "done",3,"John", "sales", "1234567890",3,"ABC", "retail");

            // Get the list of appointments for the date 2021-12-09
            List<Appointment> result = appo_dao.getAllAppointmentsToday();
        appo_dao.deleteAppointment(1,1,1,dep_dao,agent_dao);
        appo_dao.deleteAppointment(2,2,2,dep_dao,agent_dao);
        appo_dao.deleteAppointment(3,3,3,dep_dao,agent_dao);

            // Assert that the result is not null and has the expected size and data
            assertNotNull(result);

            assertEquals(2, result.size());
            // Use the getters and assert equality based on the values
            assertEquals(appointment1e.getId(), result.get(0).getId());
            assertEquals(appointment1e.getStart_datetime(), result.get(0).getStart_datetime());
            assertEquals(appointment1e.getReminder_datetime(), result.get(0).getReminder_datetime());
            assertEquals(appointment1e.getStat(), result.get(0).getStat());
            assertEquals(appointment1e.getDependency_id(), result.get(0).getDependency_id());
            assertEquals(appointment1e.getAgent_id(), result.get(0).getAgent_id());
            assertEquals(appointment1e.getAgent_name(), result.get(0).getAgent_name());
            assertEquals(appointment1e.getAgent_category(), result.get(0).getAgent_category());
            assertEquals(appointment1e.getAgent_phone(), result.get(0).getAgent_phone());
            assertEquals(appointment1e.getDependency_id(), result.get(0).getDependency_id());
            assertEquals(appointment1e.getDependency_name(), result.get(0).getDependency_name());
            assertEquals(appointment1e.getDependency_category(), result.get(0).getDependency_category());
            assertEquals(appointment2e.getId(), result.get(1).getId());
            assertEquals(appointment2e.getStart_datetime(), result.get(1).getStart_datetime());
            assertEquals(appointment2e.getReminder_datetime(), result.get(1).getReminder_datetime());
            assertEquals(appointment2e.getStat(), result.get(1).getStat());
            assertEquals(appointment2e.getDependency_id(), result.get(1).getDependency_id());
            assertEquals(appointment2e.getAgent_id(), result.get(1).getAgent_id());
            assertEquals(appointment2e.getAgent_name(), result.get(1).getAgent_name());
            assertEquals(appointment2e.getAgent_category(), result.get(1).getAgent_category());
            assertEquals(appointment2e.getAgent_phone(), result.get(1).getAgent_phone());
            assertEquals(appointment2e.getDependency_id(), result.get(1).getDependency_id());
            assertEquals(appointment2e.getDependency_name(), result.get(1).getDependency_name());
            assertEquals(appointment2e.getDependency_category(), result.get(1).getDependency_category());



    }


    // Test the getAllAppointmentsInThisDay method
    @Test
    public void testGetAllAppointmentsWithStatus() throws Exception {
        // Create some sample data
        Appointment appointment1 = new Appointment("2023-12-12 11:00:00", "2021-12-09 09:00:00", "pending");
        Appointment appointment2 = new Appointment("2023-12-12 09:00:00", "2021-12-09 10:00:00", "pending");
        Appointment appointment3 = new Appointment("2021-12-11 12:00:00", "2021-12-10 11:00:00", "done");
        Agent agent = new Agent("John", "sales", "1234567890");
        Dependency dependency = new Dependency("ABC", "retail");

        // Insert the data into the database
        appo_dao.insertAppointment(appointment1, agent, dependency, agent_dao, dep_dao);
        appo_dao.insertAppointment(appointment2, agent, dependency, agent_dao, dep_dao);
        appo_dao.insertAppointment(appointment3, agent, dependency, agent_dao, dep_dao);
        Appointment appointment1e = new Appointment(1,"2023-12-12 11:00:00", "2021-12-09 09:00:00", "pending",1,"John", "sales", "1234567890",1,"ABC", "retail");
        Appointment appointment2e = new Appointment(2,"2023-12-12 09:00:00", "2021-12-09 10:00:00", "pending",2,"John", "sales", "1234567890",2,"ABC", "retail");
        Appointment appointment3e = new Appointment(3,"2021-12-11 11:00:00", "2021-12-10 11:00:00", "done",3,"John", "sales", "1234567890",3,"ABC", "retail");

        // Get the list of appointments for the date 2021-12-09
        List<Appointment> result = appo_dao.getAllAppointmentsWithStat("pending");
        appo_dao.deleteAppointment(1,1,1,dep_dao,agent_dao);
        appo_dao.deleteAppointment(2,2,2,dep_dao,agent_dao);
        appo_dao.deleteAppointment(3,3,3,dep_dao,agent_dao);

        // Assert that the result is not null and has the expected size and data
        assertNotNull(result);
        assertEquals(2, result.size());
        // Use the getters and assert equality based on the values
        assertEquals(appointment1e.getId(), result.get(0).getId());
        assertEquals(appointment1e.getStart_datetime(), result.get(0).getStart_datetime());
        assertEquals(appointment1e.getReminder_datetime(), result.get(0).getReminder_datetime());
        assertEquals(appointment1e.getStat(), result.get(0).getStat());
        assertEquals(appointment1e.getDependency_id(), result.get(0).getDependency_id());
        assertEquals(appointment1e.getAgent_id(), result.get(0).getAgent_id());
        assertEquals(appointment1e.getAgent_name(), result.get(0).getAgent_name());
        assertEquals(appointment1e.getAgent_category(), result.get(0).getAgent_category());
        assertEquals(appointment1e.getAgent_phone(), result.get(0).getAgent_phone());
        assertEquals(appointment1e.getDependency_id(), result.get(0).getDependency_id());
        assertEquals(appointment1e.getDependency_name(), result.get(0).getDependency_name());
        assertEquals(appointment1e.getDependency_category(), result.get(0).getDependency_category());
        assertEquals(appointment2e.getId(), result.get(1).getId());
        assertEquals(appointment2e.getStart_datetime(), result.get(1).getStart_datetime());
        assertEquals(appointment2e.getReminder_datetime(), result.get(1).getReminder_datetime());
        assertEquals(appointment2e.getStat(), result.get(1).getStat());
        assertEquals(appointment2e.getDependency_id(), result.get(1).getDependency_id());
        assertEquals(appointment2e.getAgent_id(), result.get(1).getAgent_id());
        assertEquals(appointment2e.getAgent_name(), result.get(1).getAgent_name());
        assertEquals(appointment2e.getAgent_category(), result.get(1).getAgent_category());
        assertEquals(appointment2e.getAgent_phone(), result.get(1).getAgent_phone());
        assertEquals(appointment2e.getDependency_id(), result.get(1).getDependency_id());
        assertEquals(appointment2e.getDependency_name(), result.get(1).getDependency_name());
        assertEquals(appointment2e.getDependency_category(), result.get(1).getDependency_category());

    }



}




