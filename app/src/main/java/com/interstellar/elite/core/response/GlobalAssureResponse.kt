package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.GloabalAssureEntity

data class GlobalAssureResponse(
    val Data: MutableList<GloabalAssureEntity>,

    ):APIResponse()