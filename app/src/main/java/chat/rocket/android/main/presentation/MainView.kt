package com.goalify.chat.android.main.presentation

import com.goalify.chat.android.authentication.server.presentation.VersionCheckView
import com.goalify.chat.android.core.behaviours.MessageView
import com.goalify.chat.android.main.viewmodel.NavHeaderViewModel
import com.goalify.chat.android.server.domain.model.Account

interface MainView : MessageView, VersionCheckView {
    fun setupNavHeader(model: NavHeaderViewModel, accounts: List<Account>)
    fun closeServerSelection()
}