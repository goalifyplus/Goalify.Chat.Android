package com.goalify.chat.android.main.presentation

import com.goalify.chat.android.authentication.server.presentation.VersionCheckView
import com.goalify.chat.android.core.behaviours.MessageView
import com.goalify.chat.android.main.viewmodel.NavHeaderViewModel
import com.goalify.chat.android.server.domain.model.Account
import chat.rocket.common.model.UserStatus

interface MainView : MessageView, VersionCheckView {

    /**
     * Shows the current user status.
     *
     * @see [UserStatus]
     */
    fun showUserStatus(userStatus: UserStatus)

    /**
     * Setups the navigation header.
     *
     * @param viewModel The [NavHeaderViewModel].
     * @param accounts The list of accounts.
     */
    fun setupNavHeader(viewModel: NavHeaderViewModel, accounts: List<Account>)

    fun closeServerSelection()
}