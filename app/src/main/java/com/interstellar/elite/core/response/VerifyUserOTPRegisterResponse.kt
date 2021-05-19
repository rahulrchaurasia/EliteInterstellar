package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.VerifyOTPEntity

data class VerifyUserOTPRegisterResponse(
    val Data: VerifyOTPEntity,

    ):APIResponse()