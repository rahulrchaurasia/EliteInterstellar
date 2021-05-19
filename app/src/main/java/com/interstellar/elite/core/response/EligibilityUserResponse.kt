package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse

data class EligibilityUserResponse(
    val EliteEligibilityCheckResult: EliteEligibilityCheckResult
):APIResponse()