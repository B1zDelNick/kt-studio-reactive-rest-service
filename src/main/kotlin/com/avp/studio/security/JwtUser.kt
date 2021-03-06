package com.avp.studio.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


class JwtUser(
        val id: String?,
        private val email: String,
        val firstname: String,
        val lastname: String,
        private val password: String,
        private val authorities: Collection<GrantedAuthority>,
        val enabled: Boolean,
        val lastPasswordResetDate: Date
) : UserDetails {

    override fun getUsername(): String {
        return email
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}