package com.avp.studio.security.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class User(
        var email: String,
        var password: String,
        var firstname: String,
        var lastname: String,
        var enabled: Boolean = false,
        var lastPasswordResetDate: Date,
        var authorities: List<Authority> = emptyList(),
        @Id var id: String? = null

)