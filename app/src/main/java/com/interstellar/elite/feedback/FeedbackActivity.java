package com.interstellar.elite.feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.product.ProductController;
import com.interstellar.elite.core.controller.register.RegisterController;
import com.interstellar.elite.core.model.CompleteOrderEntity;
import com.interstellar.elite.core.model.FeedBackDisplayEntity;
import com.interstellar.elite.core.model.OrderDetailEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.response.CompleteOrderResponse;
import com.interstellar.elite.core.response.DisplayFeedbackResponse;
import com.interstellar.elite.core.response.FeedbackResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber, ICompOrderData {

    // etBody   btnSubmit
    EditText etBody, etReqName;
    TextView txtReqestID, textRequestID, txtServiceName, txtHdr;
    Button btnSubmit;

    RecyclerView rvRTO;
    ImageView ivCross;
    OrderDetailEntity orderDetailEntity;
    CompleteOrderEntity completeOrderEntity;
    ComplOrderAdapter feedbackAdapter;
    List<CompleteOrderEntity> lstCompOrderDetail;
    UserEntity loginEntity;
    LinearLayout lyReqID;

    AlertDialog alertDialog;
    int OrderId;
    PrefManager prefManager;

    // region Botom sheetDeclaration
    BottomSheetDialog mBottomSheetDialog;
    List<FeedBackDisplayEntity> feedBackDisplayEntityList;
    FeedBackHistoryAdapter feedBackHistoryAdapter;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        lstCompOrderDetail = new ArrayList<CompleteOrderEntity>();

        initialize();

        setOnClickListener();

        if (getIntent().hasExtra(Constants.FEEDBACK_DATA)) {
            if (getIntent().getExtras().getParcelable(Constants.FEEDBACK_DATA) != null) {
                lyReqID.setVisibility(View.GONE);
                // cvRating.setVisibility(View.GONE);
                txtServiceName.setVisibility(View.VISIBLE);
                orderDetailEntity = getIntent().getExtras().getParcelable(Constants.FEEDBACK_DATA);
                textRequestID.setText("Request ID: ");
//                txtReqestID.setText("" + orderDetailEntity.getDisplay_order_id());    // temp commented
                txtReqestID.setText("" + orderDetailEntity.getOrder_id());
                txtServiceName.setText("" + orderDetailEntity.getProduct_name());
                OrderId = orderDetailEntity.getOrder_id();
                showDialog();
                new RegisterController(this).displayFeedBack(loginEntity.getUser_id(), this);

            }
        } else {
            txtServiceName.setVisibility(View.GONE);
            lyReqID.setVisibility(View.VISIBLE);
            // cvRating.setVisibility(View.VISIBLE);
            textRequestID.setText("Select Request ID: ");


            showDialog();
            new ProductController(this).getCompleteOrderData(loginEntity.getUser_id(), this);
        }


    }

    private void initialize() {

        lyReqID = findViewById(R.id.lyReqID);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtReqestID = findViewById(R.id.txtReqestID);
        textRequestID = findViewById(R.id.textRequestID);
        etBody = findViewById(R.id.etBody);
        etReqName = findViewById(R.id.etReqName);

        txtServiceName = findViewById(R.id.txtServiceName);


    }


    public void getBottomSheetDialog() {


        if (feedBackDisplayEntityList != null && feedBackDisplayEntityList.size() > 0) {


            mBottomSheetDialog = new BottomSheetDialog(this, R.style.bottomSheetDialog);

            View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_feedback, null);

            mBottomSheetDialog.setContentView(sheetView);
            TextView txtHdr = mBottomSheetDialog.findViewById(R.id.txtHdr);
            RecyclerView rvRTO = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rvRTO);
            ImageView ivCross = (ImageView) mBottomSheetDialog.findViewById(R.id.ivCross);

            rvRTO.setLayoutManager(new LinearLayoutManager(this));
            rvRTO.setHasFixedSize(true);
            feedBackHistoryAdapter = new FeedBackHistoryAdapter(this, feedBackDisplayEntityList);
            rvRTO.setAdapter(feedBackHistoryAdapter);

            rvRTO.setVisibility(View.VISIBLE);


            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mBottomSheetDialog.isShowing()) {

                        mBottomSheetDialog.dismiss();
                    }
                }
            });


            mBottomSheetDialog.setCancelable(false);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            mBottomSheetDialog.show();
        } else {
            getCustomToast("No Data Available");
        }


    }


    private void setOnClickListener() {
        etReqName.setFocusable(false);
        etReqName.setClickable(true);

        etReqName.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    public void requestPopUp(String Title) {
        if (alertDialog != null && alertDialog.isShowing()) {

            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        TextView txtHdr;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.feedback_dialog, null);

        builder.setView(dialogView);
        alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtHdr = dialogView.findViewById(R.id.txtHdr);
        RecyclerView rvRTO = dialogView.findViewById(R.id.rvRTO);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        rvRTO.setLayoutManager(new LinearLayoutManager(this));
        rvRTO.setHasFixedSize(true);
        rvRTO.setNestedScrollingEnabled(false);
        feedbackAdapter = new ComplOrderAdapter(FeedbackActivity.this, lstCompOrderDetail, this);
        rvRTO.setAdapter(feedbackAdapter);
        rvRTO.setVisibility(View.VISIBLE);
        txtHdr.setText(Title);


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }


    private boolean validate() {

        if (!isEmpty(etReqName) && lyReqID.getVisibility() == View.VISIBLE) {
            etReqName.requestFocus();
            etReqName.setError("Select Request Id");
            return false;
        } else if (!isEmpty(etBody)) {
            etBody.requestFocus();
            etBody.setError("Enter Feedback");
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, FeedbackActivity.this);
        switch (view.getId()) {
            case R.id.btnSubmit:


                if (validate() == false) {
                    return;
                } else {

                    showDialog();

                    new RegisterController(this).saveFeedBack("" + OrderId, String.valueOf(loginEntity.getUser_id()), etBody.getText().toString().trim(), this);
                }

                break;

            case R.id.etReqName:
                if (lstCompOrderDetail.size() > 0) {
                    etReqName.setError(null);
                    requestPopUp("Select Request");
                }

                break;


        }


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof CompleteOrderResponse) {
            if (response.getStatus_code() == 0) {

                lstCompOrderDetail = ((CompleteOrderResponse) response).getData();
                new RegisterController(this).displayFeedBack(loginEntity.getUser_id(), this);

            }
        } else if (response instanceof FeedbackResponse) {
            if (response.getStatus_code() == 0) {

                etBody.setText("");
                new RegisterController(this).displayFeedBack(loginEntity.getUser_id(), this);
                getCustomToast(((FeedbackResponse) response).getMessage());


            }
        } else if (response instanceof DisplayFeedbackResponse) {
            if (response.getStatus_code() == 0) {

                feedBackDisplayEntityList = ((DisplayFeedbackResponse) response).getData();

            }
        }
    }

    @Override
    public void OnFailure(String error) {

        cancelDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_feedback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_view:
                getBottomSheetDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // finish();
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public void getOrderData(CompleteOrderEntity tempcompleteOrderEntity) {

        if (alertDialog != null)
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
                completeOrderEntity = tempcompleteOrderEntity;
                txtReqestID.setText("" + completeOrderEntity.getDisplay_order_id());
                etReqName.setText(completeOrderEntity.getProduct_name());

                OrderId = completeOrderEntity.getOrder_id();

            }
    }
}
