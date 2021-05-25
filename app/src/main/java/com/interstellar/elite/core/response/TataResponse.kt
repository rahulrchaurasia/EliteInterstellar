package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.TataEntity

data class TataResponse(
    val Data: List<TataEntity>,

    ) :APIResponse()