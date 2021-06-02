package com.interstellar.elite.assure;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.interstellar.elite.core.model.RTOServiceEntity;
import com.interstellar.elite.core.model.UserConstatntEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.MiscReminderPUCRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.response.miscNonRto.ProductPriceResponse;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;
import com.interstellar.elite.document.DocUploadActivity;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.product.ProductMainActivity;
import com.interstellar.elite.searchCity.SearchCityActivity;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.utility.DateTimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PUCExpiryRenewalFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etDate;


    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etPincode, etCity, etVehicle;
    UserEntity loginEntity;
    Button btnBooked;
    CardView cvClient;

    DashProductEntity dashProductEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle, lvLogo, lyTAT;
    RelativeLayout rlDoc, rlEditVehicle;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;
    TextInputLayout tilDate, tilCity , tilPincode;


    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;

    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_puc, container, false);

       // getActivity().finish();
        return view;
    }


    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);
        cvClient  = (CardView) view.findViewById(R.id.cvClient);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditVehicle = (RelativeLayout) view.findViewById(R.id.rlEditVehicle);

        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);


        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        etDate = view.findViewById(R.id.etDate);
        tilDate = (TextInputLayout) view.findViewById(R.id.tilDate);
        tilCity = (TextInputLayout) view.findViewById(R.id.tilCity);
        tilPincode = (TextInputLayout) view.findViewById(R.id.tilPincode);

        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});

    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);

        etDate.setOnClickListener(this);


    }

    private void bindData() {

        cvClient.setVisibility(View.GONE);

        if(userConstatntEntity.getVehicleno().length() >0)
        {
            etVehicle.setText(userConstatntEntity.getVehicleno());
            etVehicle.setEnabled(false);
          //  rlEditVehicle.setVisibility(View.VISIBLE);
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_edit));


        }else{
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));
          //  rlEditVehicle.setVisibility(View.GONE);
            etVehicle.setEnabled(true);

        }


    }

    private void setVehicleEdiatable() {
        etVehicle.setEnabled(true);

        etVehicle.setText("");
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        initialize(view);

        setOnClickListener();

        setTextChangeListener();

        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

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

        super.onViewCreated(view, savedInstanceState);

    }

    private void setTextChangeListener(){



        etPincode.addTextChangedListener(getTextWatcher( tilPincode));

    }

    private boolean validate() {
        if (!isEmpty(etVehicle)) {
            etVehicle.requestFocus();
            etVehicle.setError("Enter Vehicle Number");
            return false;
        } else if (!isEmpty(etDate)) {
            etDate.requestFocus();
            tilDate.setError("Enter Accident Date");
            return false;
        } else if (!isEmpty(etCity)) {
            etCity.requestFocus();
            tilCity.setError("Enter City");
            return false;
        }
        if (!isEmpty(etPincode) || etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            tilPincode.setError("Enter Pincode");
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
        MiscReminderPUCRequestEntity requestEntity = new MiscReminderPUCRequestEntity();
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("0");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));
        if(productPriceEntity.getRto_id() != null){
            requestEntity.setRto_id(productPriceEntity.getRto_id());
        }else{
            requestEntity.setRto_id("");
        }


        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));
        requestEntity.setVehicle_no(etVehicle.getText().toString());
        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setPucexpirydate(etDate.getText().toString());


        new MiscNonRTOController(mContext).saveMiscRemiderPUC(requestEntity, this);
    }

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,mContext);
        switch (view.getId()) {


            case R.id.etDate:

                DateTimePicker.showOpenDatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = simpleDateFormat.format(calendar.getTime());
                            etDate.setText(currentDay);
                            tilDate.setError(null);
                        }
                    }
                });
                break;


            case R.id.rlDoc:

                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);
                break;


            case R.id.rlEditVehicle:

                setVehicleEdiatable();
                break;
            case R.id.btnBooked:
                if (validate() == false) {
                    return;
                } else {

                    saveData();
                }

                break;

            case R.id.etCity:
                if (!isEmpty(etVehicle)) {
                    etVehicle.requestFocus();
                    etVehicle.setError("Enter Vehicle Number");
                    return;
                }
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

                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                entity.setVehicleno(etVehicle.getText().toString());
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


    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
