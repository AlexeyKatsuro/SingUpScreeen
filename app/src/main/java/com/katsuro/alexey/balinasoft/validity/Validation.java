package com.katsuro.alexey.balinasoft.validity;

import android.content.Context;

/**
 * Created by alexey on 6/30/18.
 */

public abstract class Validation {

    private String mErrorMessage;


    public abstract boolean validate(String textForValidation);

    protected void setErrorMessage(String errorMessage){
        mErrorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return mErrorMessage;
    }

    public abstract boolean isValid();
}
