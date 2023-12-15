package com.example.todo.ui;

// Import the required libraries
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.data.models.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

// Create a class that extends RecyclerView.Adapter and uses a custom ViewHolder class
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    // Declare a private field to store the list of appointments
    private List<Appointment> appointments;
    // Declare a private field to store the context
    private Context context;
    // Create a constructor that takes a context and a list of appointments as parameters and assigns them to the fields
    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
    }
    // Define constants for the view types
    private static final int DONE = 1;
    private static final int CANCELLED = 2;
    private static final int DELAYED = 3;

    // Override the getItemViewType method to return the view type based on the appointment status
    @Override
    public int getItemViewType(int position) {
        // Get the appointment object at the given position
        Appointment appointment = appointments.get(position);

        // Check the status of the appointment and return the corresponding view type
        if (appointment.getStat().equals("completed")) {
            return DONE;
        } else if (appointment.getStat().equals("canceled")) {
            return CANCELLED;
        } else if (appointment.getStat().equals("delayed")) {
            return DELAYED;
        }

        else {
            // If the status is not done or cancelled, return a default view type
            return super.getItemViewType(position);
        }
    }


    // Override the onCreateViewHolder method to inflate the item layout and return a new ViewHolder object
    // Override the onCreateViewHolder method to inflate the item layout and return a new ViewHolder object
    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get a layout inflater from the context
        LayoutInflater inflater = LayoutInflater.from(context);

        // Declare a view variable to store the item view
        View itemView;

        // Use a switch statement to check the view type and inflate the corresponding layout
        switch (viewType) {
            case DONE:
                // Inflate the layout for done appointments
                itemView = inflater.inflate(R.layout.event_item_done, parent, false);
                break;
            case CANCELLED:
                // Inflate the layout for cancelled appointments
                itemView = inflater.inflate(R.layout.event_item_cancelled, parent, false);
                break;
            case DELAYED:
                // Inflate the layout for cancelled appointments
                itemView = inflater.inflate(R.layout.event_item_delayed, parent, false);
                break;
            default:
                // Inflate the default layout
                itemView = inflater.inflate(R.layout.event_item_pending, parent, false);
                break;
        }

        // Return a new ViewHolder object with the item view
        return new AppointmentViewHolder(itemView);
    }


    // Override the onBindViewHolder method to bind the data to the views in the ViewHolder object
    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        // Get the appointment object at the given position
        Appointment appointment = appointments.get(position);

        // Get the view type of the item
        int viewType = getItemViewType(position);
        // Set the text of the views for default appointments
        String start_datetime = appointment.getStart_datetime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(start_datetime, dtf);


        String monthName = ldt.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String dayName = ldt.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        int dayNumber = ldt.getDayOfMonth();


        // Use a switch statement to check the view type and bind the data accordingly
        switch (viewType) {
            case DONE:
                // Set the text of the views for done appointments


                holder.textViewMonthName.setText(monthName);
                holder.textViewDayName.setText(dayName);
                holder.textViewDayNumber.setText(String.valueOf(dayNumber));
                holder.textViewStat.setText(appointment.getStat());
                holder.textViewAgentName.setText(appointment.getAgent_name());
                holder.textViewAgentCategory.setText(appointment.getAgent_category());

                holder.textViewDependencyName.setText(appointment.getDependency_name());
                holder.textViewDependencyCategory.setText(appointment.getDependency_category());

                // Set the color of the views for done appointments


                holder.textViewAgentName.setTextColor(Color.GREEN);
                holder.textViewAgentCategory.setTextColor(Color.GREEN);

                holder.textViewDependencyName.setTextColor(Color.GREEN);
                holder.textViewDependencyCategory.setTextColor(Color.GREEN);

                // Set the icon of the views for done appointments
                //holder.imageViewStat.setImageResource(R.drawable.ic_done);
                break;
            case CANCELLED:
                // Set the text of the views for cancelled appointments



                holder.textViewMonthName.setText(monthName);
                holder.textViewDayName.setText(dayName);
                holder.textViewDayNumber.setText(String.valueOf(dayNumber));
                holder.textViewStat.setText(appointment.getStat());
                holder.textViewAgentName.setText(appointment.getAgent_name());
                holder.textViewAgentCategory.setText(appointment.getAgent_category());

                holder.textViewDependencyName.setText(appointment.getDependency_name());
                holder.textViewDependencyCategory.setText(appointment.getDependency_category());

                // Set the color of the views for cancelled appointments

                holder.textViewAgentName.setTextColor(Color.RED);
                holder.textViewAgentCategory.setTextColor(Color.RED);

                holder.textViewDependencyName.setTextColor(Color.RED);
                holder.textViewDependencyCategory.setTextColor(Color.RED);

                // Set the icon of the views for cancelled appointments
                //holder.imageViewStat.setImageResource(R.drawable.ic_cancelled);
                break;
            default:


                holder.textViewMonthName.setText(monthName);
                holder.textViewDayName.setText(dayName);
                holder.textViewDayNumber.setText(String.valueOf(dayNumber));
                holder.textViewStat.setText(appointment.getStat());
                holder.textViewAgentName.setText(appointment.getAgent_name());
                holder.textViewAgentCategory.setText(appointment.getAgent_category());

                holder.textViewDependencyName.setText(appointment.getDependency_name());
                holder.textViewDependencyCategory.setText(appointment.getDependency_category());
                break;
        }
    }

    // Override the getItemCount method to return the size of the list of appointments
    @Override
    public int getItemCount() {
        return appointments.size();
    }
    // Define a custom method to set the list of appointments
    public void setAppointments(List<Appointment> appointments) {
        // Assign the parameter to the field
        this.appointments = appointments;
        // Notify the RecyclerView that the data has changed
        notifyDataSetChanged();
    }

    // Create a nested class that extends RecyclerView.ViewHolder and holds the views for each item
    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        // Declare the views for each item
        TextView textViewMonthName;
        TextView textViewDayName;
        TextView textViewDayNumber;

        TextView textViewStat;
        TextView textViewAgentName;
        TextView textViewAgentCategory;
        TextView textViewAgentPhone;
        TextView textViewDependencyName;
        TextView textViewDependencyCategory;
        // Create a constructor that takes a view as a parameter and assigns the views to the fields
        public AppointmentViewHolder(View itemView) {
            super(itemView);
            // Find the views by their ids
            textViewMonthName = itemView.findViewById(R.id.monthName);
            textViewDayName = itemView.findViewById(R.id.dayName);
            textViewDayNumber = itemView.findViewById(R.id.dayNumber);
            textViewStat = itemView.findViewById(R.id.status);
            textViewAgentName = itemView.findViewById(R.id.agent_name);
            textViewAgentCategory = itemView.findViewById(R.id.agent_category);
            textViewAgentPhone = itemView.findViewById(R.id.agent_phone);
            textViewDependencyName = itemView.findViewById(R.id.dependency_name);
            textViewDependencyCategory = itemView.findViewById(R.id.dependency_category);
        }
    }
}

