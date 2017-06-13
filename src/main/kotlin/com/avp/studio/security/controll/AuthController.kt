package com.avp.studio.security.controll

import com.avp.studio.security.JwtAuthenticationRequest
import com.avp.studio.security.service.AccountDetailsService
import org.springframework.beans.factory.annotation.Autowired
import com.avp.studio.security.JwtTokenUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import com.avp.studio.security.service.JwtAuthenticationResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.mobile.device.Device
import org.springframework.security.core.AuthenticationException
import com.avp.studio.security.JwtUser
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@RestController
@Slf4j
//@CrossOrigin(maxAge = 3600)
class AuthController(
        val authenticationManager: AuthenticationManager,
        val jwtTokenUtil: JwtTokenUtil,
        val userDetailsService: AccountDetailsService) {

    @Value("\${jwt.header}")
    private lateinit var tokenHeader: String

    @Value("\${jwt.route.authentication.path}")
    private lateinit var AUTH_PATH: String

    @Value("\${jwt.route.registration.path}")
    private lateinit var REGISTER_PATH: String

    @Value("\${jwt.route.authentication.refresh}")
    private lateinit var REFREASH_PATH: String

    @PostConstruct
    fun pre() {
        println(AUTH_PATH)
    }

    @RequestMapping(value = "\${jwt.route.authentication.path}", method = arrayOf(RequestMethod.POST))
    @Throws(AuthenticationException::class)
    fun createAuthenticationToken(
            @RequestBody authenticationRequest: JwtAuthenticationRequest, device: Device): ResponseEntity<*> {

        println("Auth Request: $authenticationRequest")

        // Perform the security
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authenticationRequest.username,
                        authenticationRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        // Reload password post-security so we can generate token
        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtil.generateToken(userDetails, device)

        // Return the token
        return ResponseEntity.ok(JwtAuthenticationResponse(token))
    }

    //@CrossOrigin
    @RequestMapping(value = "\${jwt.route.registration.path}", method = arrayOf(RequestMethod.POST))
    @Throws(AuthenticationException::class)
    fun registerUserAndCreateAuthenticationToken(
            @RequestBody authenticationRequest: JwtAuthenticationRequest, device: Device): ResponseEntity<*> {

        println("Try sing in with: " + authenticationRequest.username + " - " + authenticationRequest.password)

        val userDetails = userDetailsService.saveUser(
                authenticationRequest.username!!,
                authenticationRequest.password!!)

        // Perform the security
        /*val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authenticationRequest.username,
                        authenticationRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication*/

        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                authenticationRequest.username,
                authenticationRequest.password
        )

        // Reload password post-security so we can generate token
        //val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        /*final UserDetails userDetails = userDetailsService.saveUser(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword());*/

        val token = jwtTokenUtil.generateToken(userDetails, device)

        // Return the token
        return ResponseEntity.ok(JwtAuthenticationResponse(token))
    }

    @RequestMapping(value = "\${jwt.route.authentication.refresh}", method = arrayOf(RequestMethod.GET))
    fun refreshAndGetAuthenticationToken(request: HttpServletRequest): ResponseEntity<*> {

        val token = request.getHeader(tokenHeader)
        val username = jwtTokenUtil.getUsernameFromToken(token)
        val user = userDetailsService.loadUserByUsername(username) as JwtUser

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.lastPasswordResetDate)) {
            val refreshedToken = jwtTokenUtil.refreshToken(token)
            return ResponseEntity.ok(JwtAuthenticationResponse(refreshedToken!!))
        }
        else {
            return ResponseEntity.badRequest().body(null)
        }
    }
}