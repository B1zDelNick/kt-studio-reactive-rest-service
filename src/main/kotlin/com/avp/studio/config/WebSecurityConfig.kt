package com.avp.studio.config

import com.avp.studio.security.JwtAuthenticationEntryPoint
import com.avp.studio.security.JwtAuthenticationTokenFilter
import com.avp.studio.security.JwtTokenUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.web.reactive.function.server.RequestPredicates.headers
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.authorization.AuthenticatedAuthorizationManager.authenticated
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.web.filter.CorsFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableWebSecurity
//@EnableWebFluxSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        val userDetailsService: UserDetailsService,
        val persistentTokenRepository: PersistentTokenRepository,
        val unauthorizedHandler: JwtAuthenticationEntryPoint): WebSecurityConfigurerAdapter() { ////{ //val userDetailsService: UserDetailsService) {// : WebFluxSecurityConfiguration() { ///

    @Value("\${jwt.rememberme.key:juliaBeautyStudioRemKey}")
    private lateinit var rememberMeKey: String

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationTokenFilterBean(): JwtAuthenticationTokenFilter {
        return JwtAuthenticationTokenFilter()
    }

    @Bean
    fun corsFilter(): CorsFilter {

        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("DELETE")
        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }

    @Throws(Exception::class)
    fun configureAuthentication(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(httpSecurity: HttpSecurity) {

        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()

                // don't create session
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()

                .authorizeRequests()
                    // allow anonymous resource requests
                    .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                    ).permitAll()
                    .antMatchers("/auth/**")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()

                .rememberMe()
                    .userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository)
                    .rememberMeCookieName("REMEMBER_ME")
                    .rememberMeParameter("remember_me")
                    .tokenValiditySeconds(1209600)
                    .useSecureCookie(false)
                    .key(rememberMeKey)

        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter::class.java)


        // disable page caching
        httpSecurity.headers().cacheControl()
    }
}
