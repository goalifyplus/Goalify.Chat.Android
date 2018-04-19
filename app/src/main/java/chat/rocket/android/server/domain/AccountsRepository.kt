package com.goalify.chat.android.server.domain

import com.goalify.chat.android.server.domain.model.Account

interface AccountsRepository {
    suspend fun save(account: Account)
    suspend fun load(): List<Account>
    suspend fun remove(serverUrl: String)
}