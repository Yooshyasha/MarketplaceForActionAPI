package com.yooshyasha.marketplaceforaction.services

import com.yooshyasha.marketplaceforaction.entities.User
import com.yooshyasha.marketplaceforaction.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun addUser(user: User) {
        userRepository.save(user)
    }

    fun updateUser(user: User) {
        userRepository.save(user)
    }

    fun deleteUser(user: User) {
        userRepository.delete(user)
    }
}