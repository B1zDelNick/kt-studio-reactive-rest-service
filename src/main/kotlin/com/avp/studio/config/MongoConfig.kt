package com.avp.studio.config

import com.mongodb.MongoClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import com.mongodb.Mongo
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import javax.annotation.PostConstruct


@Configuration
@EnableMongoRepositories(*arrayOf("com.avp.studio.security.repository"))
class MongoConfig : AbstractMongoConfiguration() {

    @Value("\${spring.data.mongodb.database:reactive}")
    private lateinit var DB_NAME: String

    @Value("\${spring.data.mongodb.host:localhost}")
    private lateinit var HOST: String

    @Value("\${spring.data.mongodb.port:27017}")
    private var PORT: Int = 27017

    @Throws(ClassNotFoundException::class)
    override fun mongoMappingContext(): MongoMappingContext = super.mongoMappingContext()

    @Bean
    override fun mongoClient(): MongoClient = MongoClient(HOST + ":" + PORT)

    override fun getDatabaseName() = DB_NAME
}