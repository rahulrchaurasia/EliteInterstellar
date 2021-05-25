package com.interstellar.elite.core.response

import com.interstellar.elite.core.model.TataEntity

data class EliteTataPeepResult(
    val EliteTataPeepdetails: List<TataEntity>,
    val message: String,
    val status: String,
    val status_code: Int
)