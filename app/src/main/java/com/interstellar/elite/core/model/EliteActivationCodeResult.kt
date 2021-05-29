package com.interstellar.elite.core.model

import com.interstellar.elite.core.response.EliteActivationCodedetail

data class EliteActivationCodeResult(
    val EliteActivationCodedetails: List<EliteActivationCodedetail>,
    val message: String,
    val status: String,
    val status_code: Int
)