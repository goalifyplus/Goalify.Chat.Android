package com.goalify.chat.android.server.domain.model

data class Server(
    val id: Long,
    val name: String,
    val url: String,
    val avatar: String
)