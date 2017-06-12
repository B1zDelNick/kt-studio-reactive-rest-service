package com.avp.studio.security.service

import com.avp.studio.security.JwtUserFactory
import com.avp.studio.security.model.Authority
import com.avp.studio.security.model.AuthorityName
import com.avp.studio.security.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class JwtUserDetailsServiceImpl(
        val passwordEncoder: PasswordEncoder,
        val userRepository: UserRepository) : AccountDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByEmail(username)

        if (user.isPresent) {
            return JwtUserFactory.create(user.get())
        }
        else {
            throw UsernameNotFoundException("No user found with username '$username'.")
        }
    }

    override fun saveUser(username: String, password: String) {

    }

    private fun createDefaultAuthority() = Authority(AuthorityName.ROLE_USER)
}