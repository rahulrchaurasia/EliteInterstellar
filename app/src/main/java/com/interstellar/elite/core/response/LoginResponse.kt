package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.LoginEntity

data class LoginResponse(
    val Data: List<LoginEntity>
): APIResponse()