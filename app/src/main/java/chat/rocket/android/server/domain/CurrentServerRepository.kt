package com.goalify.chat.android.server.domain

interface CurrentServerRepository {
    fun save(url: String)
    fun get(): String?
    fun clear()
}