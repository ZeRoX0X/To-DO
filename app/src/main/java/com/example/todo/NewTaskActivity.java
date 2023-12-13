package com.example.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.data.dao.AgentDAO;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.dao.DependencyDAO;
import com.example.todo.data.models.Agent;
import com.example.todo.data.models.Appointment;
import com.example.todo.data.models.Dependency;
import com.example.todo.databinding.ActivityNewTaskBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {
    // New ---
    private EditText appointmentDate;
    private ImageButton date;
    private EditText appointmentTime;
    private ImageButton time;

    private Switch reminderSwitch;
    private TextView reminderTime;


    private ActivityNewTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using the binding class
        binding = ActivityNewTaskBinding.inflate(getLayoutInflater());
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

         
        EditText agentName = findViewById(R.id.agent_name);
             
        EditText agentCategory = findViewById(R.id.agent_category);
 
        EditText agentPhone = findViewById(R.id.agent_phone);
 
        EditText dependencyName = findViewById(R.id.dependency_name);
 
        EditText dependencyCategory = findViewById(R.id.dependency_category);
 
         appointmentDate = findViewById(R.id.AppointmentDate);
 
         appointmentTime = findViewById(R.id.AppointmentTime);

      reminderSwitch = findViewById(R.id.reminder_switch);

         reminderTime = findViewById(R.id.reminder_time);

        Button saveButton = findViewById(R.id.newAppointmentButton);


        // New ---
        
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

        reminderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reminderSwitch.isChecked()){

                    openDialogTimeReminder();

                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        reminder_datetime != null && !reminder_datetime.isEmpty() &&
                        agent_name != null && !agent_name.isEmpty() &&
                        agent_category != null && !agent_category.isEmpty() &&
                        agent_phone != null && !agent_phone.isEmpty() &&
                        dependency_name != null && !dependency_name.isEmpty() &&
                        dependency_category != null && !dependency_category.isEmpty()) {
                    // Do something if all these strings are not empty
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
                    AppointmentDAO appointmentDAO = new AppointmentDAO(NewTaskActivity.this);

                    // Create an instance of the AgentDAO class
                    AgentDAO agentDAO = new AgentDAO(NewTaskActivity.this);

                    // Create an instance of the DependencyDAO class
                    DependencyDAO dependencyDAO = new DependencyDAO(NewTaskActivity.this);

                    // Call the insertAppointment method and pass the objects
                    int id = appointmentDAO.insertAppointment(appointment, agent, dependency, agentDAO, dependencyDAO);

                    // Check if the insertion was successful
                    if (id > 0) {
                        // Show a toast message
                        Toast.makeText(NewTaskActivity.this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show a toast message
                        Toast.makeText(NewTaskActivity.this, "Appointment insertion failed", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    Toast.makeText(NewTaskActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    // New ---
    private void openDialogDate(){
        DatePickerDialog dialogDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                appointmentDate.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
            }
        }, 2023, 0, 10);

        dialogDate.show();
    }

    private void openDialogTime(){

        TimePickerDialog dialogTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                appointmentTime.setText(String.valueOf(hours)+":"+String.valueOf(minutes));

            }
        }, 10, 00, true);
      dialogTime.show();
    }

    private void openDialogTimeReminder(){


        TimePickerDialog dialogReminder = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                reminderTime.setText(String.valueOf(hours)+":"+String.valueOf(minutes));

            }
        }, 10, 00, true);
        dialogReminder.show();
    }


    // A method that converts the text view and the edit text to a datetime string
    public String getDateTime( TextView in_time, EditText in_date) {
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

            // Format the date and time objects to a datetime string

            String datetime = outputDateFormat.format(dateObject) + " " + outputTimeFormat.format(timeObject);

            // Return the datetime string
            return datetime;
        } catch (ParseException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }



}

