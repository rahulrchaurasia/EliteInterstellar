package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.GlobalAssureResult

data class GlobalAssureLandmarkResponse(
    val GlobalAssureResult: GlobalAssureResult
):APIResponse()