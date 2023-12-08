package com.example.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo.databinding.ActivityNewTaskBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class NewTaskActivity extends AppCompatActivity {
    // New ---
    private TextView textDate;
    private ImageButton date;
    private TextView textTime;
    private ImageButton time;

    private Switch switchReminder;
    private TextView textReminder;


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

        // New ---
        textDate = findViewById(R.id.AppointmentDate);
        date = findViewById(R.id.AppointmentDateButton);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogDate();

            }
        });

        textTime = findViewById(R.id.AppointmentTime);
        time = findViewById(R.id.AppointmentTimeButton);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              openDialogTime();

            }
        });

        // New ---
        switchReminder = findViewById(R.id.switch1);
        switchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchReminder.isChecked()){

                    openDialogTimeReminder();

                }
            }
        });


    }

    // New ---
    private void openDialogDate(){
        DatePickerDialog dialogDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                textDate.setText(String.valueOf(year)+" , "+String.valueOf(month+1)+" , "+String.valueOf(day));
            }
        }, 2023, 0, 10);

        dialogDate.show();
    }

    private void openDialogTime(){

        TimePickerDialog dialogTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                textTime.setText(String.valueOf(hours)+":"+String.valueOf(minutes));

            }
        }, 10, 00, true);
      dialogTime.show();
    }

    private void openDialogTimeReminder(){

        TimePickerDialog dialogReminder = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                textReminder.setText(String.valueOf(hours)+":"+String.valueOf(minutes));

            }
        }, 10, 00, true);
        dialogReminder.show();
    }
}

