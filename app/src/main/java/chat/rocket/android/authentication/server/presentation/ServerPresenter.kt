package com.goalify.chat.android.authentication.server.presentation

import com.goalify.chat.android.authentication.presentation.AuthenticationNavigator
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.helper.NetworkHelper
import com.goalify.chat.android.helper.UrlHelper
import com.goalify.chat.android.server.domain.GetAccountsInteractor
import com.goalify.chat.android.server.domain.RefreshSettingsInteractor
import com.goalify.chat.android.server.domain.SaveCurrentServerInteractor
import com.goalify.chat.android.util.extensions.launchUI
import chat.rocket.common.util.ifNull
import javax.inject.Inject

class ServerPresenter @Inject constructor(private val view: ServerView,
                                          private val strategy: CancelStrategy,
                                          private val navigator: AuthenticationNavigator,
                                          private val serverInteractor: SaveCurrentServerInteractor,
                                          private val refreshSettingsInteractor: RefreshSettingsInteractor,
                                          private val getAccountsInteractor: GetAccountsInteractor) {
    fun connect(server: String) {
        if (!UrlHelper.isValidUrl(server)) {
            view.showInvalidServerUrlMessage()
        } else {
            launchUI(strategy) {
                // Check if we already have an account for this server...
                val account = getAccountsInteractor.get().firstOrNull { it.serverUrl == server }
                if (account != null) {
                    navigator.toChatList(server)
                    return@launchUI
                }

                if (NetworkHelper.hasInternetAccess()) {
                    view.showLoading()
                    try {
                        refreshSettingsInteractor.refresh(server)
                        serverInteractor.save(server)
                        navigator.toLogin()
                    } catch (ex: Exception) {
                        ex.message?.let {
                            view.showMessage(it)
                        }.ifNull {
                            view.showGenericErrorMessage()
                        }
                    } finally {
                        view.hideLoading()
                    }
                } else {
                    view.showNoInternetConnection()
                }
            }
        }
    }
}