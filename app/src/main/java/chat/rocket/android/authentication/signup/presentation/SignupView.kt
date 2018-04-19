package com.goalify.chat.android.authentication.signup.presentation

import com.goalify.chat.android.core.behaviours.InternetView
import com.goalify.chat.android.core.behaviours.LoadingView
import com.goalify.chat.android.core.behaviours.MessageView

interface SignupView : LoadingView, MessageView, InternetView {

    /**
     * Alerts the user about a blank name.
     */
    fun alertBlankName()

    /**
     * Alerts the user about a blank username.
     */
    fun alertBlankUsername()

    /**
     * Alerts the user about a empty password.
     */
    fun alertEmptyPassword()

    /**
     * Alerts the user about a blank email.
     */
    fun alertBlankEmail()
}