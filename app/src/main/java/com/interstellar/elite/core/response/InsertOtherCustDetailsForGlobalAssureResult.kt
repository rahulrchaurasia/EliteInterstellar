package com.interstellar.elite.core.response

data class InsertOtherCustDetailsForGlobalAssureResult(
    val EliteGlobalAssuredetails: List<EliteGlobalAssuredetail>,
    val message: String,
    val status: String,
    val status_code: String
)