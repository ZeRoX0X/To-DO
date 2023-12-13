package com.example.todo.ui.delayed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo.databinding.FragmentDelayedTasksBinding;

public class DelayedTasksFragment extends Fragment {

    private FragmentDelayedTasksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DelayedTasksViewModel delayedTasksViewModel =
                new ViewModelProvider(this).get(DelayedTasksViewModel.class);

        binding = FragmentDelayedTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReminders;
        delayedTasksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}