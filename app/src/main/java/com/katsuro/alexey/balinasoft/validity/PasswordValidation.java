package com.katsuro.alexey.balinasoft.validity;

import android.content.Context;
import android.view.View;

import com.katsuro.alexey.balinasoft.R;

/**
 * Created by alexey on 7/1/18.
 */

public class PasswordValidation extends Validation {

    //For access to resources
    private Context mContext;
    boolean isValid;
    private static final int mMinPasswordLength = 6;
    private static final int mMaxPasswordLength = 30;

    public PasswordValidation(Context context) {
        mContext = context;
    }

    @Override
    public boolean validate(String password) {

        if(password == null || password.equals("")){
            isValid = false;
            setErrorMessage(mContext.getResources().getString(R.string.password_enter_requirement));
            return isValid;
        }else if(password.length() > mMaxPasswordLength || password.length() <mMinPasswordLength){
            isValid = false;
            setErrorMessage(mContext.getResources().getString(R.string.password_length_requirement,
                    mMinPasswordLength,mMaxPasswordLength));
        } else {
            isValid=true;
        }


        return isValid;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }
}
