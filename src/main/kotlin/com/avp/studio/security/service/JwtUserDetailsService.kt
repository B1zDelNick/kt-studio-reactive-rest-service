package com.avp.studio.security.service

import com.avp.studio.security.JwtUserFactory
import com.avp.studio.security.model.Authority
import com.avp.studio.security.model.AuthorityName
import com.avp.studio.security.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import com.google.common.collect.ImmutableList
import com.avp.studio.security.model.User
import lombok.extern.slf4j.Slf4j
import java.util.*



@Service
@Slf4j
class JwtUserDetailsService : AccountDetailsService {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByEmail(username)

        println("Retrieved: $user is ${user.isPresent}")

        if (user.isPresent) {


            return JwtUserFactory.create(user.get())
        }
        else {
            throw UsernameNotFoundException("No user found with username '$username'.")
        }
    }

    override fun saveUser(username: String, password: String) : UserDetails {

        val userOptional = userRepository.findByEmail(username)

        if (userOptional.isPresent)
            throw UsernameNotFoundException(String.format("User with email '%s' already exists!", username))

        val defaultAuthority = createDefaultAuthority()

        val user = User(username, passwordEncoder.encode(password), "Andrei", "Vasilevich", true, Date(), ImmutableList.of(defaultAuthority))

        /*user.email = username
        user.setEmail(username)
        user.setFirstname("Andrei")
        user.setLastname("Vasilevich")
        user.setPassword(passwordEncoder.encode(password))
        user.setEnabled(true)
        user.setLastPasswordResetDate(Date())

        defaultAuthority.getUsers().add(user)
        user.setAuthorities(ImmutableList.of(defaultAuthority))*/

        val userSaved = userRepository.save(user)

        return JwtUserFactory.create(userSaved)
    }

    private fun createDefaultAuthority() = Authority(AuthorityName.ROLE_USER)
}