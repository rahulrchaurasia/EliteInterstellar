package com.interstellar.elite.rto;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.interstellar.elite.core.controller.product.ProductController;
import com.interstellar.elite.core.model.CityMainEntity;
import com.interstellar.elite.core.model.DashProductEntity;
import com.interstellar.elite.core.model.RTOServiceEntity;
import com.interstellar.elite.core.model.UserConstatntEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;
import com.interstellar.elite.core.requestmodel.Rto.AdditionHypothecationRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.response.RtoProductDisplayResponse;
import com.interstellar.elite.core.response.miscNonRto.ProductPriceResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.payment.PaymentRazorActivity;
import com.interstellar.elite.product.ProductMainActivity;
import com.interstellar.elite.searchCity.SearchCityActivity;
import com.interstellar.elite.utility.Constants;


import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HypotheticalFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {


      // service :3
    // region Common Declration
    private Context mContext;
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etVehicle , etCity , etFinance ,etPincode;
    UserEntity loginEntity;
    Button btnBooked;
    CardView cvClient;
    TextInputLayout tilCity ,tilPincode,tilFinance;
    DashProductEntity dashProductEntity;


    ScrollView scrollView;
    LinearLayout lyVehicle ,lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc ,rlEditVehicle;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hypothetical, container, false);

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
           // Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
        }


        // endregion


        // Not in Used
//        showDialog();
//        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), HypotheticalFragment.this);



    }

    // region Method
    private void initialize(View view) {



        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);
        etFinance = (EditText) view.findViewById(R.id.etFinance);
        etPincode = (EditText) view.findViewById(R.id.etPincode);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditVehicle = (RelativeLayout) view.findViewById(R.id.rlEditVehicle);

        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);
        cvClient  = (CardView) view.findViewById(R.id.cvClient);

        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);
        tilCity  =  (TextInputLayout)view.findViewById(R.id.tilCity);
        tilPincode  =  (TextInputLayout)view.findViewById(R.id.tilPincode);
        tilFinance  =  (TextInputLayout)view.findViewById(R.id.tilFinance);
        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);



    }

    private void setTextChangeListener(){



        etFinance.addTextChangedListener(getTextWatcher(tilFinance));

        etPincode.addTextChangedListener(getTextWatcher( tilPincode));

    }

    private void bindData() {

        cvClient.setVisibility(View.GONE);

        if(loginEntity.getVehicleno().length() >0)
        {
            etVehicle.setText(loginEntity.getVehicleno());
            etVehicle.setEnabled(false);
           // rlEditVehicle.setVisibility(View.VISIBLE);
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
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));

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
        AdditionHypothecationRequestEntity requestEntity = new AdditionHypothecationRequestEntity();
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
        requestEntity.setVehicleno(etVehicle.getText().toString());

        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setVehicle_finance_form(etFinance.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putString(Constants.REQUEST_TYPE,"3");
        bundle.putParcelable(Constants.PRODUCT_PAYMENT_REQUEST,requestEntity);


        getActivity().startActivity(new Intent(getActivity(), PaymentRazorActivity.class)
                .putExtra(Constants.PAYMENT_REQUEST_BUNDLE,bundle)) ;


        getActivity().finish();



    }

    private boolean validate() {
        if (!validateVehicle(etVehicle)) {

            return false;
        }
        else if (!isEmpty(etFinance)) {
            etFinance.requestFocus();
            tilFinance.setError("Enter Vehicle Finanace From");
            return false;
        }
        else if (!validateCity(etCity,tilCity)) {

            return false;
        }
        else if (!validatePinCode(etPincode,tilPincode)) {

            return false;
        }

        return true;
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


            case R.id.rlEditVehicle:

                setVehicleEdiatable();
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
        }else if (response instanceof RtoProductDisplayResponse) {
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

    //endregion
}
