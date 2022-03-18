package com.ebookfrenzy.viewmodeldemo.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private static final Float rate = 0.74F;
    private String dollarText = "";

    // change result to be mutable live data
    private MutableLiveData<Float> result = new MutableLiveData<>();


    // use setValue rather than assignment operator for result
    public void setAmount(String value) {
        this.dollarText = value;
        result.setValue(Float.parseFloat(dollarText)*rate);
    }

    // now returns MutableLiveData<Float> rather than returning Float
    public MutableLiveData<Float> getResult() {
        return result;
    }
}