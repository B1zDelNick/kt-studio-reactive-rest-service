package com.avp.studio.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MainConfig {

    @Bean
    fun kotlinModule() = KotlinModule()
}