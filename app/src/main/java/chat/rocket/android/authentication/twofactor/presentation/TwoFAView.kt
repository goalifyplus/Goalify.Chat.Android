package com.goalify.chat.android.authentication.twofactor.presentation

import com.goalify.chat.android.core.behaviours.InternetView
import com.goalify.chat.android.core.behaviours.LoadingView
import com.goalify.chat.android.core.behaviours.MessageView

interface TwoFAView : LoadingView, MessageView, InternetView {

    /**
     * Alerts the user about a blank Two Factor Authentication code.
     */
    fun alertBlankTwoFactorAuthenticationCode()

    /**
     * Alerts the user about an invalid inputted Two Factor Authentication code.
     */
    fun alertInvalidTwoFactorAuthenticationCode()
}