package com.interstellar.elite.core.requestbuilder;



import com.interstellar.elite.RetroRequestBuilder;
import com.interstellar.elite.core.requestmodel.Rto.AdditionHypothecationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AddressEndorsementRCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AssistanceObtainingRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.DriverDLVerificationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.PaperToSmartCardRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.RCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.TransferOwnershipRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.VehicleRegCertificateRequestEntity;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RtoRequestBuilder extends RetroRequestBuilder {

    public RtoNetworkService getService() {

        return super.build().create(RtoNetworkService.class);
    }

    public interface RtoNetworkService {


        @POST("/api/order-save-rcservice1")
        Call<ProvideClaimAssResponse> saveRCservice1(@Body RCRequestEntity requestEntity);

        @POST("/api/order-save-dl-service2")
        Call<ProvideClaimAssResponse> saveAssistObtaining(@Body AssistanceObtainingRequestEntity requestEntity);

        @POST("/api/save-addition-hypothecation-service-3")
        Call<ProvideClaimAssResponse> saveAdditionHypothecation(@Body AdditionHypothecationRequestEntity entity);

        @POST("/api/save-transfer-ownership-service-4")
        Call<ProvideClaimAssResponse> saveTransferOwnership(@Body TransferOwnershipRequestEntity entity);

        @POST("/api/save-drivers-dl-verification-service-5")
        Call<ProvideClaimAssResponse> saveDriverDLVerification(@Body DriverDLVerificationRequestEntity entity);

        @POST("/api/save-address-endorsement-on-rc-service-6")
        Call<ProvideClaimAssResponse> saveAddressEndorsementRC(@Body AddressEndorsementRCRequestEntity entity);

        @POST("/api/save-paper-to-smart-card-licence-service-7")
        Call<ProvideClaimAssResponse> savePaperSmartCard(@Body PaperToSmartCardRequestEntity entity);

        @POST("/api/save-vehicle-registration-certificate-service-8")
        Call<ProvideClaimAssResponse> saveVehicleRegCertificate(@Body VehicleRegCertificateRequestEntity entity);

    }
}