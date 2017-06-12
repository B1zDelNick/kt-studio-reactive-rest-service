package com.avp.studio.security.repository

import com.avp.studio.security.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface UserRepository  : MongoRepository<User, String> {

    @Query("{'email':?0}")
    fun findByEmail(email: String): Optional<User>
}