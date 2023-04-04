package com.example.prjfarmfreshv1.ui.aboutUs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Farm Fresh is an urban agricultural company located in the  neighborhood of Montreal, Quebec. The company states its mission on its website is to grow food where people live and grow it more sustainably We're out to create a better food system. We want to reconnect people with where their food comes from by growing veggies right here in the city on rooftops, partnering up with hundreds of farmers and food makers, and providing it all to you through our online farmer’s market.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}