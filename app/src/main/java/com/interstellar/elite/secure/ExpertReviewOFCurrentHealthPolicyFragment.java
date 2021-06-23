package com.interstellar.elite.secure;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.BaseFragment;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.miscNonRto.MiscNonRTOController;
import com.interstellar.elite.core.model.CityMainEntity;
import com.interstellar.elite.core.model.DashProductEntity;
import com.interstellar.elite.core.model.UserConstatntEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ExpertReviewOfCurrentHealthPolicyRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.response.miscNonRto.ProductPriceResponse;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.product.ProductMainActivity;
import com.interstellar.elite.searchCity.SearchCityActivity;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.utility.DateTimePicker;
import com.interstellar.elite.utility.addListenerOnTextChange;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpertReviewOFCurrentHealthPolicyFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IResponseSubcriber {

    // Service : 14

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etNameOfProposer, etSumAssured, etDOB;
    TextView txtFamilyError;
    TextInputLayout tilNameOfProposer, tilSumAssured, tilDOB, tilCity, tilPincode;


    Spinner spNoOfFamily;

    RadioGroup radioGroup;
    RadioButton rbIndividual, rbFloater;

    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etPincode, etCity;

    UserEntity loginEntity;
    Button btnBooked;
    CardView cvClient;

    DashProductEntity dashProductEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle, lvLogo, lyTAT, lyFamily;
    RelativeLayout rlDoc;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;

    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis_health_plan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        mContext = view.getContext();
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        initialize(view);

        setOnClickListener();


        OrderID = 0;
        bindData();

        // region Filter Type

        if (getArguments() != null) {


            if (getArguments().getParcelable(Constants.SUB_PRODUCT_DATA) != null) {

                dashProductEntity = getArguments().getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = dashProductEntity.getTitle();
                PRODUCT_ID = dashProductEntity.getProductId();
                PRODUCT_CODE = dashProductEntity.getProductCode();


            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);

        }


        // endregion


        spNoOfFamily.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos == 0) {
                    // txtFamilyError.setVisibility(View.VISIBLE);
                } else {
                    txtFamilyError.setVisibility(View.GONE);
                    spNoOfFamily.setBackgroundResource(R.drawable.custom_spinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setTextChangeListener();
        super.onViewCreated(view, savedInstanceState);

    }


    private void initialize(View view) {

        etDOB = view.findViewById(R.id.etDOB);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        cvClient = (CardView) view.findViewById(R.id.cvClient);

        etNameOfProposer = (EditText) view.findViewById(R.id.etNameOfProposer);
        etSumAssured = (EditText) view.findViewById(R.id.etSumAssured);


        radioGroup = view.findViewById(R.id.radioGroup);
        rbFloater = view.findViewById(R.id.rbFloater);


        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);

        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);


        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        etDOB = (EditText) view.findViewById(R.id.etDOB);

        // region Floater
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        rbIndividual = (RadioButton) view.findViewById(R.id.rbIndividual);
        rbFloater = (RadioButton) view.findViewById(R.id.rbFloater);

        lyFamily = (LinearLayout) view.findViewById(R.id.lyFamily);
        spNoOfFamily = (Spinner) view.findViewById(R.id.spNoOfFamily);

        tilNameOfProposer = (TextInputLayout) view.findViewById(R.id.tilNameOfProposer);
        tilSumAssured = (TextInputLayout) view.findViewById(R.id.tilSumAssured);
        tilDOB = (TextInputLayout) view.findViewById(R.id.tilDOB);
        txtFamilyError = (TextView) view.findViewById(R.id.txtFamilyError);
        tilCity = (TextInputLayout) view.findViewById(R.id.tilCity);
        tilPincode = (TextInputLayout) view.findViewById(R.id.tilPincode);


        //endregion


    }

    private void setTextChangeListener() {


        etNameOfProposer.addTextChangedListener(getTextWatcher(tilNameOfProposer));

        // etNameOfProposer.addTextChangedListener(new addListenerOnTextChange(requireContext(), tilNameOfProposer));
        etSumAssured.addTextChangedListener(getTextWatcher(tilSumAssured));
        etDOB.addTextChangedListener(getTextWatcher(tilDOB));

        //etCity.addTextChangedListener(getTextWatcher(tilCity));
        etPincode.addTextChangedListener(getTextWatcher(tilPincode));
    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);
        etCity.setOnClickListener(this);

        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etDOB.setOnClickListener(this);

        rbFloater.setOnCheckedChangeListener(this);
        rbIndividual.setOnCheckedChangeListener(this);

        rbIndividual.setChecked(true);
        lyFamily.setVisibility(View.GONE);


    }

    private void bindData() {
        cvClient.setVisibility(View.GONE);


    }


    private boolean validate() {
        if (!validateProposer(etNameOfProposer, tilNameOfProposer)) {
            return false;

        } else if (!validateSumAssured(etSumAssured, tilSumAssured)) {
            return false;
        } else if (!isEmpty(etDOB)) {
            tilDOB.setError("Enter  DOB");
            return false;
        } else if (rbFloater.isChecked() && spNoOfFamily.getSelectedItemPosition() == 0) {

            txtFamilyError.setVisibility(View.VISIBLE);
            spNoOfFamily.setBackgroundResource(R.drawable.custom_rect_spinner);

            return false;

//            if(spNoOfFamily.getSelectedItemPosition() == 0){
//
//            }
        } else if (!validateCity(etCity, tilCity)) {

            return false;
        } else if (!validatePinCode(etPincode, tilPincode)) {
            return false;
        }
        return true;
    }


    private void getTatData() {
        if (productPriceEntity != null) {
            lvLogo.setVisibility(View.VISIBLE);
            txtCharges.setText(productPriceEntity.getPrice());
            txtTAT.setText(productPriceEntity.getTAT());

        } else {
            lvLogo.setVisibility(View.GONE);
        }
    }

    private void setScrollatBottom() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);
    }


    private void saveData() {

        showDialog();
        ExpertReviewOfCurrentHealthPolicyRequestEntity requestEntity = new ExpertReviewOfCurrentHealthPolicyRequestEntity();
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("1");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));
        if (productPriceEntity.getRto_id() != null) {
            requestEntity.setRto_id(productPriceEntity.getRto_id());
        } else {
            requestEntity.setRto_id("");
        }


        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));

        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setDOB(etDOB.getText().toString());
        requestEntity.setSume_assured(etSumAssured.getText().toString());
        requestEntity.setName_of_praposal(etNameOfProposer.getText().toString());

        if (rbIndividual.isChecked()) {
            requestEntity.setCovered_for(rbIndividual.getText().toString());//hl
            requestEntity.setNo_family_member("");

        } else if (rbFloater.isChecked()) {
            requestEntity.setCovered_for(rbFloater.getText().toString());
            requestEntity.setNo_family_member(spNoOfFamily.getSelectedItem().toString());
        }


        new MiscNonRTOController(mContext).saveAnalysisCurrentHealth(requestEntity, this);
    }


    public void slideToBottom(View view, Boolean bln) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        if (bln) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, mContext);
        switch (view.getId()) {

            case R.id.etDOB:

                DateTimePicker.showPrevPickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = simpleDateFormat.format(calendar.getTime());
                            etDOB.setText(currentDay);
                        }
                    }
                });
                break;

            case R.id.rlDoc:
                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);
                break;


            case R.id.btnBooked:
                if (validate() == false) {
                    return;
                } else {

                    showConfirmAlert();

                }

                break;

            case R.id.etCity:
                setScrollatBottom();
                startActivityForResult(new Intent(getActivity(), SearchCityActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), Constants.SEARCH_CITY_CODE);

                break;


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                CityMainEntity cityMainEntity = data.getParcelableExtra(Constants.SEARCH_CITY_DATA);
                CITY_ID = String.valueOf(cityMainEntity.getCity_id());
                etCity.setText(cityMainEntity.getCityname());
                etCity.setError(null);
                tilCity.setError(null);

                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                //   entity.setVehicleno(etVehicle.getText().toString());
                entity.setCityid(CITY_ID);
                entity.setProduct_id(String.valueOf(PRODUCT_ID));
                entity.setProductcode(PRODUCT_CODE);
                entity.setUserid(String.valueOf(loginEntity.getUser_id()));
                entity.setMake("");
                entity.setModel("");

                new MiscNonRTOController(mContext).getProductTAT(entity, this);

                //endregion

            }
        }

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ProductPriceResponse) {
            if (response.getStatus_code() == 0) {

                productPriceEntity = ((ProductPriceResponse) response).getData().get(0);
                getTatData();

            }
        } else if (response instanceof ProvideClaimAssResponse) {
            if (response.getStatus_code() == 0) {

                showMiscPaymentAlert(btnBooked, response.getMessage().toString(), ((ProvideClaimAssResponse) response).getData().get(0));
            }
        }
        //
    }

    public void showConfirmAlert() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.app_name));

            builder.setMessage(getString(R.string.confirmMessage));
            String positiveText = "Yes";
            String negativeText = "No";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            saveData();

                        }
                    });

            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.rbFloater) {

            if (isChecked) {
                lyFamily.setVisibility(View.VISIBLE);

            }
        } else if (buttonView.getId() == R.id.rbIndividual) {

            if (isChecked) {
                lyFamily.setVisibility(View.GONE);

            }
        }
    }
}
