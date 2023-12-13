package com.example.todo.ui.delayed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DelayedTasksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DelayedTasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is delayed fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}