package com.interstellar.elite.core.requestbuilder;



import com.interstellar.elite.RetroRequestBuilder;
import com.interstellar.elite.core.requestmodel.RegisterRequest;
import com.interstellar.elite.core.response.CityMainResponse;
import com.interstellar.elite.core.response.CommonResponse;
import com.interstellar.elite.core.response.DisplayFeedbackResponse;
import com.interstellar.elite.core.response.DisplayRateResponse;
import com.interstellar.elite.core.response.FastLaneDataResponse;
import com.interstellar.elite.core.response.FeedbackResponse;
import com.interstellar.elite.core.response.LoginResponse;
import com.interstellar.elite.core.response.PincodeResponse;
import com.interstellar.elite.core.response.ProfileResponse;
import com.interstellar.elite.core.response.RateResponse;
import com.interstellar.elite.core.response.UpdateUserResponse;
import com.interstellar.elite.core.response.UserConsttantResponse;
import com.interstellar.elite.core.response.UserRegistrationResponse;
import com.interstellar.elite.core.response.VehicleMasterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RegisterRequestBuilder extends RetroRequestBuilder {

    public RegisterRequestBuilder.RegisterQuotesNetworkService getService() {

        return super.build().create(RegisterRequestBuilder.RegisterQuotesNetworkService.class);
    }

    public interface RegisterQuotesNetworkService {



        @POST("/api/add-by-pincode")
        Call<PincodeResponse> getCity(@Body HashMap<String, String> body);



        @POST("/api/change-password")
        Call<CommonResponse> changePassword(@Body HashMap<String, String> body);

        @POST("/api/forgot-password")
        Call<CommonResponse> forgotPassword(@Body HashMap<String, String> body);


        @POST("/api/update-customer-profile")
        Call<UserRegistrationResponse> userProfile(@Body RegisterRequest registerRequest);

        @POST("/api/get-user-profile")
        Call<ProfileResponse> getProfile(@Body HashMap<String, String> body);




        @GET("/api/get-city-rto")
        Call<CityMainResponse> getCityMainMaster();  //used

        //vehicle request

        @POST("/api/get-vehicle-data")
        Call<VehicleMasterResponse> getVehicleData();


        @GET
        Call<VehicleMasterResponse> getCarMaster(@Url String url);

        @POST("/api/save-feedback-form")
        Call<FeedbackResponse> saveFeedBack(@Body HashMap<String, String> body);

        @POST("/api/display-feedback-form")
        Call<DisplayFeedbackResponse> displayFeedBack(@Body HashMap<String, String> body);

        @POST("/api/save-rate")
        Call<RateResponse> saveRate(@Body RateRequestEntity rateRequestEntity);

        @POST("/api/display-rate")
        Call<DisplayRateResponse> dispalyRate(@Body HashMap<String, String> body);


        @POST
        @Headers("token:1234567890")
        Call<FastLaneDataResponse> getFastLaneData(@Url String strUrl, @Body HashMap<String, String> body);

    }
}