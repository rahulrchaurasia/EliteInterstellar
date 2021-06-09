package com.interstellar.elite.core.requestmodel

import com.interstellar.elite.core.APIResponse

data class RegisterRequest (



    var otp: String,
    var name: String,
    var emailid: String,
    var mobile: String,

    var password: String,
    var company_id: String,
    var lat: String,
    var lon: String,

    var pincode: String,
    var state: String,
    var area: String,
    var city: String,

    var ProductCode: String,
    var RiskEndDate: String,
    var RiskStartDate: String,
    var InsuredName: String,


    var policy_no: String,
    var PolicyStatus: String,
    var ResponseStatus: String,
    var vehicle_no: String,


    var Make: String,
    var Model: String,
    var user_id: String,


)
