package com.yooshyasha.marketplaceforaction.dto

import com.yooshyasha.marketplaceforaction.entities.User

data class UserDTO (
    val username: String,
    val roles : Set<String>,
) {
    constructor(user: User) : this(user.username, user.roles)
}