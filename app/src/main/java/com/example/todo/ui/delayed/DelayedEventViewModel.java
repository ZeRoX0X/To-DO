package com.example.todo.ui.delayed;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.models.Appointment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayedEventViewModel extends ViewModel {

    // A LiveData object to hold the list of appointments
    private MutableLiveData<List<Appointment>> appointments;

    // A DAO object to get the data from
    private AppointmentDAO appointmentDAO;

    // A constructor that takes an AppointmentDAO as a parameter and assigns it to the field
    public DelayedEventViewModel(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    // A method to get the LiveData object
    public LiveData<List<Appointment>> getAppointments(String status, SQLiteDatabase db) {
        // If the LiveData object is null, initialize it
        if (appointments == null) {
            appointments = new MutableLiveData<List<Appointment>>();
            // Load the appointments from the DAO
            loadAppointments(status, db);
        }
        // Return the LiveData object
        return appointments;
    }

    // A method to load the appointments from the DAO
    private void loadAppointments(String status, SQLiteDatabase db) {
        // Use an executor service and a handler to perform the task on a background thread and post the result to the UI thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Call the DAO method with the status parameter and store the result
                List<Appointment> appointmentList = appointmentDAO.getAppointmentsByStatus(status, db);
                // Post the result to the LiveData object
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        appointments.setValue(appointmentList);
                    }
                });
            }
        });
    }

    // A method to get the done appointments
    public LiveData<List<Appointment>> getDelayedEvents(SQLiteDatabase db) {
        return getAppointments("delayed", db);
    }
}