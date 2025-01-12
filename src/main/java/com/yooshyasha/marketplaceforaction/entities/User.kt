package com.yooshyasha.marketplaceforaction.entities

import com.yooshyasha.marketplaceforaction.enums.Role
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity
data class User (
    @Id var id: Long? = null,

    var username: String? = null,
    var password: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = null,
)