package com.example.todo.ui;

// Import the required libraries
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.data.models.Appointment;

import java.util.List;
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

    // Override the onCreateViewHolder method to inflate the item layout and return a new ViewHolder object
    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get a layout inflater from the context
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the item layout using the layout inflater
        View itemView = inflater.inflate(R.layout.appointment_item, parent, false);

        // Return a new ViewHolder object with the item view
        return new AppointmentViewHolder(itemView);
    }

    // Override the onBindViewHolder method to bind the data to the views in the ViewHolder object
    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        // Get the appointment object at the given position
        Appointment appointment = appointments.get(position);

        // Set the text of the views in the ViewHolder object with the data from the appointment object
        holder.textViewId.setText(String.valueOf(appointment.getId()));
        holder.textViewDatetime.setText(appointment.getStart_datetime());
        holder.textViewReminder.setText(appointment.getReminder_datetime());
        holder.textViewStat.setText(appointment.getStat());
        holder.textViewAgentName.setText(appointment.getAgent_name());
        holder.textViewAgentCategory.setText(appointment.getAgent_category());
        holder.textViewAgentPhone.setText(appointment.getAgent_phone());
        holder.textViewDependencyName.setText(appointment.getDependency_name());
        holder.textViewDependencyCategory.setText(appointment.getDependency_category());
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
        TextView textViewId;
        TextView textViewDatetime;
        TextView textViewReminder;
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
            textViewId = itemView.findViewById(R.id.text_view_id);
            textViewDatetime = itemView.findViewById(R.id.text_view_datetime);
            textViewReminder = itemView.findViewById(R.id.text_view_reminder);
            textViewStat = itemView.findViewById(R.id.text_view_stat);
            textViewAgentName = itemView.findViewById(R.id.text_view_agent_name);
            textViewAgentCategory = itemView.findViewById(R.id.text_view_agent_category);
            textViewAgentPhone = itemView.findViewById(R.id.text_view_agent_phone);
            textViewDependencyName = itemView.findViewById(R.id.text_view_dependency_name);
            textViewDependencyCategory = itemView.findViewById(R.id.text_view_dependency_category);
        }
    }
}

