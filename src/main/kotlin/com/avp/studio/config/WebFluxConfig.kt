package com.avp.studio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.bodyFromPublisher
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Stream

@Configuration
@EnableWebFlux
class WebFluxConfig {

    val atom: AtomicLong = AtomicLong()


    /* Good for Browsers SIMPLE SSE Json Array */
    /*@Bean
    fun router(): RouterFunction<ServerResponse> =
            RouterFunctions.route(RequestPredicates.GET("/persons"),
                    HandlerFunction<ServerResponse> {
                        ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.just({"1"}, {"2"}, {"3"}, {"4"}, {"5"}).delayElements(Duration.ofSeconds(1))) //(Flux.just("1", "2", "3", "4", "5")), String::class.java)
                    })*/

    /* Bad for browsers Good for data transmittings */
    /*@Bean
    fun router(): RouterFunction<ServerResponse> =
            RouterFunctions.route(RequestPredicates.GET("/persons"),
                    HandlerFunction<ServerResponse> {
                        ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(Flux.just({"1"}, {"2"}, {"3"}, {"4"}, {"5"}).delayElements(Duration.ofSeconds(1))) //(Flux.just("1", "2", "3", "4", "5")), String::class.java)
                    })*/

    /* Array of Jsons */
    /*@Bean
    fun router(): RouterFunction<ServerResponse> =
            RouterFunctions.route(RequestPredicates.GET("/persons"),
                    HandlerFunction<ServerResponse> {
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(Flux.just({"1"}, {"2"}, {"3"}, {"4"}, {"5"}).delayElements(Duration.ofSeconds(1))) //(Flux.just("1", "2", "3", "4", "5")), String::class.java)
                    })*/

    @Bean
    fun router(): RouterFunction<ServerResponse> =
            RouterFunctions.route(RequestPredicates.GET("/persons"),
                    HandlerFunction<ServerResponse> {
                        ServerResponse.ok().body(Flux.just({"1"}, {"2"}, {"3"}, {"4"}, {"5"}).delayElements(Duration.ofSeconds(1))) //(Flux.just("1", "2", "3", "4", "5")), String::class.java)
                    })
}