package com.goalify.chat.android.server.domain

import com.goalify.chat.android.server.domain.model.Account
import javax.inject.Inject

class SaveAccountInteractor @Inject constructor(val repository: AccountsRepository) {
    suspend fun save(account: Account) = repository.save(account)
}