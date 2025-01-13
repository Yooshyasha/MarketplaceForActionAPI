package com.yooshyasha.marketplaceforaction.services

import com.yooshyasha.marketplaceforaction.entities.User
import com.yooshyasha.marketplaceforaction.exceptions.UsernameOrPasswordInvalid
import com.yooshyasha.marketplaceforaction.security.JwtTokenProvider
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key

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

    fun validateToken(token: String): Claims {
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
        val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getUserFromToken(token: String): User {
        val username = getUsernameFromToken(token)
        return userService.getUserByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }
}