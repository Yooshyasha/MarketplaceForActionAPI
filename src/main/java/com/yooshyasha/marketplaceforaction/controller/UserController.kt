package com.yooshyasha.marketplaceforaction.controller

import com.yooshyasha.marketplaceforaction.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/getAllUsers")
    fun getAllUsers(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getAllUsers())
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.message)
        }
    }

    @GetMapping("/getMe")
    fun getMe(): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication

        try {
            val userDetails = authentication.principal as UserDetails
            val user = userService.getUserByUsername(userDetails.username)

            return ResponseEntity.ok(user)
        } catch (ex: Exception) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to ex.message))
        }
    }
}