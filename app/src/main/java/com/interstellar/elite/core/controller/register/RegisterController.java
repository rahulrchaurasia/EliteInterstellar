package com.interstellar.elite.core.controller.register;

import android.content.Context;


import com.interstellar.elite.core.BaseController;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.requestbuilder.RateRequestEntity;
import com.interstellar.elite.core.requestbuilder.RegisterRequestBuilder;
import com.interstellar.elite.core.requestmodel.RegisterRequest;
import com.interstellar.elite.core.response.CityMainResponse;
import com.interstellar.elite.core.response.CommonResponse;
import com.interstellar.elite.core.response.DisplayFeedbackResponse;
import com.interstellar.elite.core.response.DisplayRateResponse;
import com.interstellar.elite.core.response.FastLaneDataResponse;
import com.interstellar.elite.core.response.FeedbackResponse;
import com.interstellar.elite.core.response.PincodeResponse;
import com.interstellar.elite.core.response.ProfileResponse;
import com.interstellar.elite.core.response.RateResponse;
import com.interstellar.elite.core.response.UserRegistrationResponse;
import com.interstellar.elite.core.response.VehicleMasterResponse;
import com.interstellar.elite.facade.PrefManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 02/02/2018.
 */

public class RegisterController  extends BaseController implements IRegister {

    RegisterRequestBuilder.RegisterQuotesNetworkService registerQuotesNetworkService;
    Context mContext;
    UserEntity loginEntity;
    PrefManager prefManager;

    String MESSAGE = "Unable to connect to server, Please try again";
    String ERROR = "Server not responded, Try again later";

    public RegisterController(Context context) {
        registerQuotesNetworkService = new RegisterRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
        loginEntity = prefManager.getUserData();
    }

//    @Override
//    public void getDbVersion(final IResponseSubcriber iResponseSubcriber) {
//
//        registerQuotesNetworkService.getDBVersion().enqueue(new Callback<DBVersionRespone>() {
//            @Override
//            public void onResponse(Call<DBVersionRespone> call, Response<DBVersionRespone> response) {
//                if (response.body() != null) {
//
//                    if (response.body().getStatus_code() == 0) {
//                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
//                    } else {
//                        //failure
//                        iResponseSubcriber.OnFailure(response.body().getMessage());
//                    }
//
//                } else {
//                    //failure
//                    iResponseSubcriber.OnFailure(MESSAGE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DBVersionRespone> call, Throwable t) {
//                if (t instanceof ConnectException) {
//                    iResponseSubcriber.OnFailure(t);
//                } else if (t instanceof SocketTimeoutException) {
//                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
//                } else if (t instanceof UnknownHostException) {
//                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
//                } else if (t instanceof NumberFormatException) {
//                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
//                } else {
//                    iResponseSubcriber.OnFailure(new RuntimeException("" + t.getMessage()));
//                }
//            }
//        });
//    }


//    @Override
//    public void addUser(AddUserRequestEntity addUserRequestEntity, final IResponseSubcriber iResponseSubcriber) {
//        registerQuotesNetworkService.addUser(addUserRequestEntity).enqueue(new Callback<AddUserResponse>() {
//            @Override
//            public void onResponse(Call<AddUserResponse> call, Response<AddUserResponse> response) {
//                if (response.body() != null) {
//
//                    if (response.body().getStatus_code() == 0) {
//                        iResponseSubcriber.OnSuccess(response.body(), "");
//                    } else {
//                        //failure
//                        iResponseSubcriber.OnFailure(response.body().getMessage());
//                    }
//
//                } else {
//                    //failure
//                    iResponseSubcriber.OnFailure(MESSAGE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddUserResponse> call, Throwable t) {
//                if (t instanceof ConnectException) {
//                    iResponseSubcriber.OnFailure(t);
//                } else if (t instanceof SocketTimeoutException) {
//                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
//                } else if (t instanceof UnknownHostException) {
//                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
//                } else if (t instanceof NumberFormatException) {
//                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
//                } else {
//                    iResponseSubcriber.OnFailure(new RuntimeException("" + t.getMessage()));
//                }
//            }
//        });
//    }


