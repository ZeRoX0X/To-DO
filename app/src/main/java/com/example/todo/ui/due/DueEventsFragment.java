package com.example.todo.ui.due;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo.databinding.FragmentDueEventsBinding;
import com.example.todo.R;
import com.example.todo.data.DBHelper;
import com.example.todo.data.dao.AppointmentDAO;
import com.example.todo.data.models.Appointment;

import com.example.todo.ui.EventAdapter;


import java.util.ArrayList;
import java.util.List;

public class DueEventsFragment extends Fragment {

    private FragmentDueEventsBinding binding;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private DueEventsViewModel dueEventsViewModel;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create an instance of the viewmodel using the ViewModelProvider class
        // Pass the fragment as the scope and a factory that provides the AppointmentDAO as a parameter
        dueEventsViewModel = new ViewModelProvider(this, new DueEventsFragment.DueEventsViewModelFactory(new AppointmentDAO(getActivity()))).get(DueEventsViewModel.class);

        binding = FragmentDueEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Find the RecyclerView by its id
        recyclerView = root.findViewById(R.id.recycler_view);

        // Set the layout manager to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));

        // Create a new list of appointments or get it from somewhere else
        List<Appointment> newappointments = new ArrayList<>();
        // Create a new adapter object and set it to the RecyclerView
        adapter = new EventAdapter(getActivity(), newappointments);
        recyclerView.setAdapter(adapter);
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();


        // Observe the list of appointments in the viewmodel using the observe method
        // Pass the fragment as the lifecycle owner and a lambda expression as the observer
        dueEventsViewModel.getDueEvents(db).observe(getViewLifecycleOwner(), appointments -> {
            // Check if the list is not null and not empty
            if (appointments != null && !appointments.isEmpty()) {
                // Pass the list to the adapter
                adapter.setAppointments(appointments);
            } else {
                // Show a toast message that there are no appointments
                Toast.makeText(getActivity(), "No Due Events", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        recyclerView = null;
        adapter = null;
        dueEventsViewModel = null;
        db.close();
        dbHelper.close();
    }

    public static class DueEventsViewModelFactory implements ViewModelProvider.Factory {
        // A field to store the AppointmentDAO
        private AppointmentDAO appointmentDAO;

        // A constructor that takes an AppointmentDAO as a parameter and assigns it to the field
        public DueEventsViewModelFactory(AppointmentDAO appointmentDAO) {
            this.appointmentDAO = appointmentDAO;
        }

        // Override the create method to return an instance of dueEventsViewModel with the AppointmentDAO
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DueEventsViewModel(appointmentDAO);
        }
    }
}
