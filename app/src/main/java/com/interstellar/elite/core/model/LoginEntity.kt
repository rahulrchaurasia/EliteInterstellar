package com.interstellar.elite.core.model

data class LoginEntity(
    val orderstatuslist: List<OrderStatusEntity>,
    val userdetails: List<UserEntity>
)