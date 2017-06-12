package com.avp.studio.config

import com.avp.studio.security.test.UserRepositoryUserDetailsRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.DispatcherHandler
import org.springframework.context.ApplicationContext
import org.springframework.security.web.reactive.result.method.AuthenticationPrincipalArgumentResolver
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.ipc.netty.NettyContext
import reactor.ipc.netty.http.server.HttpServer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.server.AuthorizeRequestBuilder
import org.springframework.security.config.web.server.HttpSecurity.http
import org.springframework.security.web.server.ContentTypeOptionsHttpHeadersWriter

import org.springframework.web.server.WebFilter






//@Configuration
//@EnableWebFlux
//@EnableWebFluxSecurity
class WebFluxSecurityConfig : WebFluxConfigurer {

    //@Value("\${server.port:8080}")
    private var port = 8080

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) =
            configurer.addCustomResolver(authenticationPrincipalArgumentResolver())

    @Bean
    fun authenticationPrincipalArgumentResolver(): AuthenticationPrincipalArgumentResolver {
        return AuthenticationPrincipalArgumentResolver()
    }

    @Bean
    fun contentTypeOptionsHttpHeadersWriter() = ContentTypeOptionsHttpHeadersWriter()

    @Bean
    @Throws(Exception::class)
    fun springSecurityFilterChain(manager: ReactiveAuthenticationManager): WebFilter {

        val http = http()

        http.headers()

        http.authenticationManager(manager)
        http.httpBasic()

        //val authorize = http.authorizeRequests()

        http.authorizeRequests().antMatchers("/persons").hasRole("ADMIN")
        http.authorizeRequests().anyExchange().authenticated()

        //authorize.build()



        return http.build()
    }

    @Bean
    fun authenticationManager(udr: UserRepositoryUserDetailsRepository): ReactiveAuthenticationManager {
        return UserDetailsAuthenticationManager(udr)
    }

    /*@Bean
    fun nettyContext(context: ApplicationContext): NettyContext {
        val handler = RouterFunctions.toHttpHandler(router())
        val adapter = ReactorHttpHandlerAdapter(handler)
        val httpServer = HttpServer.create(port)

        val nettyContext = httpServer.newHandler(adapter).block()

        //nettyContext.onClose().block()

        return nettyContext
    }*/
}