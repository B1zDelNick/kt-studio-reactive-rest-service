package com.avp.studio.security.repository

import com.avp.studio.security.model.RmToken
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

interface TokenRepository : MongoRepository<RmToken, String> {

    fun findBySeries(series: String): RmToken?
    fun findByUsername(username: String): RmToken?
}