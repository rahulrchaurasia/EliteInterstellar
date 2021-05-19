package com.interstellar.elite.rto;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.BaseFragment;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.miscNonRto.MiscNonRTOController;
import com.interstellar.elite.core.controller.product.ProductController;
import com.interstellar.elite.core.model.CityMainEntity;
import com.interstellar.elite.core.model.DashProductEntity;
import com.interstellar.elite.core.model.RtoCityMain;
import com.interstellar.elite.core.model.UserConstatntEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;
import com.interstellar.elite.core.requestmodel.Rto.AssistanceObtainingRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.response.RtoProductDisplayResponse;
import com.interstellar.elite.core.response.miscNonRto.ProductPriceResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.payment.PaymentRazorActivity;
import com.interstellar.elite.product.ProductMainActivity;
import com.interstellar.elite.rto.adapter.IRTOCity;
import com.interstellar.elite.rto.adapter.RtoMainAdapter;
import com.interstellar.elite.searchCity.SearchCityActivity;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.utility.DateTimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrivingLicenseFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IResponseSubcriber, IRTOCity {


    // region Declaration
    private Context mContext;
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etRTO, etRTO_OTH, etCity,etPincode, etLic;

    UserEntity loginEntity;
    Button btnBooked;

    DashProductEntity dashProductEntity;

    ScrollView scrollView;
    LinearLayout lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc, rlCorrect;
    LinearLayout lyLic, llCorrection, lyName, lyDOB, lyAddress, lyVehicleType;
    ImageView ivLogo, ivClientLogo, ivArrow, ivLic;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;
    EditText etName, etDOB, etAddress;
    CheckBox chkName, chkDOB, chkAddress;
    RadioButton rbfour ,rbtwo;
    TextInputLayout tilCity ,tilPincode;
    TextView ivTick;


    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;
    CardView cvClient;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    ProductPriceEntity productPriceEntity;
    // region Botom sheetDeclaration

    BottomSheetDialog mBottomSheetDialog;


    CityMainEntity cityMainEntity;
    RtoCityMain rtoMainEntity;

    RtoMainAdapter rtoMainAdapter;

    //endregion

    //endregion


    //region datepicker

    protected View.OnClickListener datePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.etDOB) {
                DateTimePicker.showAgePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
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

            }

        }
    };

    //endregion
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_assistan_obtain, container, false);


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();
        initialize(view);

        rlCorrect.setVisibility(View.GONE);
        llCorrection.setVisibility(View.GONE);
        lyVehicleType.setVisibility(View.GONE);

        setOnClickListener();


        prefManager = new PrefManager(getActivity());
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



                if (PRODUCT_CODE.equalsIgnoreCase("2.1")) {
                    lyLic.setVisibility(View.GONE);
                    lyVehicleType.setVisibility(View.VISIBLE);


                } else if (PRODUCT_CODE.equalsIgnoreCase("2.2") || PRODUCT_CODE.equalsIgnoreCase("2.3")) {
                    lyLic.setVisibility(View.VISIBLE);
                    lyVehicleType.setVisibility(View.VISIBLE);

                } else if (PRODUCT_CODE.equalsIgnoreCase("2.4")) {
                    lyLic.setVisibility(View.VISIBLE);
                    rlCorrect.setVisibility(View.VISIBLE);

                }

            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
          //  Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
        }


        // endregion


