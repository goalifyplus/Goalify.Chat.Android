package com.goalify.chat.android.authentication.server.presentation

import com.goalify.chat.android.core.behaviours.LoadingView
import com.goalify.chat.android.core.behaviours.MessageView

interface ServerView : LoadingView, MessageView {

    /**
     * Shows an invalid server URL message.
     */
    fun showInvalidServerUrlMessage()
}