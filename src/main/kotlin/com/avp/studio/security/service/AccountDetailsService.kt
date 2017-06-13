package com.avp.studio.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface AccountDetailsService: UserDetailsService {

    fun saveUser(username: String, password: String) : UserDetails
}