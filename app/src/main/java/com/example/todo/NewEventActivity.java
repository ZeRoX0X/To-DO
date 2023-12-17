package com.example.todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.data.dao.AgentDAO;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.dao.DependencyDAO;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;
import com.example.todo.databinding.ActivityNewEventBinding;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEventActivity extends AppCompatActivity {
    // New ---
    private TextView appointmentDate;
    private ImageButton date;
    private TextView appointmentTime;
    private ImageButton time;
    private ImageButton reminderTimeBtn;

    private Switch reminderTimeSwitch;
    private TextView reminderTime;
    private EditText agentName;
    private EditText agentCategory;
    private EditText agentPhone;
    private EditText dependencyName;
    private EditText dependencyCategory;


    private ActivityNewEventBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using the binding class
        binding = ActivityNewEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set the title of the activity
        setTitle("New Task");
        // Enable the display of the back button in the action bar
        // Get the support action bar
        ActionBar actionBar = getSupportActionBar();
        // Check if it is not null
        if (actionBar != null) {
            // Call the method on the action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

         
         agentName = findViewById(R.id.agent_name);
             
         agentCategory = findViewById(R.id.agent_category);
 
         agentPhone = findViewById(R.id.agent_phone);
 
         dependencyName = findViewById(R.id.dependency_name);
 
         dependencyCategory = findViewById(R.id.dependency_category);
 
        appointmentDate = findViewById(R.id.AppointmentDate);
 
        appointmentTime = findViewById(R.id.AppointmentTime);


        reminderTime = findViewById(R.id.ReminderTime);
        reminderTimeBtn = findViewById(R.id.ReminderTimeButton);

        Button saveButton = findViewById(R.id.newAppointmentButton);
        reminderTimeSwitch = findViewById(R.id.reminderSwitch);
        reminderTimeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reminderTimeSwitch.isChecked()) {
                    // Show the text views
                    reminderTime.setVisibility(View.VISIBLE);
                    reminderTimeBtn.setVisibility(View.VISIBLE);
                } else {
                    reminderTime.setText("");
                    // Hide the text views
                    reminderTime.setVisibility(View.GONE);
                    reminderTimeBtn.setVisibility(View.GONE);
                }
            }
        });








        
        date = findViewById(R.id.AppointmentDateButton);
     

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogDate();

            }
        });


        time = findViewById(R.id.AppointmentTimeButton);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              openDialogTime();

            }
        });

        // New ---

        reminderTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    openDialogTimeReminder();

            }
        });
        // Get the Intent and the Bundle from it
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if(bundle != null){boolean isUpdate = bundle.getBoolean("isUpdate");



        //String appointmentstat = intent.getStringExtra("Stat");
       if (isUpdate){
           setOldEvent(bundle);
       }
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle != null){updateEvent(bundle);}

                String status = "pending";
                String start_datetime = getDateTime(appointmentTime,appointmentDate);
                String reminder_datetime = getDateTime(reminderTime, appointmentDate);
                String agent_name = agentName.getText().toString();
                String agent_category =  agentCategory.getText().toString();
                String agent_phone =  agentPhone.getText().toString();
                String dependency_name = dependencyName.getText().toString();
                String dependency_category =  dependencyCategory.getText().toString();

                // Check if all these strings are not empty
                if (start_datetime != null && !start_datetime.isEmpty() &&

                         !agent_name.isEmpty() && !agent_category.isEmpty() &&
                         !agent_phone.isEmpty() &&
                         !dependency_name.isEmpty() && !dependency_category.isEmpty()) {

                    // Create a new Appointment object and pass the values from the views
                    Appointment appointment = new Appointment(start_datetime, reminder_datetime,status


                    );

                    // Create a new Agent object and pass the values from the views
                    Agent agent = new Agent(
                            agent_name,
                            agent_category,
                            agent_phone
                    );

                    // Create a new Dependency object and pass the values from the views
                    Dependency dependency = new Dependency(
                            dependency_name,
                            dependency_category
                    );

                    // Create an instance of the AppointmentDAO class
                    AppointmentDAO appointmentDAO = new AppointmentDAO(NewEventActivity.this);

                    // Create an instance of the AgentDAO class
                    AgentDAO agentDAO = new AgentDAO(NewEventActivity.this);

                    // Create an instance of the DependencyDAO class
                    DependencyDAO dependencyDAO = new DependencyDAO(NewEventActivity.this);

                    // Call the insertAppointment method and pass the objects
                    int id = appointmentDAO.insertAppointment(appointment, agent, dependency, agentDAO, dependencyDAO);

                    // Check if the insertion was successful
                    if (id > 0) {
                        if (reminder_datetime != null  && !reminder_datetime.isEmpty() ){

                            // Parse the string reminder_datetime into a Date object
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                           Date date;
                            try {
                                date = sdf.parse(reminder_datetime);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            // Get the number of milliseconds
                            long dateInMillis = date.getTime();
                            // Pass the dateInMillis as a long parameter to the setAlarm() method
                            setAlarm(dateInMillis, agent_phone);
                            Intent intent = new Intent(NewEventActivity.this, MainActivity.class);
                            // Start the new activity
                            startActivity(intent);

                        }
                        Intent intent = new Intent(NewEventActivity.this, MainActivity.class);
                        // Start the new activity
                        startActivity(intent);
                        // Show a toast message
                        Toast.makeText(NewEventActivity.this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show a toast message
                        Toast.makeText(NewEventActivity.this, "Appointment insertion failed", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    Toast.makeText(NewEventActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void setOldEvent(Bundle bundle){
        // Get the values from the Bundle

        String oldStart_datetime = bundle.getString("oldStart_datetime");
        String oldReminder_datetime = bundle.getString("oldReminder_datetime");
        String oldAgent_name = bundle.getString("oldAgent_name");
        String oldAgent_category = bundle.getString("oldAgent_category");
        String oldAgent_phone = bundle.getString("oldAgent_phone");
        String oldDependency_name = bundle.getString("oldDependency_name");
        String oldDependency_category = bundle.getString("oldDependency_category");




        appointmentDate.setText(oldStart_datetime.substring(0, 10));
        appointmentTime.setText(oldStart_datetime.substring(11, 16));

        agentName.setText(oldAgent_name);
        agentCategory.setText(oldAgent_category);
        agentPhone.setText(oldAgent_phone);
        dependencyName.setText(oldDependency_name);
        dependencyCategory.setText(oldDependency_category);
        if(oldReminder_datetime != null){

        reminderTime.setText(oldReminder_datetime.substring(11, 16));
        reminderTimeSwitch.setChecked(true);
    }



    }
    private void updateEvent(Bundle bundle){


        // Get the values from the Bundle
        int oldAppointment_id = bundle.getInt("oldAppointment_id");




        String status = "pending";
        String start_datetime = getDateTime(appointmentTime,appointmentDate);
        String reminder_datetime = getDateTime(reminderTime, appointmentDate);
        String agent_name = agentName.getText().toString();
        String agent_category =  agentCategory.getText().toString();
        String agent_phone =  agentPhone.getText().toString();
        String dependency_name = dependencyName.getText().toString();
        String dependency_category =  dependencyCategory.getText().toString();

        // Check if all these strings are not empty
        if (start_datetime != null && !start_datetime.isEmpty() &&

                !agent_name.isEmpty() && !agent_category.isEmpty() &&
                !agent_phone.isEmpty() &&
                !dependency_name.isEmpty() && !dependency_category.isEmpty()) {

            // Create a new Appointment object and pass the values from the views
            Appointment appointment = new Appointment(start_datetime, reminder_datetime,status


            );

            // Create a new Agent object and pass the values from the views
            Agent agent = new Agent(
                    agent_name,
                    agent_category,
                    agent_phone
            );

            // Create a new Dependency object and pass the values from the views
            Dependency dependency = new Dependency(
                    dependency_name,
                    dependency_category
            );

            // Create an instance of the AppointmentDAO class
            AppointmentDAO appointmentDAO = new AppointmentDAO(NewEventActivity.this);

            // Create an instance of the AgentDAO class
            AgentDAO agentDAO = new AgentDAO(NewEventActivity.this);

            // Create an instance of the DependencyDAO class
            DependencyDAO dependencyDAO = new DependencyDAO(NewEventActivity.this);

            // Call the insertAppointment method and pass the objects
            int id = appointmentDAO.insertAppointment(appointment, agent, dependency, agentDAO, dependencyDAO);

            // Check if the insertion was successful
            if (id > 0) {
                if (reminder_datetime != null  && !reminder_datetime.isEmpty() ){

                    // Parse the string reminder_datetime into a Date object
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date;
                    try {
                        date = sdf.parse(reminder_datetime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    // Get the number of milliseconds
                    long dateInMillis = date.getTime();
                    // Pass the dateInMillis as a long parameter to the setAlarm() method
                    setAlarm(dateInMillis, agent_phone);
                    int rows = appointmentDAO.updateAppointmentStatus(oldAppointment_id, "delayed");
                    Intent intent = new Intent(NewEventActivity.this, MainActivity.class);
                    // Start the new activity
                    startActivity(intent);
                    // Show a toast message
                    Toast.makeText(NewEventActivity.this, "Appointment updated successfully", Toast.LENGTH_SHORT).show();
                }
                int rows = appointmentDAO.updateAppointmentStatus(oldAppointment_id, "delayed");
                Intent intent = new Intent(NewEventActivity.this, MainActivity.class);
                // Start the new activity
                startActivity(intent);
                // Show a toast message
                Toast.makeText(NewEventActivity.this, "Appointment updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Show a toast message
                Toast.makeText(NewEventActivity.this, "Appointment insertion failed", Toast.LENGTH_SHORT).show();
            }



        } else {
            Toast.makeText(NewEventActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

    }

    // New ---
    private void openDialogDate(){

        // Get the current date from the Calendar class
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialogDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                appointmentDate.setText(String.format("%02d/%02d/%04d", day, month + 1, year));
            }
        }, year, month, day);

        dialogDate.show();
    }

    private void openDialogTime(){

        TimePickerDialog dialogTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {


                appointmentTime.setText(String.format("%02d:%02d", hours, minutes));

            }
        }, 10, 00, true);
        dialogTime.show();
    }

    private void openDialogTimeReminder(){


        TimePickerDialog dialogReminder = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {


                reminderTime.setText(String.format("%02d:%02d", hours, minutes));

            }
        }, 10, 00, true);
        dialogReminder.show();
    }



    // A method that converts the text views to a datetime string
    public String getDateTime( TextView in_time, TextView in_date) {
        // Get the time as a string
        String time = in_time.getText().toString();

        // Get the date as a string
        String date = in_date.getText().toString();

        // Create a simple date format object for the input date format
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Create a simple date format object for the input time format
        SimpleDateFormat inputTimeFormat = new SimpleDateFormat("hh:mm");

        // Create a simple date format object for the output datetime format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat("HH:mm:ss");


        try {
            // Parse the date string to a date object
            Date dateObject = inputDateFormat.parse(date);

            // Parse the time string to a date object
            Date timeObject = inputTimeFormat.parse(time);
           if (dateObject != null && timeObject != null){
            // Format the date and time objects to a datetime string
            String datetime = outputDateFormat.format(dateObject) + " " + outputTimeFormat.format(timeObject);

            // Return the datetime string
            return datetime;
           }
        } catch (ParseException e) {
            // Handle the exception
            e.printStackTrace();

        }
        return null;
    }
    @RequiresPermission(value = "android.permission.SCHEDULE_EXACT_ALARM", conditional = true)
public void setAlarm(Long ReminderDateTime, String PhoneNumber) {

        String time = appointmentTime.getText().toString();


        // Check if the app has the permission to schedule exact alarms
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (manager.canScheduleExactAlarms()) {
                // If the permission is granted, proceed with setting the alarm
                Intent intent = new Intent(this,
                        Receiver.class);

                // Add the person's phone number and the event time to the intent as an extra
                intent.putExtra("phone_number", PhoneNumber);
                intent.putExtra("event_time", time);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
                // Use the setExact() method of the AlarmManager
                manager.setExact(AlarmManager.RTC_WAKEUP, ReminderDateTime, pendingIntent);
            } else {
                // If the permission is not granted, request it from the user
                // Explain why the app needs to schedule exact alarms
                Toast.makeText(this, "This app needs to schedule exact alarms for your events. Please grant the permission in the next screen.", Toast.LENGTH_LONG).show();
                // Invoke an intent with the ACTION_REQUEST_SCHEDULE_EXACT_ALARM action
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                // Set the package name of the app
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                // Start the intent
                startActivity(intent);
            }
        } else {

            Intent intent = new Intent(this, Receiver.class);

            if (!PhoneNumber.isEmpty() && !time.isEmpty()){
                // Add the person's phone number and the event time to the intent as an extra
                intent.putExtra("phone_number", PhoneNumber);
            intent.putExtra("event_time", time);
                }
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

            manager.setExact(AlarmManager.RTC_WAKEUP, ReminderDateTime, pendingIntent);


        }

        }


    }


