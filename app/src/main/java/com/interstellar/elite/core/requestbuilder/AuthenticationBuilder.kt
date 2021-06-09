package com.interstellar.elite.core.requestbuilder

import com.interstellar.elite.RetroRequestBuilder
import com.interstellar.elite.core.requestmodel.RegisterRequest
import com.interstellar.elite.core.requestmodel.RoadSideRequestEntity
import com.interstellar.elite.core.response.*
import com.squareup.okhttp.RequestBody

import retrofit2.Call
import retrofit2.http.*
import java.util.*

open class AuthenticationBuilder : RetroRequestBuilder() {

    fun getService(): AuthenticationBuilder.AuthenticationNetworkService {
        return super.build().create(AuthenticationBuilder.AuthenticationNetworkService::class.java)
    }

    interface AuthenticationNetworkService {


//        @POST("/api/generate-otp")
//        fun getnerateOTP(@Body body: HashMap<String, String>): Call<OTPResponse>
//
//        @POST("/api/validate-otp-login")
//        fun validateOTPLogin(@Body body: HashMap<String, String>): Call<RegisterValidateOTPResponse>
//
//        @POST("/crbl/1.0/validate/customer")
//        fun register(@Body body: HashMap<String, String>): Call<OTPValidResponse>
//
//
//        @POST("/api/validate-otp")
//        fun validateRegisterOTP(@Body body: HashMap<String, String>): Call<RegisterValidateOTPResponse>
//
//        @POST("/api/registration-success")
//        fun registrationSuccess(@Body body: RegisterCustomerEntity): Call<RegisterSuccessResponse>
//
//        @POST("/api/get-tnc-values")
//        fun getTermAndCondition(@Body body: HashMap<String, String>): Call<TNCResponse>

        @GET("/api/get-app-version")
        fun getVersionCode(): Call<VersionCodeResponse>

        @GET()
        fun getUserEligibility(@Url url: String, @Query("MobileNo") MobileNo: String? = null , @Query("RegistrationNo") RegistrationNo: String? = null ): Call<EligibilityUserResponse>

        @GET()
        fun getLandmarkEliteTataPee(@Url url: String, @Query("MobileNo") MobileNo: String? = null , @Query("RegistrationNo") RegistrationNo: String? = null ): Call<TataLandmarkResponse>

//        @GET()
//        fun getLandmarkEliteGlobalAssure(@Url url: String, @Query("MobileNo") MobileNo: String? = null , @Query("RegistrationNo") RegistrationNo: String? = null ): Call<GlobalAssureLandmarkResponse>

        @POST()
        fun getLandmarkEliteGlobalAssure(@Url url: String, @Body body: String) : Call<GlobalAssureLandmarkResponse>

        @POST()
        fun getLandmarkEliteGlobalAssureQuery(@Url url :String, @Body body: HashMap<String, String> ) : Call<GlobalAssureLandmarkResponse>


        @GET()
        fun getLandmarkEliteActivationCode(@Url url: String, @Query("MobileNo") MobileNo: String? = null , @Query("RegistrationNo") RegistrationNo: String? = null ): Call<ActivationCodeLandmarkResponse>

        @POST()
        fun getPolicyBossVehicleInfo(@Url url :String, @Body body: HashMap<String, String>): Call<PolicyBossVehicleInfoResponse>

        @POST("/api/get-user-constant")
        fun getUserConstant(@Body body: HashMap<String, String>): Call<UserConsttantResponse>

        @POST("/api/login")
        fun getLogin(@Body body: HashMap<String, String>): Call<LoginResponse>

        @POST("/api/update-is-gold")
        fun updateGolduser(@Body  body: HashMap<String, String>): Call<UpladeGoldResponse>

        @POST("/api/check-user-registration")
        fun verifyOTPTegistration(@Body body: HashMap<String, String>): Call<VerifyUserOTPRegisterResponse>

        @POST("/api/add-by-pincode")
        fun getCityState(@Body body: HashMap<String, String>): Call<PincodeResponse>

        @POST("/api/user-otp-verify")
        fun userRegistration(@Body registerRequest: RegisterRequest): Call<UserRegistrationResponse>

        @POST("/api/load-Tata-Peep")
        fun getTataPeep(@Body body: HashMap<String, String>): Call<TataResponse>

        @POST("/api/insertTataPeep")
        fun insertTataPeep(@Body  body: HashMap<String, String>): Call<CommonResponse>



        @POST("/api/insertGlobalAssure")
        fun insertGlobalAssure(@Body  body: HashMap<String, String>): Call<CommonResponse>

        @POST("/api/load-Global-Assure")
        fun getGlobalAssure(@Body body: HashMap<String, String>): Call<GlobalAssureResponse>


        @POST("/api/insert-activation-code")
        fun insertActivationCode(@Body  body: HashMap<String, String>): Call<CommonResponse>

        @POST("/api/load-activation-code")
        fun getActivationCode(@Body body: HashMap<String, String>): Call<ActivationCodeResponse>



    }
}