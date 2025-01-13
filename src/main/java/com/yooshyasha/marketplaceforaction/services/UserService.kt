package com.yooshyasha.marketplaceforaction.services

import com.yooshyasha.marketplaceforaction.entities.User
import com.yooshyasha.marketplaceforaction.exceptions.UserAlreadyExists
import com.yooshyasha.marketplaceforaction.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
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

    fun registerUser(username: String, password: String, roles: Set<String>) : User {
        if (userRepository.existsByUserName(username)) {
            throw UserAlreadyExists()
        }

        val encodedPassword = passwordEncoder.encode(password)

        val user = User(id = UUID.randomUUID(), userName = username, hashedPassword = encodedPassword, roles = roles)

        addUser(user)

        return user
    }

    fun getUserByUsername(username: String): User? {
        return userRepository.findByUserName(username)
    }
}