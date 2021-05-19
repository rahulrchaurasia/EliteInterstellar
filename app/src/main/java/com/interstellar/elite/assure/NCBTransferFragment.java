package com.interstellar.elite.assure;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import com.interstellar.elite.core.controller.register.RegisterController;
import com.interstellar.elite.core.model.CityMainEntity;
import com.interstellar.elite.core.model.DashProductEntity;
import com.interstellar.elite.core.model.FastLaneDataEntity;
import com.interstellar.elite.core.model.RTOServiceEntity;
import com.interstellar.elite.core.model.RtoCityMain;
import com.interstellar.elite.core.model.UserConstatntEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.TransferBenefitsNCBRequestEntity;
import com.interstellar.elite.core.response.FastLaneDataResponse;
import com.interstellar.elite.core.response.miscNonRto.ProductPriceResponse;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.makemodel.old.MakeAdapter;
import com.interstellar.elite.product.ProductMainActivity;
import com.interstellar.elite.rto.adapter.IRTOCity;
import com.interstellar.elite.rto.adapter.RtoMainAdapter;
import com.interstellar.elite.searchCity.SearchCityActivity;
import com.interstellar.elite.utility.Constants;


import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NCBTransferFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber, IRTOCity {

    // Service 13

    private Context mContext;
    AutoCompleteTextView acMake;
    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etVehicle, etInsCompanyName, etPincode, etCity, etRTO;
    DashProductEntity dashProductEntity;
    UserEntity loginEntity;
    Button btnBooked, btnGo;
    CardView cvClient;

    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle, lvLogo, lyTAT;
    RelativeLayout rlDoc, rlEditVehicle;

    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;


    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;
    boolean IsMakeValid = false;
    MakeAdapter makeAdapter;

    // region Botom sheetDeclaration

    BottomSheetDialog mBottomSheetDialog;


    CityMainEntity cityMainEntity;
    RtoCityMain rtoMainEntity;

    RtoMainAdapter rtoMainAdapter;
    TextInputLayout tilCity ,tilPincode;

    //endregion

    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_ncb, container, false);
        return view;
    }

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
            rtoMainAdapter = new RtoMainAdapter(NCBTransferFragment.this, cityMainEntity.getRTOList(), this);
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

        } else {
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

        scrollView = view.findViewById(R.id.scrollView);
        btnBooked = view.findViewById(R.id.btnBooked);
        btnGo = view.findViewById(R.id.btnGo);
        etCity = view.findViewById(R.id.etCity);
        etPincode = view.findViewById(R.id.etPincode);
        etVehicle = view.findViewById(R.id.etVehicle);
        etRTO = view.findViewById(R.id.etRTO);
        cvClient  = (CardView) view.findViewById(R.id.cvClient);

        etInsCompanyName = view.findViewById(R.id.etInsCompanyName);

        txtCharges = view.findViewById(R.id.txtCharges);
        txtPrdName = view.findViewById(R.id.txtPrdName);
        txtDoc = view.findViewById(R.id.txtDoc);
        txtClientName = view.findViewById(R.id.txtClientName);
        txtTAT = view.findViewById(R.id.txtTAT);

        rlDoc = view.findViewById(R.id.rlDoc);
        rlEditVehicle = view.findViewById(R.id.rlEditVehicle);

        lyVehicle = view.findViewById(R.id.lyVehicle);
        lvLogo = view.findViewById(R.id.lvLogo);


        lyTAT = view.findViewById(R.id.lyTAT);


        ivLogo = view.findViewById(R.id.ivLogo);
        ivClientLogo = view.findViewById(R.id.ivClientLogo);

        acMake = view.findViewById(R.id.acMake);

        tilCity  =  view.findViewById(R.id.tilCity);
        tilPincode  =  view.findViewById(R.id.tilPincode);

    }

    private void bindMakeModel() {
        acMake.setText(userConstatntEntity.getMake());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                acMake.dismissDropDown();
            }
        });

        if (userConstatntEntity.getMake() != "") {
            IsMakeValid = true;
        }

    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);
        etCity.setOnClickListener(this);

        etRTO.setOnClickListener(this);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);
        btnGo.setOnClickListener(this);

    }

    private void setAutoComplete() {
        acMake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acMake.setError(null);
                acMake.setSelection(0);
                IsMakeValid = true;

            }
        });


        acMake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = acMake.getText().toString();
                ListAdapter listAdapter = acMake.getAdapter();
                if (acMake.getAdapter() != null) {
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString().toUpperCase();
                        if (str.compareTo(temp) == 0) {
                            acMake.setError(null);

                            IsMakeValid = true;


                            return;
                        }
                    }
                }

                acMake.setError("Invalid Make");
                acMake.setFocusable(true);
                IsMakeValid = false;


            }
        });


    }

    private void bindData() {
        cvClient.setVisibility(View.GONE);

        if (userConstatntEntity.getVehicleno().length() > 0) {
            etVehicle.setText(userConstatntEntity.getVehicleno());
            etVehicle.setEnabled(false);
          //  rlEditVehicle.setVisibility(View.VISIBLE);
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_edit));
            btnGo.setVisibility(View.GONE);

        } else {
           // rlEditVehicle.setVisibility(View.GONE);
            etVehicle.setText("");
            etVehicle.setEnabled(true);
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));

            btnGo.setVisibility(View.VISIBLE);

        }
    }

    private boolean validate() {

        if (!validateVehicle(etVehicle)) {

            return false;
        } else if (!validateMake(acMake, IsMakeValid)) {

            return false;
        } else if (!validateInsCompName(etInsCompanyName)) {

            return false;
        } else if (!validateCity(etCity,tilCity)) {

            return false;
        } else if (!validatePinCode(etPincode,tilPincode)) {

            return false;
        }
        return true;
    }

    private void setVehicleEdiatable() {
        etVehicle.setEnabled(true);

        etVehicle.setText("");
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_content));

        btnGo.setVisibility(View.VISIBLE);
        acMake.setEnabled(false);
        acMake.setText("");


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

        TransferBenefitsNCBRequestEntity requestEntity = new TransferBenefitsNCBRequestEntity();

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
        requestEntity.setVehicleno(String.valueOf(etVehicle.getText()));


        requestEntity.setPincode(etPincode.getText().toString());

        requestEntity.setMake(acMake.getText().toString());
        requestEntity.setInsure_company_name(etInsCompanyName.getText().toString());


        new MiscNonRTOController(mContext).saveTransferNCBBenefits(requestEntity, this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        initialize(view);

        setOnClickListener();


        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();
        OrderID = 0;
        acMake.setThreshold(2);
        acMake.setSelection(0);

        acMake.setEnabled(false);

        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});



        if (prefManager.getMake() != null) {
            makeAdapter = new MakeAdapter(getActivity(), R.layout.fragment_transfer_ncb, R.id.lbl_name, prefManager.getMake());
            acMake.setAdapter(makeAdapter);
            bindMakeModel();

        }

        bindData();

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


        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, mContext);
        switch (view.getId()) {

            case R.id.rlEditVehicle:

                setVehicleEdiatable();
                break;

            case R.id.rlDoc:
                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);
                break;


            case R.id.btnBooked:
                if (validate() == false) {
                    return;
                } else {

                    saveData();
                }

                break;

            case R.id.btnGo:

                if (!isEmpty(etVehicle)) {
                    etVehicle.requestFocus();
                    etVehicle.setError("Enter Vehicle Number");
                    return;
                } else {

                    showDialog();
                    new RegisterController(getActivity()).getVechileDetails(etVehicle.getText().toString().trim(), this);

                }

                break;

            case R.id.etCity:
                setScrollatBottom();

                startActivityForResult(new Intent(getActivity(), SearchCityActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), Constants.SEARCH_CITY_CODE);

                break;

            case R.id.etRTO:
                // setScrollatBottom();
                if (!etCity.getText().toString().equalsIgnoreCase("")) {

                    getBottomSheetDialog();
                } else {

                    etCity.requestFocus();
                    etCity.setError("Enter City");
                }
                break;


        }

    }

    private void resetMakeModel() {

        makeAdapter = new MakeAdapter(getActivity(), R.layout.activity_sign_up, R.id.lbl_name, prefManager.getMake());
        acMake.setAdapter(makeAdapter);
        setAutoComplete();

        acMake.setEnabled(true);


        acMake.setError(null);

        acMake.setText("");

        IsMakeValid = false;

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
        }else if (response instanceof FastLaneDataResponse) {

            // Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
            FastLaneDataEntity fastLaneDataEntity = ((FastLaneDataResponse) response).getMasterData();

            try {
                if (fastLaneDataEntity != null) {


                    acMake.setEnabled(true);


                    if (fastLaneDataEntity.getMake_Name() != "") {
                        acMake.setText("" + fastLaneDataEntity.getMake_Name());

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                acMake.dismissDropDown();
                            }
                        });

                    } else {
                        acMake.setText("");
                    }






                    acMake.setError(null);

                    IsMakeValid = true;

                    setAutoComplete();

                } else {
                    resetMakeModel();

                }
            } catch (Exception ex) {
                resetMakeModel();

            }
        }
        //
    }

    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
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
