package com.katsuro.alexey.balinasoft.validity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.katsuro.alexey.balinasoft.R;

public class EmailValidation extends Validation {

    //For access to resources
    private Context mContext;
    boolean isValid;

    public EmailValidation(Context context) {

        mContext = context;
    }

    @Override
    public boolean validate(String email) {

        if (TextUtils.isEmpty(email)) {
            isValid = false;
            setErrorMessage(mContext.getResources().getString(R.string.email_enter_requirement));
        } else if(!(isValid =android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            setErrorMessage(mContext.getResources().getString(R.string.email_invalid));
        }

        return isValid;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }
}
