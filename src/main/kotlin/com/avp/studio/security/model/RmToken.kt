package com.avp.studio.security.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document
@CompoundIndexes(
        CompoundIndex(name = "i_username", def = "{'username': 1}"),
        CompoundIndex(name = "i_series", def = "{'series': 1}"))
class RmToken @PersistenceConstructor constructor(
        username: String,
        series: String,
        tokenValue: String,
        date: Date,
        @Id val id: String? = null) : PersistentRememberMeToken(username, series, tokenValue, date)