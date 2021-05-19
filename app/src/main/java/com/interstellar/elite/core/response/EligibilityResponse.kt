package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.EligibilityEntity

data class EligibilityResponse(
        val MasterData: EligibilityEntity,
        val StatusNo: Int
) : APIResponse()