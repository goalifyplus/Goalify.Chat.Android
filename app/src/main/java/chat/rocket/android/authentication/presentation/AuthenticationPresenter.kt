package com.goalify.chat.android.authentication.presentation

import com.goalify.chat.android.infrastructure.LocalRepository
import com.goalify.chat.android.server.domain.GetAccountInteractor
import com.goalify.chat.android.server.domain.GetCurrentServerInteractor
import com.goalify.chat.android.server.domain.SettingsRepository
import com.goalify.chat.android.server.domain.TokenRepository
import javax.inject.Inject

class AuthenticationPresenter @Inject constructor(
    private val navigator: AuthenticationNavigator,
    private val getCurrentServerInteractor: GetCurrentServerInteractor,
    private val getAccountInteractor: GetAccountInteractor,
    private val settingsRepository: SettingsRepository,
    private val localRepository: LocalRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun loadCredentials(newServer: Boolean, callback: (authenticated: Boolean) -> Unit) {
        val currentServer = getCurrentServerInteractor.get()
        val serverToken = currentServer?.let { tokenRepository.get(currentServer) }
        val settings = currentServer?.let { settingsRepository.get(currentServer) }
        val account = currentServer?.let { getAccountInteractor.get(currentServer) }

        account?.let {
            localRepository.save(LocalRepository.CURRENT_USERNAME_KEY, account.userName)
        }

        if (newServer || currentServer == null || serverToken == null || settings == null || account?.userName == null) {
            callback(false)
        } else {
            callback(true)
            navigator.toChatList()
        }
    }
}