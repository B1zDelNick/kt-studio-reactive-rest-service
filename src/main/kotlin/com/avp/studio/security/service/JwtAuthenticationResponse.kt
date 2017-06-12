package com.avp.studio.security.service

import java.io.Serializable

class JwtAuthenticationResponse(val token: String) : java.io.Serializable {

    companion object {
        private const val serialVersionUID = 1250166508152483573L
    }
}