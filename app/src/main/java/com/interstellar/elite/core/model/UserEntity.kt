package com.interstellar.elite.core.model

data class UserEntity(
    val cityid: String,
    val email: String,
    val make: String,
    val mobile: String,
    val model: String,
    val name: String,
    val rtoid: String,
    val seriesno: String,
    val user_id: Int,
    val user_type_id: Int,
    val vehicleno: String
)