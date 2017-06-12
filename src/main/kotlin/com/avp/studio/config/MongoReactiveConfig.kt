package com.avp.studio.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import javax.annotation.PostConstruct

@EnableReactiveMongoRepositories
@Configuration
class MongoReactiveConfig : AbstractReactiveMongoConfiguration() {

    @Value("\${spring.data.mongodb.database:reactive}")
    private lateinit var DB_NAME: String

    @Bean
    override fun mongoClient(): MongoClient = MongoClients.create()

    override fun getDatabaseName() = DB_NAME
}