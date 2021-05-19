package com.interstellar.elite.utility;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class addListenerOnTextChange implements TextWatcher {
    private Context mContext;
    TextInputLayout textInputLayout;

    public addListenerOnTextChange(Context context, TextInputLayout textInputLayout) {
        super();
        this.mContext = context;
        this.textInputLayout =  textInputLayout;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().length() >0){
            textInputLayout.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}