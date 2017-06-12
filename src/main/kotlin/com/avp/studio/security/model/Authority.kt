package com.avp.studio.security.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Authority(
        var name: AuthorityName = AuthorityName.ROLE_USER,
        @Id var id: String ? = null)
