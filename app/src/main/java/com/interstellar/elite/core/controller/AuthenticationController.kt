package com.interstellar.elite.core.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.interstellar.elite.core.BaseController
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.requestbuilder.AuthenticationBuilder
import com.interstellar.elite.core.requestmodel.RegisterRequest
import com.interstellar.elite.core.requestmodel.RoadSideRequestEntity
import com.interstellar.elite.core.response.*
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.utility.Constants
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException


open class AuthenticationController(mCxt: Context) : BaseController(), IAuthentication {

    internal fun printLog(log: String) = Log.d("---log", log)
    private val MESSAGE = "Unable to connect to server, Please try again"

    var authenticationBuilder: AuthenticationBuilder.AuthenticationNetworkService
    var mContext: Context
    //var loginEntity: UserEntity
   // var prefManager: PrefManager
    init {
        authenticationBuilder = AuthenticationBuilder().getService()
        mContext = mCxt

       //prefManager = PrefManager(mContext)
        //loginEntity = prefManager.getUserData()!!


    }



    override fun getUserEligibility(
        MobileNo: String,
        RegistrationNo: String,
        iResponseSubcriber: IResponseSubcriber
    ) {
        var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/EliteEligibilityCheck"



        authenticationBuilder.getUserEligibility(url, MobileNo, RegistrationNo).enqueue(object :
            Callback<EligibilityUserResponse> {
            override fun onResponse(
                call: Call<EligibilityUserResponse>,
                response: Response<EligibilityUserResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()!!.EliteEligibilityCheckResult.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<EligibilityUserResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun getLandmarkEliteTataPee(
        MobileNo: String,
        RegistrationNo: String,
        iResponseSubcriber: IResponseSubcriber
    ) {

        var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/EliteTataPeep"



        authenticationBuilder.getLandmarkEliteTataPee(url, MobileNo, RegistrationNo).enqueue(object :
            Callback<TataLandmarkResponse> {
            override fun onResponse(
                call: Call<TataLandmarkResponse>,
                response: Response<TataLandmarkResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()!!.EliteTataPeepResult.EliteTataPeepdetails != null) {

                            iResponseSubcriber.OnSuccess(response.body()!!, "")
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, "")
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<TataLandmarkResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun getLandmarkEliteGlobalAssure(
        strBody: String,
        iResponseSubcriber: IResponseSubcriber
    ) {
        //  Old ONE
        // var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/GlobalAssure"

        var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/InsertOtherCustDetailsForGlobalAssure"



        authenticationBuilder.getLandmarkEliteGlobalAssure(url, strBody ).enqueue(
            object :
                Callback<GlobalAssureLandmarkResponse> {
                override fun onResponse(
                    call: Call<GlobalAssureLandmarkResponse>,
                    landmarkResponse: Response<GlobalAssureLandmarkResponse>
                ) {

                    if (landmarkResponse!!.isSuccessful) {

                        printLog(Gson().toJson(landmarkResponse.body()))

                        if (landmarkResponse.body() != null) {

                            // default success
                            iResponseSubcriber.OnSuccess(landmarkResponse.body()!!, "")

                        } else {

                            iResponseSubcriber.OnFailure(MESSAGE)
                        }
                    } else {
                        iResponseSubcriber.OnFailure(
                            errorStatus(
                                landmarkResponse.code().toString()
                            )
                        )
                    }

                }

                override fun onFailure(call: Call<GlobalAssureLandmarkResponse>, t: Throwable) {
                     if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
                }
            })
    }

    override fun getLandmarkEliteGlobalAssureQuery(
        strBody: String,
        iResponseSubcriber: IResponseSubcriber
    ) {


        var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/InsertOtherCustDetailsForGlobalAssure?js="

        url = url + strBody
        Log.i("MYDATA",url.toString())


        var map = hashMapOf<String, String>()
        map.put("Mobile", "")

        authenticationBuilder.getLandmarkEliteGlobalAssureQuery(url, map ).enqueue(
            object :
                Callback<GlobalAssureLandmarkResponse> {
                override fun onResponse(
                    call: Call<GlobalAssureLandmarkResponse>,
                    landmarkResponse: Response<GlobalAssureLandmarkResponse>
                ) {

                    if (landmarkResponse!!.isSuccessful) {

                        printLog(Gson().toJson(landmarkResponse.body()))

                        if (landmarkResponse.body() != null) {

                            // default success
                            iResponseSubcriber.OnSuccess(landmarkResponse.body()!!, "")

                        } else {

                            iResponseSubcriber.OnFailure(MESSAGE)
                        }
                    } else {
                        iResponseSubcriber.OnFailure(
                            errorStatus(
                                landmarkResponse.code().toString()
                            )
                        )
                    }

                }

                override fun onFailure(call: Call<GlobalAssureLandmarkResponse>, t: Throwable) {
                     if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
                }
            })
    }

    override fun getLandmarkEliteActivationCode(
        MobileNo: String,
        RegistrationNo: String,
        iResponseSubcriber: IResponseSubcriber
    ) {
        var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/EliteActivationCode"


        authenticationBuilder.getLandmarkEliteActivationCode(url, MobileNo, RegistrationNo).enqueue(
            object :
                Callback<ActivationCodeLandmarkResponse> {
                override fun onResponse(
                    call: Call<ActivationCodeLandmarkResponse>,
                    landmarkResponse: Response<ActivationCodeLandmarkResponse>
                ) {

                    if (landmarkResponse!!.isSuccessful) {

                        printLog(Gson().toJson(landmarkResponse.body()))

                        if (landmarkResponse.body() != null) {

                            // default success
                            iResponseSubcriber.OnSuccess(landmarkResponse.body()!!, "")

                        } else {

                            iResponseSubcriber.OnFailure(MESSAGE)
                        }
                    } else {
                        iResponseSubcriber.OnFailure(
                            errorStatus(
                                landmarkResponse.code().toString()
                            )
                        )
                    }

                }

                override fun onFailure(call: Call<ActivationCodeLandmarkResponse>, t: Throwable) {
                     if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
                }
            })
    }

    override fun getPolicyBossVehicleInfo(
        RegistrationNumber: String,
        iResponseSubcriber: IResponseSubcriber
    ) {
        var url = "http://horizon.policyboss.com:5000/quote/vehicle_info"

        var map = hashMapOf<String, String>()
        map.put("RegistrationNumber", RegistrationNumber)
        map.put("client_key", "CLIENT-CNTP6NYE-CU9N-DUZW-CSPI-SH1IS4DOVHB9")
        map.put("product_id", "1")
        map.put("secret_key", "SECRET-HZ07QRWY-JIBT-XRMQ-ZP95-J0RWP3DYRACW")
        map.put("ss_id", "8067")



        authenticationBuilder.getPolicyBossVehicleInfo(url, map).enqueue(object :
            Callback<PolicyBossVehicleInfoResponse> {
            override fun onResponse(
                call: Call<PolicyBossVehicleInfoResponse>,
                response: Response<PolicyBossVehicleInfoResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {

                        // ApplicationPersistance(mContext).saveUser(response.body()!!)
                        iResponseSubcriber.OnSuccess(response.body()!!, response.message())

                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<PolicyBossVehicleInfoResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }

            }
        })
    }


    override fun getUserConstatnt(userid: String, iResponseSubcriber: IResponseSubcriber) {
        var map = hashMapOf<String, String>()
        map.put("userid", userid)



        authenticationBuilder.getUserConstant(map).enqueue(object :
            Callback<UserConsttantResponse> {
            override fun onResponse(
                call: Call<UserConsttantResponse>,
                response: Response<UserConsttantResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {
                            // ApplicationPersistance(mContext).saveUser(response.body()!!)
                            PrefManager(mContext).storeUserConstatnt(
                                response.body()?.Data?.get(0)
                            )
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<UserConsttantResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun getCityState(pincode: String, iResponseSubcriber: IResponseSubcriber) {
        var map = hashMapOf<String, String>()
        map.put("pincode", pincode)



        authenticationBuilder.getCityState(map).enqueue(object :
            Callback<PincodeResponse> {
            override fun onResponse(
                call: Call<PincodeResponse>,
                response: Response<PincodeResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {
                            // ApplicationPersistance(mContext).saveUser(response.body()!!)
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<PincodeResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun verifyOTPTegistration(
        email: String,
        mobile: String,

        iResponseSubcriber: IResponseSubcriber
    ) {
        var map = hashMapOf<String, String>()
        map.put("email", email)
        map.put("mobNo", mobile)
        map.put("ip", "")



        authenticationBuilder.verifyOTPTegistration(map).enqueue(object :
            Callback<VerifyUserOTPRegisterResponse> {
            override fun onResponse(
                call: Call<VerifyUserOTPRegisterResponse>,
                response: Response<VerifyUserOTPRegisterResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {
                            // ApplicationPersistance(mContext).saveUser(response.body()!!)
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<VerifyUserOTPRegisterResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }

            }
        })
    }

    override fun saveUserRegistration(
        registerRequest: RegisterRequest,
        iResponseSubcriber: IResponseSubcriber
    ) {
        authenticationBuilder.userRegistration(registerRequest).enqueue(object :
            Callback<UserRegistrationResponse> {
            override fun onResponse(
                call: Call<UserRegistrationResponse>,
                response: Response<UserRegistrationResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {
                            // ApplicationPersistance(mContext).saveUser(response.body()!!)

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            //iResponseSubcriber.OnFailure(response.body()?.message!!)
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<UserRegistrationResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }


    override fun getVersion(iResponseSubcriber: IResponseSubcriber) {

      //  var url = BuildConfig.FINMART_URL + "/api/EmpLogin"

        authenticationBuilder.getVersionCode().enqueue(object : Callback<VersionCodeResponse> {
            override fun onResponse(
                call: Call<VersionCodeResponse>,
                response: Response<VersionCodeResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {
                            // ApplicationPersistance(mContext).saveUser(response.body()!!)
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<VersionCodeResponse>, t: Throwable) {
                 if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun getLogin(
        mobile: String,
        password: String,
        token: String,
        devId: String,
        iResponseSubcriber: IResponseSubcriber
    ) {


        var map = hashMapOf<String, String>()
        map.put("mobile", mobile)
        map.put("password", password)
        map.put("device_token", token)
        map.put("device_id", devId)
        map.put("user_type_id", "1")


        authenticationBuilder.getLogin(map).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {
                            // ApplicationPersistance(mContext).saveUser(response.body()!!)
                            PrefManager(mContext).storeUserData(
                                response.body()!!.Data.get(0).userdetails.get(0)
                            )
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }

            }
        })
    }

    override fun updateGolduser(
        user_id: String,
        ActivationCode: String,
        iResponseSubcriber: IResponseSubcriber
    ) {


        var map = hashMapOf<String, String>()
        map.put("id", user_id)
        map.put("activation_code", ActivationCode)


        authenticationBuilder.updateGolduser(map).enqueue(object :
            Callback<UpladeGoldResponse> {
            override fun onResponse(
                call: Call<UpladeGoldResponse>,
                response: Response<UpladeGoldResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<UpladeGoldResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }

            }
        })
    }

    override fun insertTataPeep(
        MobileNo: String,
        RegistrationNo: String,
        PolicyLink: String,
        iResponseSubcriber: IResponseSubcriber
    ) {

        var map = hashMapOf<String, String>()
        map.put("MobileNo", MobileNo)
        map.put("RegistrationNo", RegistrationNo)
        map.put("PolicyLink", PolicyLink)



        authenticationBuilder.insertTataPeep(map).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })

    }

    override fun getTataPeep(MobileNo: String, iResponseSubcriber: IResponseSubcriber) {

        var map = hashMapOf<String, String>()
        map.put("MobileNo", MobileNo)



        authenticationBuilder.getTataPeep(map).enqueue(object : Callback<TataResponse> {
            override fun onResponse(
                call: Call<TataResponse>,
                response: Response<TataResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<TataResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }


    override fun insertGlobalAssure(
        LoginID: String ,
        MobileNo: String,
        RegistrationNo: String,
        CertificateNo: String,
        CertificateFile: String,
        iResponseSubcriber: IResponseSubcriber
    ) {

        var map = hashMapOf<String, String>()
        map.put("id", LoginID)
        map.put("MobileNo", MobileNo)
        map.put("RegistrationNo", RegistrationNo)
        map.put("CertificateNo", CertificateNo)
        map.put("CertificateFile", CertificateFile)



        authenticationBuilder.insertGlobalAssure(map).enqueue(object : Callback<GlobalAssureResponse> {
            override fun onResponse(
                call: Call<GlobalAssureResponse>,
                response: Response<GlobalAssureResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<GlobalAssureResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })

    }

    override fun getGlobalAssure(MobileNo: String, iResponseSubcriber: IResponseSubcriber) {

        var map = hashMapOf<String, String>()
        map.put("MobileNo", MobileNo)



        authenticationBuilder.getGlobalAssure(map).enqueue(object : Callback<GlobalAssureResponse> {
            override fun onResponse(
                call: Call<GlobalAssureResponse>,
                response: Response<GlobalAssureResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<GlobalAssureResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun getGlobalAssureCount(LoginID: String, iResponseSubcriber: IResponseSubcriber) {
        var map = hashMapOf<String, String>()
        map.put("id", LoginID)



        authenticationBuilder.getGlobalAssureCount(map).enqueue(object : Callback<GlobalAssureResponse> {
            override fun onResponse(
                call: Call<GlobalAssureResponse>,
                response: Response<GlobalAssureResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<GlobalAssureResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun getGlobalAssureVerification(
        LoginID: String,
        RegistrationNo: String,
        iResponseSubcriber: IResponseSubcriber
    ) {
        var map = hashMapOf<String, String>()
        map.put("id", LoginID)
        map.put("RegistrationNo", RegistrationNo)



        authenticationBuilder.getGlobalAssureVerification(map).enqueue(object : Callback<VerifyGlobalAssureResponse> {
            override fun onResponse(
                call: Call<VerifyGlobalAssureResponse>,
                response: Response<VerifyGlobalAssureResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<VerifyGlobalAssureResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })
    }

    override fun insertActivationCode(
        MobileNo: String,
        RegistrationNo: String,
        ActivationCode: String,
        iResponseSubcriber: IResponseSubcriber
    ) {

        var map = hashMapOf<String, String>()
        map.put("MobileNo", MobileNo)
        map.put("RegistrationNo", RegistrationNo)
        map.put("ActivationCode", ActivationCode)




        authenticationBuilder.insertActivationCode(map).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }
            }
        })

    }
    override fun getActivationCode(MobileNo: String, iResponseSubcriber: IResponseSubcriber) {

        var map = hashMapOf<String, String>()
        map.put("MobileNo", MobileNo)



        authenticationBuilder.getActivationCode(map).enqueue(object :
            Callback<ActivationCodeResponse> {
            override fun onResponse(
                call: Call<ActivationCodeResponse>,
                response: Response<ActivationCodeResponse>
            ) {

                if (response!!.isSuccessful) {

                    printLog(Gson().toJson(response.body()))

                    if (response.body() != null) {
                        if (response.body()?.status_code == 0) {

                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        } else {
                            iResponseSubcriber.OnSuccess(response.body()!!, response.message())
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<ActivationCodeResponse>, t: Throwable) {
                if(t is UnknownHostException){

                    iResponseSubcriber.OnFailure("Check your internet connection")

                }else{
                    iResponseSubcriber.OnFailure("" + t.message.toString())
                }

            }
        })
    }


}