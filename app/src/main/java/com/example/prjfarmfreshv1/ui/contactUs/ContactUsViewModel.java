package com.example.prjfarmfreshv1.ui.contactUs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactUsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ContactUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("123,Commercial Road, Montreal,L2G6L0");
    }

    public LiveData<String> getText() {
        return mText;
    }
}