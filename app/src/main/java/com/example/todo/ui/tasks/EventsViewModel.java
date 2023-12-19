package com.example.todo.ui.tasks;

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

public class EventsViewModel extends ViewModel {
    // A LiveData object to hold the list of completed appointments
    private MutableLiveData<List<Appointment>> completedAppointments;

    // A LiveData object to hold the list of cancelled appointments
    private MutableLiveData<List<Appointment>> cancelledAppointments;

    // A DAO object to get the data from
    private AppointmentDAO appointmentDAO;

    // A constructor that takes an AppointmentDAO as a parameter and assigns it to the field
    public EventsViewModel(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        // Initialize the LiveData objects
        completedAppointments = new MutableLiveData<List<Appointment>>();
        cancelledAppointments = new MutableLiveData<List<Appointment>>();
    }

    // A method to get the LiveData object for completed appointments
    public LiveData<List<Appointment>> getCompletedAppointments(SQLiteDatabase db) {
        // Load the appointments from the DAO with the status "completed"
        loadAppointments("Completed", db);
        // Return the LiveData object
        return completedAppointments;
    }

    // A method to get the LiveData object for cancelled appointments
    public LiveData<List<Appointment>> getCancelledAppointments(SQLiteDatabase db) {
        // Load the appointments from the DAO with the status "canceled"
        loadAppointments("Canceled", db);
        // Return the LiveData object
        return cancelledAppointments;
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
                // Post the result to the appropriate LiveData object
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (status.equals("Completed")) {
                            completedAppointments.setValue(appointmentList);
                        } else if (status.equals("Canceled")) {
                            cancelledAppointments.setValue(appointmentList);
                        }
                    }
                });
            }
        });
    }
}
