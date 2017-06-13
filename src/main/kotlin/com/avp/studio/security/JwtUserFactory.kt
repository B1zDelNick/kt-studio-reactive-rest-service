package com.avp.studio.security

import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import com.avp.studio.security.model.Authority
import com.avp.studio.security.model.User
import org.springframework.security.core.GrantedAuthority


/*
object JwtUserFactory {

    fun create(user: User) = JwtUser(
            user.id,
            user.email,
            user.firstname,
            user.lastname,
            user.password,
            mapToGrantedAuthorities(user.authorities),
            user.enabled,
            user.lastPasswordResetDate
    )

    private fun mapToGrantedAuthorities(authorities: List<Authority>): List<GrantedAuthority> {
        return authorities.stream()
                .map { (name) -> SimpleGrantedAuthority(name.name) }
                .collect(Collectors.toList<GrantedAuthority>())
    }
}*/

class JwtUserFactory {

    companion object {

        fun create(user: User) = JwtUser(
                user.id,
                user.email,
                user.firstname,
                user.lastname,
                user.password,
                mapToGrantedAuthorities(user.authorities),
                user.enabled,
                user.lastPasswordResetDate
        )

        private fun mapToGrantedAuthorities(authorities: List<Authority>): List<GrantedAuthority> {
            return authorities.stream()
                    .map { (name) -> SimpleGrantedAuthority(name.name) }
                    .collect(Collectors.toList<GrantedAuthority>())
        }
    }
}
