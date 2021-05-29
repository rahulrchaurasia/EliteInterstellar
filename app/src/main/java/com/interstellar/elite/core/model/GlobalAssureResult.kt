package com.interstellar.elite.core.model

data class GlobalAssureResult(
    val CertificateFile: String,
    val CertificateNo: String,
    val ErrorCode: String,
    val ErrorMessage: String,
    val ErrorMessageDetail: String,
    val PaymentLink: String,
    val TransId: String,
    val status: String
)