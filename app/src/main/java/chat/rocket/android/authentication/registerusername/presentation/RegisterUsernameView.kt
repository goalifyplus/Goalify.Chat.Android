package com.goalify.chat.android.authentication.registerusername.presentation

import com.goalify.chat.android.core.behaviours.LoadingView
import com.goalify.chat.android.core.behaviours.MessageView

interface RegisterUsernameView : LoadingView, MessageView {

    /**
     * Alerts the user about a blank username.
     */
    fun alertBlankUsername()
}