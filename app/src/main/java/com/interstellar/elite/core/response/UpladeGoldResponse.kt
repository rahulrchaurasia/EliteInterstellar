package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.UpdateGoldEntity

data class UpladeGoldResponse(
    val Data: List<UpdateGoldEntity>,

    ):APIResponse()