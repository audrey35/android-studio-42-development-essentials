package com.ebookfrenzy.myapplication.conversion;

import androidx.lifecycle.ViewModel;

public class ConversionViewModel extends ViewModel {

    private static final Float rate = 0.74F; // conversion rate
    private String dollarText = ""; // stores current dollar string value
    private Float result = 0F; // stores converted amount

    // setter for getting dollarText view's current value,
    // which gets stored in local var also called dollarText,
    // which is used to calculate converted value
    // this gets stored in local var called result
    public void setAmount(String value) {
        this.dollarText = value;
        this.result = Float.parseFloat(dollarText)*rate;
    }

    // getter for returning the converted value
    public Float getResult()
    {
        return result;
    }
}
