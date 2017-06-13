package com.avp.studio.security.service

import com.avp.studio.security.model.RmToken
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import com.avp.studio.security.repository.TokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
class RmTokenService : PersistentTokenRepository {

    @Autowired
    private lateinit var repository: TokenRepository

    override fun createNewToken(token: PersistentRememberMeToken) {
        repository.save(RmToken(
                token.username,
                token.series,
                token.tokenValue,
                token.date))
    }

    override fun updateToken(series: String, value: String, lastUsed: Date) {

        val token = repository.findBySeries(series)

        if (token != null)
            repository.save(RmToken(token.username, series, value, lastUsed, token.id!!))
    }

    override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? = repository.findBySeries(seriesId)

    override fun removeUserTokens(username: String) {

        val token = repository.findByUsername(username)

        if (token != null)
            repository.delete(token)

    }
}