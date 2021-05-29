package com.interstellar.elite.core.controller

import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.requestmodel.RegisterRequest


interface IAuthentication {


    fun getUserEligibility( MobileNo :String ,RegistrationNo : String ,iResponseSubcriber: IResponseSubcriber)

    fun getLandmarkEliteTataPee( MobileNo :String ,RegistrationNo : String ,iResponseSubcriber: IResponseSubcriber)

    fun getLandmarkEliteGlobalAssure( MobileNo :String ,RegistrationNo : String ,iResponseSubcriber: IResponseSubcriber)

    fun getLandmarkEliteActivationCode( MobileNo :String ,RegistrationNo : String ,iResponseSubcriber: IResponseSubcriber)

    fun getUserConstatnt(userid: String,iResponseSubcriber: IResponseSubcriber)

     fun getCityState(pincode: String, iResponseSubcriber: IResponseSubcriber)

    fun verifyOTPTegistration(
        email: String,
        mobile: String,
        iResponseSubcriber: IResponseSubcriber
    )
    fun saveUserRegistration(
        registerRequest: RegisterRequest,
        iResponseSubcriber: IResponseSubcriber
    )

    fun getVersion(iResponseSubcriber: IResponseSubcriber)

    fun getLogin(
        mobile: String,
        password: String,
        token: String,
        devId: String,
        iResponseSubcriber: IResponseSubcriber
    )


    fun insertTataPeep(
        MobileNo: String,
        RegistrationNo: String,
        PolicyLink: String,
        iResponseSubcriber: IResponseSubcriber
    )

    fun getTataPeep(MobileNo: String, iResponseSubcriber: IResponseSubcriber)

    fun insertGlobalAssure(
        MobileNo: String,
        RegistrationNo: String,
        CertificateNo: String,
        CertificateFile: String,
        iResponseSubcriber: IResponseSubcriber
    )

    fun getGlobalAssure(MobileNo: String, iResponseSubcriber: IResponseSubcriber)


    fun insertActivationCode(
        MobileNo: String,
        RegistrationNo: String,
        ActivationCode: String,
        iResponseSubcriber: IResponseSubcriber
    )

    fun getActivationCode(MobileNo: String, iResponseSubcriber: IResponseSubcriber)
}