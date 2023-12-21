package com.example.todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.todo.R;
import com.example.todo.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SettingsActivity.ThemeUtility.applyTheme(this);  // Apply the theme here
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.appBarMain.toolbar);
        setContentView(binding.getRoot());


        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create an intent to start the new activity
                Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                // Start the new activity
                startActivity(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_myday, R.id.nav_delayed, R.id.nav_tasks, R.id.nav_due)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {// User chooses the "Settings" item. Show the app settings UI.
            // Create an intent to start the new activity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            // Start the new activity
            startActivity(intent);
            return true;
        }// The user's action isn't recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
}