//        showDialog();
//        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), DrivingLicenseFragment.this);


    }

    // region Method

    //region bottomSheetDialog
    public void getBottomSheetDialog() {

        if (cityMainEntity != null && cityMainEntity.getRTOList().size() > 0) {


            mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.bottomSheetDialog);

            View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);

            mBottomSheetDialog.setContentView(sheetView);
            TextView txtHdr = mBottomSheetDialog.findViewById(R.id.txtHdr);
            RecyclerView rvRTO = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rvRTO);
            ImageView ivCross = (ImageView) mBottomSheetDialog.findViewById(R.id.ivCross);

            rvRTO.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvRTO.setHasFixedSize(true);
            rvRTO.setNestedScrollingEnabled(false);
            rtoMainAdapter = new RtoMainAdapter(DrivingLicenseFragment.this, cityMainEntity.getRTOList(), this);
            rvRTO.setAdapter(rtoMainAdapter);
            rvRTO.setVisibility(View.VISIBLE);


            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mBottomSheetDialog.isShowing()) {

                        mBottomSheetDialog.dismiss();
                    }
                }
            });


            txtHdr.setText("Select RTO");

            mBottomSheetDialog.setCancelable(false);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            mBottomSheetDialog.show();


        }else{
            getCustomToast("No RTO Available");

        }


    }

    public void setRTOData(String strRTOName, RtoCityMain rtoEntity) {
        etRTO.setText("" + strRTOName);

        rtoMainEntity = rtoEntity;

    }

    //endregion

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);

        etRTO = (EditText) view.findViewById(R.id.etRTO);
        etRTO_OTH = (EditText) view.findViewById(R.id.etRTO_OTH);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etPincode = view.findViewById(R.id.etPincode);

        etLic = (EditText) view.findViewById(R.id.etLic);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        etName = (EditText) view.findViewById(R.id.etName);
        etDOB = (EditText) view.findViewById(R.id.etDOB);
        etAddress = (EditText) view.findViewById(R.id.etAddress);

        chkName = (CheckBox) view.findViewById(R.id.chkName);
        chkDOB = (CheckBox) view.findViewById(R.id.chkDOB);
        chkAddress = (CheckBox) view.findViewById(R.id.chkAddress);

        rbfour =  view.findViewById(R.id.rbfour);
        rbtwo  =  view.findViewById(R.id.rbtwo);


        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlCorrect = (RelativeLayout) view.findViewById(R.id.rlCorrect);

        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);

        lyLic = (LinearLayout) view.findViewById(R.id.lyLic);

        llCorrection = (LinearLayout) view.findViewById(R.id.llCorrection);
        lyName = (LinearLayout) view.findViewById(R.id.lyName);
        lyDOB = (LinearLayout) view.findViewById(R.id.lyDOB);
        lyAddress = (LinearLayout) view.findViewById(R.id.lyAddress);
        lyVehicleType = (LinearLayout) view.findViewById(R.id.lyVehicleType);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);
        ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        ivLic = (ImageView) view.findViewById(R.id.ivLic);
        ivTick = (TextView) view.findViewById(R.id.ivTick);

        cvClient  = (CardView) view.findViewById(R.id.cvClient);
        tilCity  =  view.findViewById(R.id.tilCity);
        tilPincode  =  view.findViewById(R.id.tilPincode);
    }

    private void setOnClickListener() {

        etDOB.setOnClickListener(datePickerDialog);
        etCity.setFocusable(false);
        etCity.setClickable(true);

        etRTO.setFocusable(false);
        etRTO.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlCorrect.setOnClickListener(this);
        btnBooked.setOnClickListener(this);


        etCity.setOnClickListener(this);
        etRTO.setOnClickListener(this);

        chkName.setOnCheckedChangeListener(this);
        chkDOB.setOnCheckedChangeListener(this);
        chkAddress.setOnCheckedChangeListener(this);


    }

    private void bindData() {

        cvClient.setVisibility(View.GONE);


    }

    private boolean validate() {

        if ((etLic.getText().toString().trim().length() == 0) && (lyLic.getVisibility() == View.VISIBLE)) {
            etLic.requestFocus();
            etLic.setError("Enter Driving Licence");
            return false;
        }


        else if ((etCity.getText().toString().trim().length() == 0)) {
            etCity.requestFocus();
            etCity.setError("Selct City");
            return false;
        }

        else  if ((etRTO.getText().toString().trim().length() == 0)) {
            etRTO.requestFocus();
            etRTO.setError("Selct RTO");
            return false;
        }
        else  if (!validatePinCode(etPincode,tilPincode)) {

            return false;
        }


        else if (PRODUCT_CODE.equalsIgnoreCase("2.4")) {
            if ((chkAddress.isChecked() == false) && (chkDOB.isChecked() == false) && (chkName.isChecked() == false)) {
                getCustomToast("Please Select Atlease One Field");
                llCorrection.setVisibility(View.VISIBLE);

                return false;
            }

            if (chkName.isChecked() && (etName.getText().toString().trim().length() == 0)) {
                getCustomToast("Please Enter Correction In Name");
                llCorrection.setVisibility(View.VISIBLE);

                return false;
            }
            if (chkDOB.isChecked() && (etDOB.getText().toString().trim().length() == 0)) {
                getCustomToast("Please Enter Correction In DOB");
                llCorrection.setVisibility(View.VISIBLE);

                return false;
            }
            if (chkAddress.isChecked() && (etAddress.getText().toString().trim().length() == 0)) {
                getCustomToast("Please Enter Correction In Address");
                llCorrection.setVisibility(View.VISIBLE);
                return false;
            }


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

    private void saveData() {

        showDialog();
        AssistanceObtainingRequestEntity requestEntity = new AssistanceObtainingRequestEntity();
        requestEntity.setProdName(PRODUCT_NAME);
        requestEntity.setDl_address("");
        requestEntity.setDl_correct_name("");
        requestEntity.setDl_dob("");
        requestEntity.setDl_type("");
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("1");
        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));

        if(rtoMainEntity.getSeries_no() != null){
            requestEntity.setRto_id(rtoMainEntity.getSeries_no());
        }else{
            requestEntity.setRto_id("");
        }
        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));
        requestEntity.setDl_no(etLic.getText().toString());
        if (PRODUCT_CODE.equalsIgnoreCase("2.1") || PRODUCT_CODE.equalsIgnoreCase("2.1") || PRODUCT_CODE.equalsIgnoreCase("2.3"))
        {
            if(rbfour.isChecked()) {
                requestEntity.setDl_type(rbfour.getText().toString());
            }else{
                requestEntity.setDl_type(rbtwo.getText().toString());
            }
        }
        if(PRODUCT_CODE.equalsIgnoreCase("2.4"))
        {
            requestEntity.setDl_address(etAddress.getText().toString());
            requestEntity.setDl_correct_name(etName.getText().toString());
            requestEntity.setDl_dob(etDOB.getText().toString());
        }


        Bundle bundle = new Bundle();
        bundle.putString(Constants.REQUEST_TYPE, "2");
        bundle.putParcelable(Constants.PRODUCT_PAYMENT_REQUEST, requestEntity);


        getActivity().startActivity(new Intent(getActivity(), PaymentRazorActivity.class)
                .putExtra(Constants.PAYMENT_REQUEST_BUNDLE, bundle));


        getActivity().finish();


    }

    private void setScrollatBottom() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);
    }

    //endregion


  //region Event

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,mContext);
        switch (view.getId()) {


            case R.id.rlDoc:
                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);
                break;


            case R.id.rlCorrect:

                if (llCorrection.getVisibility() == View.GONE) {
                    llCorrection.setVisibility(View.VISIBLE);
                    ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow));

                } else {
                    llCorrection.setVisibility(View.GONE);
                    ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                }

                break;

            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                }else{
                    saveData();
                }


                break;
            case R.id.etCity:

                setScrollatBottom();
                startActivityForResult(new Intent(getActivity(), SearchCityActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), Constants.SEARCH_CITY_CODE);


                break;

            case R.id.etRTO:

                if (!etCity.getText().toString().equalsIgnoreCase("")) {

                     etRTO.setError(null);
                    getBottomSheetDialog();
                } else {
                    getCustomToast("Select City");
                }
                break;
        }


    }

    @Override
    public void getRTOCity(RtoCityMain entity) {

        if (mBottomSheetDialog != null) {

            if (mBottomSheetDialog.isShowing()) {
                if (entity != null) {

                    rtoMainEntity = entity;

                    setRTOData("" + rtoMainEntity.getSeries_no() + "-" + rtoMainEntity.getRto_location(), rtoMainEntity);
                    mBottomSheetDialog.dismiss();
                }

            }

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.chkName) {

            if (isChecked) {

                lyName.setBackgroundColor(getResources().getColor(R.color.seperator));

            } else {
                lyName.setBackgroundColor(getResources().getColor(R.color.white));
            }

        } else if (buttonView.getId() == R.id.chkDOB) {

            if (isChecked) {

                lyDOB.setBackgroundColor(getResources().getColor(R.color.seperator));

            } else {
                lyDOB.setBackgroundColor(getResources().getColor(R.color.white));
            }

        } else if (buttonView.getId() == R.id.chkAddress) {

            if (isChecked) {

                lyAddress.setBackgroundColor(getResources().getColor(R.color.seperator));

            } else {
                lyAddress.setBackgroundColor(getResources().getColor(R.color.white));
            }

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                cityMainEntity = data.getParcelableExtra(Constants.SEARCH_CITY_DATA);
                CITY_ID = String.valueOf(cityMainEntity.getCity_id());
                etCity.setText(cityMainEntity.getCityname());
                etCity.setError(null);

                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                entity.setVehicleno(userConstatntEntity.getVehicleno());
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
//

        if (response instanceof RtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((RtoProductDisplayResponse) response).getData().size() > 0) {


                    PRODUCT_ID = ((RtoProductDisplayResponse) response).getData().get(0).getProd_id();

                }
            }
        } else if (response instanceof ProductPriceResponse) {
            if (response.getStatus_code() == 0) {

                productPriceEntity = ((ProductPriceResponse) response).getData().get(0);
                getTatData();

            }
        }
    }

    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    //endregion
}