    @Override
    public void getCityState(String pincode, final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("pincode", pincode);
        registerQuotesNetworkService.getCity(body).enqueue(new Callback<PincodeResponse>() {
            @Override
            public void onResponse(Call<PincodeResponse> call, Response<PincodeResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<PincodeResponse> call, Throwable t) {

                if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }

            }
        });
    }

  

    @Override
    public void changePassword(String mobile, String curr_password, String new_password, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("mobile", mobile);
        body.put("current_password", curr_password);
        body.put("new_password", new_password);
        body.put("confirm_password", new_password);
        body.put("type", "1");

        registerQuotesNetworkService.changePassword(body).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void forgotPassword(String mobile, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("mobile", mobile);
        body.put("type", "1");

        registerQuotesNetworkService.forgotPassword(body).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }



    @Override
    public void saveUserProfile(RegisterRequest registerRequest, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.userProfile(registerRequest).enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void getUserProfile(final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("user_id", String.valueOf(loginEntity.getUser_id()));

        registerQuotesNetworkService.getProfile(body).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }




    @Override
    public void getCityMainMaster(final IResponseSubcriber iResponseSubcriber) {


        registerQuotesNetworkService.getCityMainMaster().enqueue(new Callback<CityMainResponse>() {
            @Override
            public void onResponse(Call<CityMainResponse> call, Response<CityMainResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        new PrefManager(mContext).storeCityData(response.body().getData());
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null) {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<CityMainResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
                     if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
                }
            }
        });
    }

    @Override
    public void getCarVehicleMaster(final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.getVehicleData().enqueue(new Callback<VehicleMasterResponse>() {
            @Override
            public void onResponse(Call<VehicleMasterResponse> call, Response<VehicleMasterResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        if (response.body().getData().getVehicleMasterResult() != null) {

                            new PrefManager(mContext).storeVehicle(response.body().getData().getVehicleMasterResult());

                            if (iResponseSubcriber != null)
                                iResponseSubcriber.OnSuccess(response.body(), "");
                        }
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null) {
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<VehicleMasterResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
                     if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
                }
            }
        });


    }

    @Override
    public void saveFeedBack(String reqId, String userID, String feedback, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("request_id", String.valueOf(reqId));
        body.put("userid", String.valueOf(userID));
        body.put("feedback_comment", String.valueOf(feedback));


        registerQuotesNetworkService.saveFeedBack(body).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void displayFeedBack(int user_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("user_id", String.valueOf(user_id));


        registerQuotesNetworkService.displayFeedBack(body).enqueue(new Callback<DisplayFeedbackResponse>() {
            @Override
            public void onResponse(Call<DisplayFeedbackResponse> call, Response<DisplayFeedbackResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<DisplayFeedbackResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void saveRate(RateRequestEntity rateRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.saveRate(rateRequestEntity).enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(response.body().getMessage());
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void displayRate(int userid, String request_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("request_id", String.valueOf(request_id));
        body.put("userid", String.valueOf(userid));

        registerQuotesNetworkService.dispalyRate(body).enqueue(new Callback<DisplayRateResponse>() {
            @Override
            public void onResponse(Call<DisplayRateResponse> call, Response<DisplayRateResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<DisplayRateResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }

    @Override
    public void getVechileDetails(String RegistrationNumber, final IResponseSubcriber iResponseSubcriber) {

        //  String url1 = "http://api.rupeeboss.com/BankAPIService.svc/GetCitywiseBankList?City_Id=" + cityid+"&Product_Id="+Productid;

        String url = "http://api.magicfinmart.com/api/vehicle-info";

        HashMap<String, String> body = new HashMap<>();

        body.put("RegistrationNumber", RegistrationNumber);

        registerQuotesNetworkService.getFastLaneData(url, body).enqueue(new Callback<FastLaneDataResponse>() {
            @Override
            public void onResponse(Call<FastLaneDataResponse> call, Response<FastLaneDataResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<FastLaneDataResponse> call, Throwable t) {
                 if (t instanceof UnknownHostException) {

                    iResponseSubcriber.OnFailure("Check your internet connection");
                }else{
                    iResponseSubcriber.OnFailure("" + t.getMessage());
                }
            }
        });
    }


}
