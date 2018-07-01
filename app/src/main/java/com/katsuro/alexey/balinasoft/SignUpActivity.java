package com.katsuro.alexey.balinasoft;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class SignUpActivity extends SingleFragmentActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return SingUpFragment.newInstance();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG,"onBackPressed: ");
    }
}
