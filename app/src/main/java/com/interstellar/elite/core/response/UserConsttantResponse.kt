package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.UserConstatntEntity

data class UserConsttantResponse(
    val Data: List<UserConstatntEntity>,

    ):APIResponse()