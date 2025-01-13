package com.yooshyasha.marketplaceforaction.controller

import com.yooshyasha.marketplaceforaction.dto.auth.LoginDTO
import com.yooshyasha.marketplaceforaction.dto.auth.RegisterDTO
import com.yooshyasha.marketplaceforaction.dto.auth.ResponseLoginDTO
import com.yooshyasha.marketplaceforaction.exceptions.UserAlreadyExists
import com.yooshyasha.marketplaceforaction.exceptions.UsernameOrPasswordInvalid
import com.yooshyasha.marketplaceforaction.services.AuthService
import com.yooshyasha.marketplaceforaction.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService,
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterDTO): ResponseEntity<Any> {
        try {
            val user = userService.registerUser(request.username, request.password, setOf())
            return ResponseEntity.ok(user)
        } catch (ex: UserAlreadyExists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.message)
        } catch (ex: Exception) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to ex.message))
        }
    }

    @GetMapping("/login")
    fun login(@RequestBody request: LoginDTO): ResponseEntity<Any> {
        try {
            val token = authService.authenticate(request.username, request.password)
            val user = authService.getUserFromToken(token)

            return ResponseEntity.ok(ResponseLoginDTO(token, user))
        } catch (ex: UsernameOrPasswordInvalid) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mapOf("message" to ex.message))
        } catch (ex: Exception) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to ex.message))
        }
    }

}