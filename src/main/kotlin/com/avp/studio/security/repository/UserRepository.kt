package com.avp.studio.security.repository

import com.avp.studio.security.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

/**
 * Repository interface to manage {@link User} instances.
 *
 * @author Andrei Vasilevich
 */
interface UserRepository  : MongoRepository<User, String> {

    /**
     * String query selecting one entity.
     *
     * @param email
     * @return
     */
    @Query("{'email':?0}")
    fun findByEmail(email: String): Optional<User>
}