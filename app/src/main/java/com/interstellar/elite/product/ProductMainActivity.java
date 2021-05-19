package com.interstellar.elite.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.assure.PUCExpiryRenewalFragment;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.product.ProductController;
import com.interstellar.elite.core.model.DashProductEntity;
import com.interstellar.elite.core.response.ProductDocumentResponse;
import com.interstellar.elite.rto.DrivingLicenseFragment;
import com.interstellar.elite.rto.HypotheticalFragment;
import com.interstellar.elite.rto.RenewRcFragment;
import com.interstellar.elite.rto.SmartCardLicFragment;
import com.interstellar.elite.rto.TransferOwnershipFragment;
import com.interstellar.elite.secure.ClaimAssistHospitalFragment;
import com.interstellar.elite.secure.ExpertReviewOFCurrentHealthPolicyFragment;
import com.interstellar.elite.secure.NomineeChangeExistingPolicyFragment;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.webview.CommonWebViewActivity;
import com.interstellar.elite.webview.WebViewHtmlActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProductMainActivity extends BaseActivity implements IResponseSubcriber {

    String SERVICE_TYPE;
    DashProductEntity productEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (getIntent().hasExtra(Constants.SERVICE_TYPE)) {
                SERVICE_TYPE = extras.getString(Constants.SERVICE_TYPE, "");
            }
            if (getIntent().hasExtra(Constants.RTO_PRODUCT_DATA)) {
                productEntity = extras.getParcelable(Constants.RTO_PRODUCT_DATA);
            } else if (getIntent().hasExtra(Constants.NON_RTO_PRODUCT_DATA)) {
                productEntity = extras.getParcelable(Constants.NON_RTO_PRODUCT_DATA);
            } else if (getIntent().hasExtra(Constants.SUB_PRODUCT_DATA)) {
                productEntity = extras.getParcelable(Constants.SUB_PRODUCT_DATA);
            }

            if (productEntity != null)

                if(productEntity.getProductCode().equals("500")  || productEntity.getProductCode().equals("501")  ){

                    loadWeb(productEntity);
                }else {
                    loadFragments(getFragmentFromProduct(productEntity));
                }

            else
                getCustomToast("Under construction..");


        }

    }

    public void loadWeb(DashProductEntity productEntity){

           if (productEntity.getProductCode().equalsIgnoreCase("500")) {

            Intent intent = new Intent(this, WebViewHtmlActivity.class);
            intent.putExtra("title", "Finpeace");
            intent.putExtra("type", "FINPEACE");
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        }
        else if (productEntity.getProductCode().equalsIgnoreCase("501")) {
            Intent intent = new Intent(this, WebViewHtmlActivity.class);
            intent.putExtra("title", "Pit Stop ");
            intent.putExtra("type", "PIT");
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    private Bundle getBundleRTO() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SUB_PRODUCT_DATA, productEntity);
        return bundle;
    }

    public void getProducDoc(int PRODUCT_ID) {

        showDialog();
        new ProductController(this).getProducDoc(PRODUCT_ID, ProductMainActivity.this);
    }


    private Fragment getFragmentFromProduct(DashProductEntity productEntity) {


        if ((productEntity.getProductCode().equalsIgnoreCase("1.1"))
                || (productEntity.getProductCode().equalsIgnoreCase("1.2"))
                || (productEntity.getProductCode().equalsIgnoreCase("1.3"))) {
            RenewRcFragment rcFragment = new RenewRcFragment();
            rcFragment.setArguments(getBundleRTO());
            return rcFragment;

        } else if ((productEntity.getProductCode().equalsIgnoreCase("3.1"))
                || (productEntity.getProductCode().equalsIgnoreCase("3.2"))) {
            HypotheticalFragment hypotheticalFragment = new HypotheticalFragment();
            hypotheticalFragment.setArguments(getBundleRTO());
            return hypotheticalFragment;
        } else if ((productEntity.getProductCode().equalsIgnoreCase("4.1"))) {
            TransferOwnershipFragment transferVehicleFragment = new TransferOwnershipFragment();
            transferVehicleFragment.setArguments(getBundleRTO());
            return transferVehicleFragment;
        }else if ((productEntity.getProductCode().equalsIgnoreCase("2.1"))
                || (productEntity.getProductCode().equalsIgnoreCase("2.2"))
                || (productEntity.getProductCode().equalsIgnoreCase("2.3"))
                || (productEntity.getProductCode().equalsIgnoreCase("2.4"))) {
            DrivingLicenseFragment obtainFragment = new DrivingLicenseFragment();
            obtainFragment.setArguments(getBundleRTO());
            return obtainFragment;
        } else if ((productEntity.getProductCode().equalsIgnoreCase("5"))) {
            DrivingLicenseFragment drivingLicenseFragment = new DrivingLicenseFragment();
            drivingLicenseFragment.setArguments(getBundleRTO());
            return drivingLicenseFragment;
        }else if ((productEntity.getProductCode().equalsIgnoreCase("7"))) {
            SmartCardLicFragment smartCardLicFragment = new SmartCardLicFragment();
            smartCardLicFragment.setArguments(getBundleRTO());
            return smartCardLicFragment;
        }
        //Secure

        //region secure
       else if (productEntity.getProductCode().equalsIgnoreCase("10")) {
            ClaimAssistHospitalFragment claimAssistanceHospitalizationFragment = new ClaimAssistHospitalFragment();
            claimAssistanceHospitalizationFragment.setArguments(getBundleRTO());
            return claimAssistanceHospitalizationFragment;
        }else if (productEntity.getProductCode().equalsIgnoreCase("14")) {
            ExpertReviewOFCurrentHealthPolicyFragment analysisHealthPlanFragment = new ExpertReviewOFCurrentHealthPolicyFragment();
            analysisHealthPlanFragment.setArguments(getBundleRTO());
            return analysisHealthPlanFragment;

        }else if (productEntity.getProductCode().equalsIgnoreCase("15")) {
            NomineeChangeExistingPolicyFragment licChangeAssistanceFragment = new NomineeChangeExistingPolicyFragment();
            licChangeAssistanceFragment.setArguments(getBundleRTO());
            return licChangeAssistanceFragment;
        }
        //endregion

        //region Assure


        else if (productEntity.getProductCode().equalsIgnoreCase("11")) {
            PUCExpiryRenewalFragment pucFragment = new PUCExpiryRenewalFragment();
            pucFragment.setArguments(getBundleRTO());
            return pucFragment;
        }
        else if (productEntity.getProductCode().equalsIgnoreCase("13")) {
            NomineeChangeExistingPolicyFragment licChangeAssistanceFragment = new NomineeChangeExistingPolicyFragment();
            licChangeAssistanceFragment.setArguments(getBundleRTO());
            return licChangeAssistanceFragment;
        }
        //endregion

             return null;
        }





    //region load fragment

    private void loadFragments(Fragment fragment) {

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.commitAllowingStateLoss();
        } else {
            getCustomToast("Under Construction...");
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();

        if (response instanceof ProductDocumentResponse) {

            if (response.getStatus_code() == 0) {

                if (((ProductDocumentResponse) response).getData() != null) {
                    reqDocPopUp(((ProductDocumentResponse) response).getData());
                } else {

                    getCustomToast("No Data Available");
                }
            }
        }
    }

    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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

    //endregion
}
