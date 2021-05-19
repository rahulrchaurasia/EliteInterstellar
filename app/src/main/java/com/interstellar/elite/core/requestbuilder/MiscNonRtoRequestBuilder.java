package com.interstellar.elite.core.requestbuilder;



import com.interstellar.elite.RetroRequestBuilder;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class MiscNonRtoRequestBuilder extends RetroRequestBuilder {

    public MiscNonRtoNetworkService getService() {

        return super.build().create(MiscNonRtoNetworkService.class);
    }

    public interface MiscNonRtoNetworkService {

        @POST("/api/get-product-price")
        Call<ProductPriceResponse> getProductTAT(@Body ProductPriceRequestEntity entity);

        @POST("/api/get-motor-insurance")
        Call<MotorInsuranceListResponse> getMotorInsuranceList();

        @POST("/api/get-health-insurance")
        Call<MotorInsuranceListResponse> getHealthInsuranceList();


        @POST("/api/order-save-misc-claim-assi-service1")
        Call<ProvideClaimAssResponse> saveProvideClaimAssistance(@Body ProvideClaimAssRequestEntity requestEntity);

        @POST("/api/order-save-claim-guidance-hospi-service2")
        Call<ProvideClaimAssResponse> saveClaimGuidanceHosp(@Body ClaimGuidanceHospRequestEntity entity);

        @POST("/api/save-misc-remider-puc-service-3")
        Call<ProvideClaimAssResponse> saveMiscRemiderPUC(@Body MiscReminderPUCRequestEntity entity);

        @POST("/api/save-special-beanifiets-service-4")
        Call<ProvideClaimAssResponse> saveSpecialBenifits(@Body SpecialBenefitsRequestEntity entity);

        @POST("/api/save-transfer-ncb-benefits-service-5")
        Call<ProvideClaimAssResponse> saveTransferNCBBenefits(@Body TransferBenefitsNCBRequestEntity entity);

        @POST("/api/save-analysis-current-health-service-6")
        Call<ProvideClaimAssResponse> saveAnalysisCurrentHealth(@Body ExpertReviewOfCurrentHealthPolicyRequestEntity entity);

        @POST("/api/save-life-insurance-policy-nominee-service-7")
        Call<ProvideClaimAssResponse> saveLifeInsurancePolicyNominee(@Body LifeInsurancePolicyNomineeRequestEntity entity);

        @POST("/api/save-beyond-life-financial-service-8")
        Call<ProvideClaimAssResponse> saveBeyondLifeFinancial(@Body BeyondLifeFinancialRequestEntity entity);

        @POST("/api/save-complimentary-credit-report-service-9")
        Call<ProvideClaimAssResponse> saveComplimentaryCreditReport(@Body ComplimentaryCreditReportRequestEntity entity);

        @POST("/api/save-complimentary-loan-audit-service-10")
        Call<ProvideClaimAssResponse> saveComplimentaryLoanAudit(@Body ComplimentaryLoanAuditRequestEntity entity);


    }
}