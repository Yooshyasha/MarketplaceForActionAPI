package com.yooshyasha.marketplaceforaction.repositories

import com.yooshyasha.marketplaceforaction.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByUserName(username: String): Boolean

    fun findByUserName(username: String): User?
}