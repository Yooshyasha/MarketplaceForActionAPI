package com.yooshyasha.marketplaceforaction.entities

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
data class User (
    @Id @GeneratedValue(strategy = GenerationType.UUID) var id: UUID? = null,

    var userName: String = "",
    var hashedPassword: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: Set<String> = setOf()
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }.toMutableList()
    }

    override fun getPassword(): String = hashedPassword

    override fun getUsername(): String = userName

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}