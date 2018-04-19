package com.goalify.chat.android.server.domain

import com.goalify.chat.android.authentication.domain.model.TokenModel

interface MultiServerTokenRepository {
    fun get(server: String): TokenModel?

    fun save(server: String, token: TokenModel)

    fun clear(server: String)
}