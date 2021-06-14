package com.interstellar.elite.changePassword;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.BaseFragment;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.register.RegisterController;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.response.CommonResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.utility.Utility;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener , IResponseSubcriber {

    EditText etoldpswd  , etnewpswd, etconfirmpswd;
    TextInputLayout tilOldpswd,tilNewpswd, tilConfirmpswd;
    Button btnSubmit;
    LinearLayout lyParent;

    PrefManager prefManager;
    UserEntity loginEntity;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initialize_Widgets(view);

        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        return view;
    }

    private void initialize_Widgets(View view) {

        lyParent =  (LinearLayout) view.findViewById(R.id.lyParent);
        etoldpswd = (EditText) view.findViewById(R.id.etoldpswd);
        etnewpswd = (EditText) view.findViewById(R.id.etnewpswd);
        etconfirmpswd = (EditText) view.findViewById(R.id.etconfirmpswd);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        tilOldpswd = (TextInputLayout) view.findViewById(R.id.tilOldpswd);
        tilNewpswd = (TextInputLayout) view.findViewById(R.id.tilNewpswd);
        tilConfirmpswd = (TextInputLayout) view.findViewById(R.id.tilConfirmpswd);

        btnSubmit.setOnClickListener(this);
        setTextChangeListener();


    }

    private void setTextChangeListener(){



        etoldpswd.addTextChangedListener(getTextWatcher( tilOldpswd));
        etnewpswd.addTextChangedListener(getTextWatcher( tilNewpswd));

        etconfirmpswd.addTextChangedListener(getTextWatcher( tilConfirmpswd));


    }


    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,requireContext());
        if (view.getId() == R.id.btnSubmit)
        {

            if (etoldpswd.getText().toString().matches("")) {

                etoldpswd.requestFocus();
                tilOldpswd.setError("Enter Old Password");

                return;
            }
            if (etnewpswd.getText().toString().matches("")) {

                etnewpswd.requestFocus();
               tilNewpswd.setError("Enter New Password");
                return;
            }
            if (etconfirmpswd.getText().toString().matches("")) {

                etconfirmpswd.requestFocus();
                tilConfirmpswd.setError("Enter Confirm Password");
                return;
            }
            if (!etconfirmpswd.getText().toString().matches(etnewpswd.getText().toString())) {

                etconfirmpswd.requestFocus();
                tilConfirmpswd.setError("Password Not Matched");
                return;
            }

            showDialog();
            new RegisterController(getActivity()).changePassword(loginEntity.getMobile(), etoldpswd.getText().toString(), etnewpswd.getText().toString(), this);
        }


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof CommonResponse) {
            if (response.getStatus_code() == 0) {
               // Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();

                //getCustomToast( response.getMessage());
                 getSnakeBar(lyParent, response.getMessage());
                 prefManager.clearPassword();
                clear();

            }
        }
    }

    private void clear()
    {
        etoldpswd.setText("");
        etnewpswd.setText("");
        etconfirmpswd.setText("");

    }
    @Override
    public void OnFailure(String error) {
        cancelDialog();
        getSnakeBar(lyParent, error);
    }
}
