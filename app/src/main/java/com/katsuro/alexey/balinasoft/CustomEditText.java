package com.katsuro.alexey.balinasoft;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by alexey on 7/1/18.
 */

public class CustomEditText extends AppCompatAutoCompleteTextView {
    private static final String TAG = CustomEditText.class.getSimpleName();
    OnBackPressed mOnBackPressedListener;

    public CustomEditText(Context context)
    {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    { }

    public interface OnBackPressed {
       void onBackPressed();
    }

    public void setOnBackPressedListener(OnBackPressed action) {
        mOnBackPressedListener = action;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        Log.d(TAG,"onKeyPreIme: ");

        if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d(TAG,"KeyEvent.KEYCODE_BACK: ");
            if(mOnBackPressedListener !=null) {
                mOnBackPressedListener.onBackPressed();
            }
        }
        return  super.onKeyPreIme(keyCode,event);
    }
}
