package com.katsuro.alexey.balinasoft;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.katsuro.alexey.balinasoft.validity.EmailValidation;
import com.katsuro.alexey.balinasoft.validity.PasswordValidation;
import com.katsuro.alexey.balinasoft.validity.Validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexey on 6/29/18.
 */

public class SingUpFragment extends Fragment {

    private static final String TAG = SingUpFragment.class.getSimpleName();
    private TextInputLayout mEmailWrapper;
    private TextInputLayout mPasswordWrapper;

    private Button mSingUpButton;

    private Validation mEmailValidation;
    private Validation mPasswordValidation;


    public static SingUpFragment newInstance() {

        SingUpFragment fragment = new SingUpFragment();
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mEmailValidation = new EmailValidation(getActivity());
        mPasswordValidation = new PasswordValidation(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        View view = inflater.inflate(R.layout.fragment_sing_up,container,false);

        mEmailWrapper = view.findViewById(R.id.email_wrapper);

        mPasswordWrapper = view.findViewById(R.id.password_wrapper);




        AppCompatAutoCompleteTextView emailTextView = (AppCompatAutoCompleteTextView) mEmailWrapper.getEditText();
        ArrayList<String> emails = new ArrayList<>();
        emails.addAll(Arrays.asList(getResources().getStringArray(R.array.email_domains_array)));
        EmailFilterAdapter adapter = new EmailFilterAdapter(getActivity(),R.layout.email_suggestions_item,emails);
        emailTextView.setAdapter(adapter);

        setWrapperValidationListeners(mEmailWrapper, mEmailValidation);
        setWrapperValidationListeners(mPasswordWrapper, mPasswordValidation);




        mSingUpButton = view.findViewById(R.id.sing_up_button);

        mSingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                updateWrapperUI(mPasswordWrapper, mPasswordValidation);
                updateWrapperUI(mEmailWrapper, mEmailValidation);

                if (mPasswordValidation.isValid() && mEmailValidation.isValid()) {
                    doSingUp();
                }
            }
        });

        //Return state after screen rotation
        if(mEmailValidation.isValid()){
            setInputTextLayoutColor(mEmailWrapper,getResources().getColor(R.color.greenblue));
        }

        if(mPasswordValidation.isValid()){
            setInputTextLayoutColor(mPasswordWrapper,getResources().getColor(R.color.greenblue));
        }


        return view;
    }

    private void setWrapperValidationListeners(final TextInputLayout wrapper, final Validation validation) {
        wrapper.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG,"Action done");
                    updateWrapperUI(wrapper,validation);
                    handled = true;
                }
                hideKeyboard();
                return handled;
            }
        });

        wrapper.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    Log.d(TAG,"Lose Focus");
                    String text = wrapper.getEditText().getText().toString();
                    if(!text.equals("")){
                        updateWrapperUI(wrapper,validation);
                    } else {
                        wrapper.setErrorEnabled(false);
                        wrapper.setHintTextAppearance(R.style.HintTextLable);
                        setInputTextLayoutColor(wrapper,getResources().getColor(R.color.white));
                    }

                }
            }
        });
        ((CustomEditText)(wrapper.getEditText())).setOnBackPressedListener(new CustomEditText.OnBackPressed() {
            @Override
            public void onBackPressed() {
                Log.d(TAG,"onBackPressed");
                String text = wrapper.getEditText().getText().toString();
                ((CustomEditText) wrapper.getEditText()).dismissDropDown();
                if(!text.equals("")){
                    updateWrapperUI(wrapper,validation);
                } else {
                    updateToDefaultUI(wrapper, R.style.HintTextLable, getResources().getColor(R.color.white));
                }
            }
        });

    }

    private void updateToDefaultUI(TextInputLayout wrapper, int hintTextLable, int color) {
        wrapper.setErrorEnabled(false);
        wrapper.setHintTextAppearance(hintTextLable);
        setInputTextLayoutColor(wrapper, color);
    }


    private void updateWrapperUI(TextInputLayout wrapper, Validation validation) {
        String text = wrapper.getEditText().getText().toString();
        boolean isValid = validation.validate(text);

        if (!isValid) {
            wrapper.setError(validation.getErrorMessage());
            wrapper.setHintTextAppearance(R.style.HintTextLable);
            setInputTextLayoutColor(wrapper,getResources().getColor(R.color.white));
        } else {
            updateToDefaultUI(wrapper, R.style.HintTextLableRight, getResources().getColor(R.color.greenblue));
        }
    }

    //Programmatically change the text color of the default hint and floating hint
    public static void setInputTextLayoutColor(TextInputLayout textInputLayout, @ColorInt int color) {
        TextInputLayout til = textInputLayout;
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{ color }));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{ color }));
            textInputLayout.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSingUp() {
        Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
