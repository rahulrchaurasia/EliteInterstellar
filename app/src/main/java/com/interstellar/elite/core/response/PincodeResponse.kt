package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.PincodeEntity

data class PincodeResponse(
    val Data: List<PincodeEntity>,

) :APIResponse()