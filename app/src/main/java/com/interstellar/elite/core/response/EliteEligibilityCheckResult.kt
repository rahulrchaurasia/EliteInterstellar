package com.interstellar.elite.core.response

import com.interstellar.elite.core.model.EligibilityEntity


data class EliteEligibilityCheckResult(
    val EliteEligibilityCheckdetails: List<EligibilityEntity>,
    val message: String,
    val status: String,
    val status_code: Int
)