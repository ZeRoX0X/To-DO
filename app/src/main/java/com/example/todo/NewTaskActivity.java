package com.example.todo;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo.databinding.ActivityNewTaskBinding;

public class NewTaskActivity extends AppCompatActivity {

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
    }
}

