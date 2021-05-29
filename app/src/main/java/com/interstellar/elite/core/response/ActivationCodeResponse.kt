package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse

data class ActivationCodeResponse(
    val Data: List<EliteActivationCodedetail>,

):APIResponse()