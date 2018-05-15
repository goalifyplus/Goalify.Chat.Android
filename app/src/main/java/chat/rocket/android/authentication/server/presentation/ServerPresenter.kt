package com.goalify.chat.android.authentication.server.presentation

import com.goalify.chat.android.authentication.domain.model.LoginDeepLinkInfo
import com.goalify.chat.android.authentication.presentation.AuthenticationNavigator
import com.goalify.chat.android.core.behaviours.showMessage
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.server.domain.GetAccountsInteractor
import com.goalify.chat.android.server.domain.RefreshSettingsInteractor
import com.goalify.chat.android.server.domain.SaveCurrentServerInteractor
import com.goalify.chat.android.server.infraestructure.RocketChatClientFactory
import com.goalify.chat.android.server.presentation.CheckServerPresenter
import com.goalify.chat.android.util.extensions.isValidUrl
import com.goalify.chat.android.util.extensions.launchUI
import javax.inject.Inject

class ServerPresenter @Inject constructor(private val view: ServerView,
                                          private val strategy: CancelStrategy,
                                          private val navigator: AuthenticationNavigator,
                                          private val serverInteractor: SaveCurrentServerInteractor,
                                          private val refreshSettingsInteractor: RefreshSettingsInteractor,
                                          private val getAccountsInteractor: GetAccountsInteractor,
                                          factory: RocketChatClientFactory
) : CheckServerPresenter(strategy, factory, view) {

    fun checkServer(server: String) {
        if (!server.isValidUrl()) {
            view.showInvalidServerUrlMessage()
        } else {
            view.showLoading()
            checkServerInfo(server)
        }
    }

    fun connect(server: String) {
        connectToServer(server) {
            navigator.toLogin()
        }
    }

    private fun connectToServer(server: String, block: () -> Unit) {
        if (!server.isValidUrl()) {
            view.showInvalidServerUrlMessage()
        } else {
            launchUI(strategy) {
                // Check if we already have an account for this server...
                val account = getAccountsInteractor.get().firstOrNull { it.serverUrl == server }
                if (account != null) {
                    navigator.toChatList(server)
                    return@launchUI
                }

                view.showLoading()
                try {
                    refreshSettingsInteractor.refresh(server)
                    serverInteractor.save(server)
                    block()
                } catch (ex: Exception) {
                    view.showMessage(ex)
                } finally {
                    view.hideLoading()
                }
            }
        }
    }

    fun deepLink(deepLinkInfo: LoginDeepLinkInfo) {
        connectToServer(deepLinkInfo.url) {
            navigator.toLogin(deepLinkInfo)
        }
    }
}