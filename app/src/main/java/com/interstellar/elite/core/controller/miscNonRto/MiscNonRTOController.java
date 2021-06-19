package com.interstellar.elite.core.controller.miscNonRto;

import android.content.Context;


import com.interstellar.elite.core.BaseController;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.requestbuilder.MiscNonRtoRequestBuilder;
import com.interstellar.elite.core.requestmodel.miscNonRto.ClaimAssistanceHospitalizationRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.BeyondLifeFinancialRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ClaimGuidanceHospRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ComplimentaryCreditReportRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ComplimentaryLoanAuditRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ExpertReviewOfCurrentHealthPolicyRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.LifeInsurancePolicyNomineeRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.MiscReminderPUCRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProvideClaimAssRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.SpecialBenefitsRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.TransferBenefitsNCBRequestEntity;
import com.interstellar.elite.core.response.miscNonRto.MotorInsuranceListResponse;
import com.interstellar.elite.core.response.miscNonRto.ProductPriceResponse;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;
import com.interstellar.elite.facade.PrefManager;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 02/02/2018.
 */

public class MiscNonRTOController  extends BaseController implements INonRTO {

    MiscNonRtoRequestBuilder.MiscNonRtoNetworkService miscNonRtoNetworkService;
    Context mContext;
    PrefManager prefManager;
    IResponseSubcriber iResponseSubcriber;
   String MESSAGE = "Unable to connect to server, Please try again";
   String ERROR = "Server not responded, Try again later";

    public MiscNonRTOController(Context context) {
        miscNonRtoNetworkService = new MiscNonRtoRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
    }



    @Override
    public void saveTransferNCBBenefits(TransferBenefitsNCBRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveTransferNCBBenefits(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus_code() == 0) {
                            iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                        } else {
                            iResponseSubcriber.OnFailure(response.body().getMessage());
                        }
                    }else {

                        iResponseSubcriber.OnFailure(MESSAGE);
                    }
                }
                else {

                    iResponseSubcriber.OnFailure(errorStatus(String.valueOf(response.code())));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveBeyondLifeFinancial(BeyondLifeFinancialRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveBeyondLifeFinancial(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });

    }

    @Override
    public void saveComplimentaryCreditReport(ComplimentaryCreditReportRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveComplimentaryCreditReport(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveComplimentaryLoanAudit(ComplimentaryLoanAuditRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveComplimentaryLoanAudit(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void getHealthInsuranceList(final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.getHealthInsuranceList().enqueue(new Callback<MotorInsuranceListResponse>() {
            @Override
            public void onResponse(Call<MotorInsuranceListResponse> call, Response<MotorInsuranceListResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<MotorInsuranceListResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void getMotorInsuranceList(final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.getMotorInsuranceList().enqueue(new Callback<MotorInsuranceListResponse>() {
            @Override
            public void onResponse(Call<MotorInsuranceListResponse> call, Response<MotorInsuranceListResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<MotorInsuranceListResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveLifeInsurancePolicyNominee(LifeInsurancePolicyNomineeRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveLifeInsurancePolicyNominee(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveAnalysisCurrentHealth(ExpertReviewOfCurrentHealthPolicyRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        miscNonRtoNetworkService.saveAnalysisCurrentHealth(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveSpecialBenifits(SpecialBenefitsRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveSpecialBenifits(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveMiscRemiderPUC(MiscReminderPUCRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveMiscRemiderPUC(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveClaimGuidanceHosp(ClaimGuidanceHospRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveClaimGuidanceHosp(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void getProductTAT(ProductPriceRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.getProductTAT(entity).enqueue(new Callback<ProductPriceResponse>() {
            @Override
            public void onResponse(Call<ProductPriceResponse> call, Response<ProductPriceResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProductPriceResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

    @Override
    public void saveProvideClaimAssistance(ProvideClaimAssRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveProvideClaimAssistance(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }
}
