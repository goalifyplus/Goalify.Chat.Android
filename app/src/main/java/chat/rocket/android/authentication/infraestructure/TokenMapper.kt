package com.goalify.chat.android.authentication.infraestructure

import com.goalify.chat.android.authentication.domain.model.TokenModel
import com.goalify.chat.android.util.DataToDomain
import chat.rocket.common.model.Token

object TokenMapper : DataToDomain<Token, TokenModel> {
    override fun translate(data: Token): TokenModel {
        return TokenModel(data.userId, data.authToken)
    }
}