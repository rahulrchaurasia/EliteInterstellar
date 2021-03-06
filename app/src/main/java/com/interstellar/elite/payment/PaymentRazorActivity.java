package com.interstellar.elite.payment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.rto_service.RTOController;
import com.interstellar.elite.core.model.InsertOrderRequestEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.model.miscNonRto.ProvideClaimAssEntity;
import com.interstellar.elite.core.requestmodel.Rto.AdditionHypothecationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AddressEndorsementRCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AssistanceObtainingRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.DriverDLVerificationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.PaperToSmartCardRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.RCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.TransferOwnershipRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.VehicleRegCertificateRequestEntity;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;
import com.interstellar.elite.document.DocUploadActivity;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.utility.Constants;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class PaymentRazorActivity extends BaseActivity implements PaymentResultListener, IResponseSubcriber, View.OnClickListener {

    private static final String TAG = "RAZOR_PAYMRNT";
    int OrderID = 0;
    long AMOUNT_PAYMENT = 0;
    String PRODUCT_NAME = "";
    UserEntity loginEntity;
    PrefManager prefManager;
    TextView txtprdName, txtAmount,txtName ,txtSuccessMessage,txtpaymentstatus;
    Button btnSubmit,btnCancel,btnContinue, btnHomeContinue;
    ProvideClaimAssEntity provideClaimAssEntity;

    CardView cvBuy, cvSuccess, cvFailure;


    InsertOrderRequestEntity orderRequestEntity;

    String REQUEST_TYPE = "";
    // Service 1
    RCRequestEntity rcRequestEntity;
    AssistanceObtainingRequestEntity assistanceRequestEntity;
    AdditionHypothecationRequestEntity additionHypothecationRequestEntity;
    TransferOwnershipRequestEntity transferOwnershipRequestEntity;

    DriverDLVerificationRequestEntity driverDLRequestEntity;
    AddressEndorsementRCRequestEntity addressEndRequestEntity;
    PaperToSmartCardRequestEntity paperRequestEntity;
    VehicleRegCertificateRequestEntity vehicleRegRequestEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_razor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        prefManager = new PrefManager(PaymentRazorActivity.this);
        loginEntity = prefManager.getUserData();

        OrderID = 0;

        initialize();

        setListner();

        cvBuy.setVisibility(View.VISIBLE);
        cvSuccess.setVisibility(View.GONE);
        cvFailure.setVisibility(View.GONE);

       txtName.setText(""+loginEntity.getName());

        Bundle extras = getIntent().getBundleExtra(Constants.PAYMENT_REQUEST_BUNDLE);
        if (extras != null) {
             REQUEST_TYPE = extras.getString(Constants.REQUEST_TYPE, "");

            if(REQUEST_TYPE.equals("1"))
            {
                // Service 1
                rcRequestEntity  =  extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = rcRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(rcRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("2"))
            {
                // Service 2
                assistanceRequestEntity  =  extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = assistanceRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(assistanceRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);
            }else if(REQUEST_TYPE.equals("3"))
            {
                // Service 3
                additionHypothecationRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = additionHypothecationRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(additionHypothecationRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("4"))
            {
                // Service 4
                transferOwnershipRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = transferOwnershipRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(transferOwnershipRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("5"))
            {
                // Service 5
                driverDLRequestEntity   = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = driverDLRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(driverDLRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("6"))
            {
                // Service 6
                addressEndRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = addressEndRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(addressEndRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("7"))
            {
                // Service 7
                paperRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = paperRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(paperRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("8"))
            {
                // Service 8
                vehicleRegRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = vehicleRegRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(vehicleRegRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }

        }


        Checkout.preload(getApplicationContext());



    }

    // region init and listner

    private void initialize() {

        cvBuy = (CardView) findViewById(R.id.cvBuy);
        cvSuccess = (CardView) findViewById(R.id.cvSuccess);
        cvFailure = (CardView) findViewById(R.id.cvFailure);

        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnHomeContinue = (Button) findViewById(R.id.btnHomeContinue);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtprdName = (TextView) findViewById(R.id.txtprdName);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtName  = (TextView) findViewById(R.id.txtName);

        txtSuccessMessage = (TextView) findViewById(R.id.txtSuccessMessage);
        txtpaymentstatus = (TextView) findViewById(R.id.txtpaymentstatus);


    }

    private void setListner(){

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnHomeContinue.setOnClickListener(this);
    }

    //endregion

    // region Razorpay Method

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
       // co.setKeyID("rzp_live_DFxDFYDslN2DIq");           // we can add key here and in Manifest also


        co.setImage(R.drawable.elite_plus_black);


       // co.setFullScreenDisable(true);
        try {
            JSONObject options = new JSONObject();
            options.put("name", PRODUCT_NAME);
            options.put("description", ""+ String.valueOf(loginEntity.getUser_id()));
            //options.put("image", ContextCompat.getDrawable(this, R.drawable.elite_plus_black));
            options.put("theme.color", "#3F51B5");

            options.put("currency", "INR");
            options.put("amount", AMOUNT_PAYMENT * 100);
           //  options.put("amount", "100");  // 05 temp added  for 1rs


//            JSONObject checkout = new JSONObject();
//            JSONObject method = new JSONObject();
//            method.put("netbanking", 1);
//            method.put("card", 0);
//            checkout.put("checkout",method);

            JSONObject preFill = new JSONObject();
            preFill.put("email", loginEntity.getEmail());
            preFill.put("contact", loginEntity.getMobile());


            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public void ServiceAfterPayment(String razorpayPaymentID){

        try {

            if(REQUEST_TYPE.equals("1"))
            {
                // Service 1
                rcRequestEntity.setPayment_status("1");
                rcRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveRCService1(rcRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("2"))
            {
                // Service 2
                assistanceRequestEntity.setPayment_status("1");
                assistanceRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveAssistanceObtaining(assistanceRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("3"))
            {
                // Service 3
                additionHypothecationRequestEntity.setPayment_status("1");
                additionHypothecationRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveAdditionHypothecation(additionHypothecationRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("4"))
            {
                // Service 4
                transferOwnershipRequestEntity.setPayment_status("1");
                transferOwnershipRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveTransferOwnership(transferOwnershipRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("5"))
            {
                // Service 5
                driverDLRequestEntity.setPayment_status("1");
                driverDLRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveDriverDLVerification(driverDLRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("6"))
            {
                // Service 6
                addressEndRequestEntity.setPayment_status("1");
                addressEndRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveAddressEndorsementRC(addressEndRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("7"))
            {
                // Service 7
                paperRequestEntity.setPayment_status("1");
                paperRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).savePaperSmartCard(paperRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("8"))
            {
                // Service 7
                vehicleRegRequestEntity.setPayment_status("1");
                vehicleRegRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveVehicleRegCertificate(vehicleRegRequestEntity,this);
            }



            //vehicleRegRequestEntity



        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    //endregion

    // region Event

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {


        ServiceAfterPayment(razorpayPaymentID);

    }

    @Override
    public void onPaymentError(int code, String response) {

        // 05 temp below commented
        try {

            cvFailure.setVisibility(View.VISIBLE);
            cvBuy.setVisibility(View.GONE);
            cvSuccess.setVisibility(View.GONE);

            txtpaymentstatus.setText("FAILED");

            Log.d(TAG, "Payment failed: " + code + " " + response);
        } catch (Exception e) {
            Log.d(TAG, "Exception in onPaymentError " + e.toString());
        }


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSubmit) {

            startPayment();

        }
        else if (view.getId() == R.id.btnCancel) {

            PaymentRazorActivity.this.finish();
        } else if (view.getId() == R.id.btnContinue) {

            PaymentRazorActivity.this.finish();
            if(provideClaimAssEntity != null) {
                startActivity(new Intent(PaymentRazorActivity.this, DocUploadActivity.class)
                        .putExtra("ORDER_ID", provideClaimAssEntity.getId()));
            }


        } else if (view.getId() == R.id.btnHomeContinue) {
            PaymentRazorActivity.this.finish();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ProvideClaimAssResponse) {
            if (response.getStatus_code() == 0) {

               // showPaymentAlert(btnSubmit, response.getMessage().toString(),((ProvideClaimAssResponse) response).getData().get(0));

                provideClaimAssEntity = ((ProvideClaimAssResponse) response).getData().get(0);
                cvBuy.setVisibility(View.GONE);

                cvFailure.setVisibility(View.GONE);
                cvSuccess.setVisibility(View.VISIBLE);

                txtSuccessMessage.setText(provideClaimAssEntity.getDisplaymessage());

            }

        }
    }

    @Override
    public void OnFailure(String error) {

        cancelDialog();
    }

    // endregion




    public void showPaymentAlert(final View view, String strhdr, final ProvideClaimAssEntity provideClaimAssEntity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentRazorActivity.this, R.style.CustomDialog);

        Button btnClose;
        TextView txtHdr ,txtMessage;
        LinearLayout lyReceipt;

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_success_message, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose =  dialogView.findViewById(R.id.btnClose);
        txtMessage =  dialogView.findViewById(R.id.txtMessage);
        txtHdr =  dialogView.findViewById(R.id.txtHdr);

        lyReceipt =    dialogView.findViewById(R.id.lyReceipt);
        txtHdr.setText(""+ strhdr);
        txtMessage.setText("" + provideClaimAssEntity.getDisplaymessage());

        lyReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(provideClaimAssEntity.getReceipt() != null)
                {
                  // new DownloadFromUrl(provideClaimAssEntity.getReceipt(), "EliteReceipt").execute();
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(provideClaimAssEntity.getReceipt()));
                    startActivity(intent);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(PaymentRazorActivity.this, DocUploadActivity.class)
                        .putExtra("ORDER_ID", provideClaimAssEntity.getId()));

                PaymentRazorActivity.this.finish();


            }
        });


        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }
}
