package com.yooshyasha.marketplaceforaction.services

import com.yooshyasha.marketplaceforaction.entities.User
import com.yooshyasha.marketplaceforaction.exceptions.UsernameOrPasswordInvalid
import com.yooshyasha.marketplaceforaction.security.JwtTokenProvider
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    @Value("\${auth-configuration.secretKey}") private val secretKey: String,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    fun generateToken(user: User): String {
        return jwtTokenProvider.createJwt(user.userName, secretKey, user.roles)
    }

    fun parseJwt(token: String): Claims {
        return jwtTokenProvider.parseJwt(token, secretKey)
    }

    fun authenticate(username: String, password: String): String {
        val user = userService.getUserByUsername(username) ?: throw UsernameNotFoundException("User not found")

        if (!passwordEncoder.matches(password, user.hashedPassword)) {
            throw UsernameOrPasswordInvalid()
        }

        return generateToken(user)
    }

    private fun getUsernameFromToken(token: String): String {
        return parseJwt(token).subject
    }

    fun getUserFromToken(token: String): User {
        val username = getUsernameFromToken(token)
        return userService.getUserByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }
}