package com.example.todo.ui.tasks;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.data.DBHelper;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.models.Appointment;

import com.example.todo.databinding.FragmentTasksBinding;
import com.example.todo.ui.EventAdapter;


import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {
    private FragmentTasksBinding binding;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private EventAdapter completedAdapter;
    private EventAdapter cancelledAdapter;
    private EventsViewModel eventsViewModel;
    private DBHelper dbHelper;
    private  SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create an instance of the viewmodel using the ViewModelProvider class
        // Pass the fragment as the scope and a factory that provides the AppointmentDAO as a parameter
        eventsViewModel = new ViewModelProvider(this, new tasksViewModelFactory(new AppointmentDAO(getActivity()))).get(EventsViewModel.class);

        binding = FragmentTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Find the RecyclerView by its id
        recyclerView1 = root.findViewById(R.id.recycler_view1);

        // Set the layout manager to the RecyclerView
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        // Find the RecyclerView by its id
        recyclerView2 = root.findViewById(R.id.recycler_view2);

        // Set the layout manager to the RecyclerView
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        dbHelper = new DBHelper(getActivity());
         db = dbHelper.getReadableDatabase();


        // Create a new list of appointments or get it from somewhere else
        List<Appointment> completedappointments = new ArrayList<>();
        // Create a new adapter object and set it to the RecyclerView
        completedAdapter = new EventAdapter(getActivity(), completedappointments);
        recyclerView1.setAdapter(completedAdapter);
        List<Appointment> canceledappointments = new ArrayList<>();

        cancelledAdapter = new EventAdapter(getActivity(), canceledappointments);
        recyclerView2.setAdapter(cancelledAdapter);

        TextView comletedEventsTextView = root.findViewById(R.id.completed_count);
        TextView canceledEventsTextView = root.findViewById(R.id.canceled_count);

        // Get the ImageView from the layout
        ImageView imageView = root.findViewById(R.id.status_emoji);

        // Get the drawable object from the resource ID
        Drawable drawableLike = ContextCompat.getDrawable(getContext (), R.drawable.like_emoji);
        Drawable drawableDislike = ContextCompat.getDrawable(getContext (), R.drawable.dislike_emoji);

        // Observe the list of appointments in the viewmodel using the observe method
        // Pass the fragment as the lifecycle owner and a lambda expression as the observer
        eventsViewModel.getCompletedAppointments(db).observe(getViewLifecycleOwner(), appointments1 -> {
            // Check if the list is not null and not empty
            if (appointments1 != null && !appointments1.isEmpty()) {
                // Pass the list to the adapter
                completedAdapter.setAppointments(appointments1);
                int completedEvents = completedAdapter.getItemCount();
                comletedEventsTextView.setText(String.valueOf(completedEvents));



            } else {
                // Show a toast message that there are no appointments
                Toast.makeText(getActivity(), "No completed events", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe the list of appointments in the viewmodel using the observe method
        // Pass the fragment as the lifecycle owner and a lambda expression as the observer
        eventsViewModel.getCancelledAppointments(db).observe(getViewLifecycleOwner(), appointments2 -> {
            // Check if the list is not null and not empty
            if (appointments2 != null && !appointments2.isEmpty()) {
                // Pass the list to the adapter
                cancelledAdapter.setAppointments(appointments2);
                // Get the item count from the adapter
                int canceledEvents = cancelledAdapter.getItemCount();
                canceledEventsTextView.setText(String.valueOf(canceledEvents));
                // Get the completed events count from the completedEventsTextView
                // Get the text content of the comletedEventsTextView as a String
                String input = comletedEventsTextView.getText().toString();

               // Declare an int variable to store the parsed value
                int completedEvents;

                // Check if the input is null or empty
                if (input == null || input.isEmpty()) {
                    // Handle the null or empty case

                    completedEvents = 0; // Set a default value
                } else {
                    // Try to parse the input as an int
                    try {
                        completedEvents = Integer.parseInt(input);
                        // The parsing was successful
                        // You can use the completedEvents variable as an int
                    } catch (NumberFormatException e) {
                        // The parsing failed
                        // Handle the exception

                        completedEvents = 0; // Set a default value
                    }
                }

                if (completedEvents > canceledEvents) {
                    // Show a happy emoji
                    //Set the drawable object as the content of the ImageView
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageDrawable(drawableLike);

                } else if (completedEvents < canceledEvents) {
                    // Show a sad emoji
                    imageView.setVisibility(View.VISIBLE);

                    imageView.setImageDrawable(drawableDislike);
                }else{

                }




            } else {
                // Show a toast message that there are no appointments
                Toast.makeText(getActivity(), "No cancelled events", Toast.LENGTH_SHORT).show();
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        recyclerView1 = null;
        recyclerView2 = null;
        completedAdapter = null;
        cancelledAdapter = null;
        eventsViewModel = null;
        db.close();
        dbHelper.close();

    }

    public static class tasksViewModelFactory implements ViewModelProvider.Factory {
        // A field to store the AppointmentDAO
        private AppointmentDAO appointmentDAO;

        // A constructor that takes an AppointmentDAO as a parameter and assigns it to the field
        public tasksViewModelFactory(AppointmentDAO appointmentDAO) {
            this.appointmentDAO = appointmentDAO;
        }

        // Override the create method to return an instance of eventsViewModel with the AppointmentDAO
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new EventsViewModel(appointmentDAO);
        }
    }
}