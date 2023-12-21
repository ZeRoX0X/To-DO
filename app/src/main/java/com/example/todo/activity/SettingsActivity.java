package com.example.todo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.todo.R;


public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).registerOnSharedPreferenceChangeListener(this);
        ThemeUtility.applyTheme(this);  // Apply the theme here
        // sets up the settings screen
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // when the specified preferences ("color_option" or "attachment") change,
    // the current activity is recreated to apply the changes to the UI.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        if (key.equals("color_option")) {
            this.recreate();
        }
        if (key.equals("attachment")) {
            this.recreate();
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public static class ThemeUtility {

        // Apply the selected theme based on shared preferences
        public static void applyTheme(AppCompatActivity activity) {

            // Get shared preferences for color option
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

            // Get the selected color option, default to "DEFAULT_VALUE" if not found
            String colorOption = sharedPreferences.getString("color_option", "DEFAULT_VALUE");



            // Get the boolean value indicating whether the sound switch is checked, default to true if not found
            boolean isSwitchChecked = sharedPreferences.getBoolean("attachment", true);

            // Apply theme based on color option and sound switch status
            if (isSwitchChecked) {
                // Apply themes with sound
                if (colorOption.equals("BLUE")) {
                    activity.setTheme(R.style.BlueTheme);
                } else if (colorOption.equals("RED")) {
                    activity.setTheme(R.style.RedTheme);
                } else {
                    activity.setTheme(R.style.GreenTheme);
                }
            } else {
                // Apply themes without sound
                if (colorOption.equals("BLUE")) {
                    activity.setTheme(R.style.BlueThemeNoSound);
                } else if (colorOption.equals("RED")) {
                    activity.setTheme(R.style.RedThemeNoSound);
                } else {
                    activity.setTheme(R.style.GreenThemeNoSound);
                }
            }
        }
    }

}