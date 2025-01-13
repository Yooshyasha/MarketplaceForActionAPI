package com.yooshyasha.marketplaceforaction.dto.auth

import com.yooshyasha.marketplaceforaction.entities.User

data class ResponseLoginDTO (
    val token: String,
    val user: User,
)