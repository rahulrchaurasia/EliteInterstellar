package com.interstellar.elite.core.controller

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.interstellar.elite.core.BaseController
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.requestbuilder.AuthenticationBuilder
import com.interstellar.elite.core.requestmodel.RegisterRequest
import com.interstellar.elite.core.response.*
import com.interstellar.elite.facade.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url


open class AuthenticationController(mCxt: Context) : BaseController(), IAuthentication {

    internal fun printLog(log: String) = Log.d("---log", log)
    private val MESSAGE = "Unable to connect to server, Please try again"

    var authenticationBuilder: AuthenticationBuilder.AuthenticationNetworkService
    var mContext: Context
    //var loginEntity: UserEntity
    var prefManager: PrefManager
    init {
        authenticationBuilder = AuthenticationBuilder().getService()
        mContext = mCxt

        prefManager = PrefManager(mContext)
        //loginEntity = prefManager.getUserData()!!
    }


    override fun getUserEligibility(
        MobileNo: String,
        RegistrationNo: String,
        iResponseSubcriber: IResponseSubcriber
    ) {
        var url = "http://elite.landmarkinsurance.in/EliteAppService.svc/EliteEligibilityCheck"



        authenticationBuilder.getUserEligibility(url,MobileNo,RegistrationNo).enqueue(object :
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
                iResponseSubcriber.OnFailure("" + t.message.toString())
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
                iResponseSubcriber.OnFailure("" + t.message.toString())
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
                iResponseSubcriber.OnFailure("" + t.message.toString())
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
                            iResponseSubcriber.OnFailure(response.body()?.message!!)
                        }
                    } else {

                        iResponseSubcriber.OnFailure(MESSAGE)
                    }
                } else {
                    iResponseSubcriber.OnFailure(errorStatus(response.code().toString()))
                }

            }

            override fun onFailure(call: Call<VerifyUserOTPRegisterResponse>, t: Throwable) {
                iResponseSubcriber.OnFailure("" + t.message.toString())

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
                iResponseSubcriber.OnFailure("" + t.message.toString())
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
                iResponseSubcriber.OnFailure("" + t.message.toString())
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
                iResponseSubcriber.OnFailure("" + t.message.toString())
            }
        })
    }
}