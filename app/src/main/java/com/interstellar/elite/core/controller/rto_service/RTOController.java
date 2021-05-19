package com.interstellar.elite.core.controller.rto_service;

import android.content.Context;


import com.interstellar.elite.core.BaseController;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.requestbuilder.RtoRequestBuilder;
import com.interstellar.elite.core.requestmodel.Rto.AdditionHypothecationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AddressEndorsementRCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AssistanceObtainingRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.DriverDLVerificationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.PaperToSmartCardRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.RCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.TransferOwnershipRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.VehicleRegCertificateRequestEntity;
import com.interstellar.elite.core.response.miscNonRto.ProvideClaimAssResponse;
import com.interstellar.elite.facade.PrefManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 02/02/2018.
 */

public class RTOController extends BaseController implements IRTO {


    RtoRequestBuilder.RtoNetworkService rtoNetworkService;
    Context mContext;
    PrefManager prefManager;
    IResponseSubcriber iResponseSubcriber;
    String MESSAGE = "Unable to connect to server, Please try again";
    String ERROR = "Server not responded, Try again later";

    public RTOController(Context context) {
        rtoNetworkService = new RtoRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
    }

    @Override
    public void saveVehicleRegCertificate(VehicleRegCertificateRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        rtoNetworkService.saveVehicleRegCertificate(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveTransferOwnership(TransferOwnershipRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        rtoNetworkService.saveTransferOwnership(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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
                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });
    }

    @Override
    public void savePaperSmartCard(PaperToSmartCardRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        rtoNetworkService.savePaperSmartCard(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveDriverDLVerification(DriverDLVerificationRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveDriverDLVerification(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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
                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveAddressEndorsementRC(AddressEndorsementRCRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAddressEndorsementRC(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveAdditionHypothecation(AdditionHypothecationRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAdditionHypothecation(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveRCService1(RCRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveRCservice1(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

                iResponseSubcriber.OnFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAssistObtaining(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

                iResponseSubcriber.OnFailure(t.getMessage());

            }
        });

    }
}
