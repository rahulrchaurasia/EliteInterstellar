package com.interstellar.elite.forgot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.register.RegisterController;
import com.interstellar.elite.core.response.CommonResponse;
import com.interstellar.elite.utility.Constants;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ForgotPasswordActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {

    EditText etMobile;
    AppCompatButton btnSubmit;
    ImageView imgClose;
    TextInputLayout tilMobile;
    CoordinatorLayout lyParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lyParent =  (CoordinatorLayout) findViewById(R.id.lyParent);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        tilMobile  = (TextInputLayout) findViewById(R.id.tilMobile);

        btnSubmit.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        etMobile.addTextChangedListener(getTextWatcher(tilMobile));

    }



    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, ForgotPasswordActivity.this);
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (!isEmpty(etMobile)) {
                    etMobile.requestFocus();
                    tilMobile.setError("Enter Mobile No.");
                    return;
                }else if(etMobile.getText().toString().trim().length() < 10){
                    etMobile.requestFocus();
                    tilMobile.setError("Enter Valid Mobile No.");
                    return;
                }

                showDialog("Please Wait...");
                new RegisterController(ForgotPasswordActivity.this).forgotPassword(etMobile.getText().toString(), this);
                break;

            case R.id.imgClose:

                this.finish();

        }

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof CommonResponse) {
            if (response.getStatus_code() == 0) {

              //  getCustomToast( response.getMessage());
                getSnakeBar(lyParent,response.getMessage());
                etMobile.setText("");

            }
        }
    }

    @Override
    public void OnFailure(String Error ) {
        cancelDialog();

        getSnakeBar(lyParent,Error);
    }
}
