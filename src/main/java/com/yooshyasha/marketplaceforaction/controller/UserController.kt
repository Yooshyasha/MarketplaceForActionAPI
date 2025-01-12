package com.yooshyasha.marketplaceforaction.controller

import com.yooshyasha.marketplaceforaction.entities.User
import com.yooshyasha.marketplaceforaction.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/getAllUsers")
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @PostMapping("/registerUser")
    fun register(@RequestBody user: User) {

    }
}