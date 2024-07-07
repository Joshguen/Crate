package com.example.crate.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaterialsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MaterialsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is materials fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}