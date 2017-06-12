package com.avp.studio.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

//@Component
class JwtUserDetailsServiceImpl : AccountDetailsService {

    override fun loadUserByUsername(p0: String?): UserDetails? {

        return null
    }

    override fun saveUser(username: String, password: String) {

    }
}