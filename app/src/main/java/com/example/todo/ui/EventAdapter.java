package com.example.todo.ui;

// Import the required libraries
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.NewEventActivity;
import com.example.todo.R;
import com.example.todo.data.dao.AgentDAO;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.dao.DependencyDAO;
import com.example.todo.data.models.Appointment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Create a class that extends RecyclerView.Adapter and uses a custom ViewHolder class
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.AppointmentViewHolder> {
    // Declare a private field to store the list of appointments
    private List<Appointment> appointments;
    // Declare a private field to store the context
    private Context context;
    // Create a constructor that takes a context and a list of appointments as parameters and assigns them to the fields
    public EventAdapter(Context context, List<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
        Collections.sort(this.appointments, new Comparator<Appointment>() {
            public int compare(Appointment a1, Appointment a2) {
                // Assuming your date and time fields are of type String
                // You can use SimpleDateFormat to parse them into Date objects
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d1 = sdf.parse(a1.getStart_datetime());
                    Date d2 = sdf.parse(a2.getStart_datetime());

                    if(d1 != null && d2!= null){
                    // Compare the dates using the compareTo() method
                    return d1.compareTo(d2);}
                } catch (ParseException e) {
                    // Handle the exception if the date format is invalid
                    e.printStackTrace();
                    return 0;
                }
                return 0;
            }
        });

    }
    // Define constants for the view types
    private static final int PENDING = 1;


    // Override the getItemViewType method to return the view type based on the appointment status
    @Override
    public int getItemViewType(int position) {
        // Get the appointment object at the given position
        Appointment appointment = appointments.get(position);

        // Check the status of the appointment and return the corresponding view type
           if (appointment.getStat().equals("pending")) {
            return PENDING;
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
            case PENDING:
                // Inflate the layout for done appointments
                itemView = inflater.inflate(R.layout.event_item_pending, parent, false);
            break;
            default:
                // Inflate the default layout
                itemView = inflater.inflate(R.layout.event_item, parent, false);
                break;
        }

        // Return a new ViewHolder object with the item view
        return new AppointmentViewHolder(itemView);
    }


    // Override the onBindViewHolder method to bind the data to the views in the ViewHolder object
    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        int completedEvents = getItemCount();

        int canceledEvents = getItemCount();
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
        String time = ldt.format(DateTimeFormatter.ofPattern("HH:mm"));


        // Use a switch statement to check the view type and bind the data accordingly
        switch (viewType) {
            case PENDING:


                holder.imageViewOptions.setOnClickListener(view -> showPopUpMenu(view, position));

                holder.textViewMonthName.setText(monthName);
                holder.textViewDayName.setText(dayName);
                holder.textViewDayNumber.setText(String.valueOf(dayNumber));
                holder.textViewTime.setText(time);
                holder.textViewStat.setText(appointment.getStat());
                holder.textViewAgentName.setText(appointment.getAgent_name());
                holder.textViewAgentCategory.setText(appointment.getAgent_category());

                holder.textViewDependencyName.setText(appointment.getDependency_name());
                holder.textViewDependencyCategory.setText(appointment.getDependency_category());



                break;

            default:


                holder.textViewMonthName.setText(monthName);
                holder.textViewDayName.setText(dayName);
                holder.textViewDayNumber.setText(String.valueOf(dayNumber));
                holder.textViewTime.setText(time);
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

    public void showPopUpMenu(View view, int position) {
        final Appointment appointment = appointments.get(position);
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuDelete) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete).
                        setPositiveButton(R.string.yes, (dialog, which) -> {
                            deleteEvent(position);
                        })
                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
            } else if (id == R.id.menuUpdate) {
                updateEvent(position);
            } else if (id == R.id.menuComplete) {
                AlertDialog.Builder completeAlertDialog = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                completeAlertDialog.setTitle(R.string.confirmation).setMessage(R.string.sureToMarkAsComplete).
                        setPositiveButton(R.string.yes, (dialog, which) -> showCompleteDialog(appointment.getId(), position))
                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();

            }else if (id == R.id.menuCancel) {
                AlertDialog.Builder cancelAlertDialog = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                cancelAlertDialog.setTitle(R.string.confirmation).setMessage(R.string.MarkAsCanceled).
                        setPositiveButton(R.string.yes, (dialog, which) -> showCancelDialog(appointment.getId(), position))
                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();

            }

            return false;
        });
        popupMenu.show();
    }
    public void showCompleteDialog(int eventId, int position) {
        Dialog dialog = new Dialog(context, R.style.Theme_ToDO);
        dialog.setContentView(R.layout.dialog_completed_theme);
        Button close = dialog.findViewById(R.id.closeButton);
        close.setOnClickListener(view -> {
            completeEvent(eventId, position);
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
    public void showCancelDialog(int eventId, int position) {
        Dialog dialog = new Dialog(context, R.style.Theme_ToDO);
        dialog.setContentView(R.layout.dialog_canceled_theme);
        Button close = dialog.findViewById(R.id.closeButton);
        close.setOnClickListener(view -> {
            cancelEvent(eventId, position);
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
    private void deleteEvent( int position) {
        // Get the appointment object at the given position
        Appointment appointment = appointments.get(position);
        int appointment_id = appointment.getId();
        int agent_id = appointment.getAgent_id();
        int dependency_id = appointment.getDependency_id();

        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        DependencyDAO dependencyDAO = new DependencyDAO(context);
        AgentDAO agentDAO = new AgentDAO(context);
        boolean result = appointmentDAO.deleteAppointment(appointment_id, agent_id, dependency_id, dependencyDAO, agentDAO);
        if (result) {
            // Show a success message
            Toast.makeText(context, "Appointment deleted successfully", Toast.LENGTH_SHORT).show();
            // Remove the appointment from the list
            appointments.remove(position);
            // Notify the adapter that the data has changed
            notifyDataSetChanged();
        } else {
            // Show an error message
            Toast.makeText(context, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
        }


    }


    private void completeEvent(int eventId, int position){
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        int rows = appointmentDAO.updateAppointmentStatus(eventId, "completed");
        appointments.remove(position);
        // Notify the adapter that the data has changed
        notifyDataSetChanged();
        
    }
    private void cancelEvent(int eventId, int position){
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        int rows = appointmentDAO.updateAppointmentStatus(eventId, "canceled");
        appointments.remove(position);
        // Notify the adapter that the data has changed
        notifyDataSetChanged();

    }

    public void updateEvent(int position){
        Appointment appointment = appointments.get(position);
        int oldAppointment_id = appointment.getId();
        int oldDependency_id = appointment.getDependency_id();
        int oldAgent_id = appointment.getAgent_id();
        String oldStart_datetime = appointment.getStart_datetime();
        String oldReminder_datetime = appointment.getReminder_datetime();
        String oldAgent_name = appointment.getAgent_name();
        String oldAgent_category = appointment.getAgent_category();
        String oldAgent_phone = appointment.getAgent_phone();
        String oldDependency_name = appointment.getDependency_name();
        String oldDependency_category = appointment.getDependency_category();
        boolean isUpdate = true;

        // Create a new Bundle
        Bundle bundle = new Bundle();

        // Put the values in the Bundle
        bundle.putInt("oldAppointment_id", oldAppointment_id);
        bundle.putInt("oldDependency_id", oldDependency_id);
        bundle.putInt("oldAgent_id", oldAgent_id);
        bundle.putString("oldStart_datetime", oldStart_datetime);
        bundle.putString("oldReminder_datetime", oldReminder_datetime);
        bundle.putString("oldAgent_name", oldAgent_name);
        bundle.putString("oldAgent_category", oldAgent_category);
        bundle.putString("oldAgent_phone", oldAgent_phone);
        bundle.putString("oldDependency_name", oldDependency_name);
        bundle.putString("oldDependency_category", oldDependency_category);
        bundle.putBoolean("isUpdate", isUpdate);


        Intent intent = new Intent(context, NewEventActivity.class);
        // Put the Bundle as an extra
        intent.putExtras(bundle);

        context.startActivity(intent);


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
        TextView textViewTime;
        ImageView imageViewOptions;
        // Create a constructor that takes a view as a parameter and assigns the views to the fields
        public AppointmentViewHolder(View itemView) {
            super(itemView);
            // Find the views by their ids
            textViewMonthName = itemView.findViewById(R.id.monthName);
            textViewDayName = itemView.findViewById(R.id.dayName);
            textViewDayNumber = itemView.findViewById(R.id.dayNumber);
            textViewTime = itemView.findViewById(R.id.time);
            textViewStat = itemView.findViewById(R.id.status);
            textViewAgentName = itemView.findViewById(R.id.agent_name);
            textViewAgentCategory = itemView.findViewById(R.id.agent_category);
            textViewAgentPhone = itemView.findViewById(R.id.agent_phone);
            textViewDependencyName = itemView.findViewById(R.id.dependency_name);
            textViewDependencyCategory = itemView.findViewById(R.id.dependency_category);
            imageViewOptions = itemView.findViewById(R.id.options);
        }
    }
}

