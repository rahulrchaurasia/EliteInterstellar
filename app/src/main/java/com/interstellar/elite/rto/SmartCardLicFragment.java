package com.interstellar.elite.rto;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.BaseFragment;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.miscNonRto.MiscNonRTOController;
import com.interstellar.elite.core.model.CityMainEntity;
import com.interstellar.elite.core.model.DashProductEntity;
import com.interstellar.elite.core.model.RtoCityMain;
import com.interstellar.elite.core.model.UserConstatntEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;
import com.interstellar.elite.core.requestmodel.Rto.PaperToSmartCardRequestEntity;
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
public class SmartCardLicFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber, IRTOCity {

    // Service : 7

    // region Common Declaration
    private Context mContext;
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;
    CardView cvClient;


    EditText etCity  ;

    UserEntity loginEntity;
    Button btnBooked;

    DashProductEntity dashProductEntity;

    ScrollView scrollView;
    LinearLayout lvLogo, lyTAT;
    RelativeLayout lyParent ,rlDoc ;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;

    ProductPriceEntity productPriceEntity;

    //endregion

    EditText etRTO ,etPincode ,etLicNo ,etLicOwnerName ,etLicOwnerDob;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    // region Botom sheetDeclaration

    BottomSheetDialog mBottomSheetDialog;


    CityMainEntity cityMainEntity;
    RtoCityMain rtoMainEntity;

    RtoMainAdapter rtoMainAdapter;

    //endregion
    TextInputLayout tilCity ,tilRTO ,tilPincode , tilLicNo ,tilLicOwnerName, tilLicOwnerDob;


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
            rtoMainAdapter = new RtoMainAdapter(SmartCardLicFragment.this, cityMainEntity.getRTOList(), this);
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
           // getCustomToast("No RTO Available");

            getSnakeBar(lyParent,"No RTO Available");
        }


    }

    public void setRTOData(String strRTOName, RtoCityMain rtoEntity) {
        etRTO.setText("" + strRTOName);

        rtoMainEntity = rtoEntity;

    }

    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_smart_card_lic, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();
        initialize(view);
        setOnClickListener();
        setTextChangeListener();
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




            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
        }


        // endregion


//        showDialog();
//        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), SmartCardLicFragment.this);


    }

    private void initialize(View view) {


        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);

        cvClient  = (CardView) view.findViewById(R.id.cvClient);
        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        lyParent =  view.findViewById(R.id.lyParent);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);

        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);


        etRTO = (EditText) view.findViewById(R.id.etRTO);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        etLicNo = (EditText) view.findViewById(R.id.etLicNo);
        etLicOwnerName = (EditText) view.findViewById(R.id.etLicOwnerName);
        etLicOwnerDob = (EditText) view.findViewById(R.id.etLicOwnerDob);

        tilCity  =  (TextInputLayout)view.findViewById(R.id.tilCity);
        tilPincode  =  (TextInputLayout)view.findViewById(R.id.tilPincode);
        tilRTO  =  (TextInputLayout)view.findViewById(R.id.tilRTO);


        tilLicNo  =  (TextInputLayout)view.findViewById(R.id.tilLicNo);
        tilLicOwnerName  =  (TextInputLayout)view.findViewById(R.id.tilLicOwnerName);
        tilLicOwnerDob =  (TextInputLayout)view.findViewById(R.id.tilLicOwnerDob);



        etLicNo.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(30)});

    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        etRTO.setFocusable(false);
        etRTO.setClickable(true);


        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);
        etLicOwnerDob.setOnClickListener(this);
        etRTO.setOnClickListener(this);


    }

    private void setTextChangeListener(){

        etPincode.addTextChangedListener(getTextWatcher( tilPincode));

        etLicNo.addTextChangedListener(getTextWatcher( tilLicNo));
        etLicOwnerName.addTextChangedListener(getTextWatcher( tilLicOwnerName));



    }

    private void bindData() {

        cvClient.setVisibility(View.GONE);


    }

    private boolean validate() {

        if (!isEmpty(etLicNo)) {
            etLicNo.requestFocus();
            tilLicNo.setError("Enter License Number");
            return false;
        }
       else if (!isEmpty(etLicOwnerName)) {
            etLicOwnerName.requestFocus();
            tilLicOwnerName.setError("Enter Owner Name");
            return false;
        }
        else if (!isEmpty(etLicOwnerDob)) {
            etLicOwnerDob.requestFocus();
            tilLicOwnerDob.setError("Enter DOB");
            return false;
        }

        else if (!validateCity(etCity,tilCity)) {

            return false;
        }
        else if (!validateRTO(etRTO,tilRTO)) {

            return false;
        }

        else if (!validatePinCode(etPincode,tilPincode)) {

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

    private void saveData() {

        showDialog();
        PaperToSmartCardRequestEntity requestEntity = new PaperToSmartCardRequestEntity();
        requestEntity.setProdName(PRODUCT_NAME);
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("1");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));

        if(productPriceEntity.getRto_id() != null){
            requestEntity.setRto_id(productPriceEntity.getRto_id());
        }else{
            requestEntity.setRto_id("");
        }

        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));
        requestEntity.setPincode(etPincode.getText().toString());

        requestEntity.setDL_Correct_name(etLicOwnerName.getText().toString());
        requestEntity.setDL_DOB(etLicOwnerDob.getText().toString());
        requestEntity.setDL_No(etLicNo.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putString(Constants.REQUEST_TYPE,"7");
        bundle.putParcelable(Constants.PRODUCT_PAYMENT_REQUEST,requestEntity);


        getActivity().startActivity(new Intent(getActivity(), PaymentRazorActivity.class)
                .putExtra(Constants.PAYMENT_REQUEST_BUNDLE,bundle)) ;


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



    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,mContext);
        switch (view.getId()) {

            case R.id.etLicOwnerDob :
                DateTimePicker.showAgePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = simpleDateFormat.format(calendar.getTime());
                            etLicOwnerDob.setText(currentDay);
                            etLicOwnerDob.setError(null);
                            tilLicOwnerDob.setError(null);
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
                }
                else {

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
                    tilRTO.setError(null);
                    getBottomSheetDialog();
                } else {
                   // getCustomToast("Select City");
                    getSnakeBar(lyParent,"Select City");
                }
                break;
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
                tilCity.setError(null);
                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                entity.setVehicleno(loginEntity.getVehicleno());
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
        }
        if (response instanceof RtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((RtoProductDisplayResponse) response).getData().size() > 0) {


                    PRODUCT_ID = ((RtoProductDisplayResponse) response).getData().get(0).getProd_id();
                }
            }
        }
    }

    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
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
}
