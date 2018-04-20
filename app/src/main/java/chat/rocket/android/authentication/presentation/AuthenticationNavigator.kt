package com.goalify.chat.android.authentication.presentation

import android.content.Intent
import com.goalify.chat.android.R
import com.goalify.chat.android.authentication.domain.model.LoginDeepLinkInfo
import com.goalify.chat.android.authentication.login.ui.LoginFragment
import com.goalify.chat.android.authentication.registerusername.ui.RegisterUsernameFragment
import com.goalify.chat.android.authentication.signup.ui.SignupFragment
import com.goalify.chat.android.authentication.twofactor.ui.TwoFAFragment
import com.goalify.chat.android.authentication.ui.AuthenticationActivity
import com.goalify.chat.android.authentication.ui.newServerIntent
import com.goalify.chat.android.main.ui.MainActivity
import com.goalify.chat.android.server.ui.changeServerIntent
import com.goalify.chat.android.util.extensions.addFragmentBackStack
import com.goalify.chat.android.webview.ui.webViewIntent

class AuthenticationNavigator(internal val activity: AuthenticationActivity) {

    fun toLogin() {
        activity.addFragmentBackStack("LoginFragment", R.id.fragment_container) {
            LoginFragment.newInstance()
        }
    }

    fun toLogin(deepLinkInfo: LoginDeepLinkInfo) {
        activity.addFragmentBackStack("LoginFragment", R.id.fragment_container) {
            LoginFragment.newInstance(deepLinkInfo)
        }
    }

    fun toTwoFA(username: String, password: String) {
        activity.addFragmentBackStack("TwoFAFragment", R.id.fragment_container) {
            TwoFAFragment.newInstance(username, password)
        }
    }

    fun toSignUp() {
        activity.addFragmentBackStack("SignupFragment", R.id.fragment_container) {
            SignupFragment.newInstance()
        }
    }

    fun toWebPage(url: String) {
        activity.startActivity(activity.webViewIntent(url))
        activity.overridePendingTransition(R.anim.slide_up, R.anim.hold)
    }

    fun toRegisterUsername(userId: String, authToken: String) {
        activity.addFragmentBackStack("RegisterUsernameFragment", R.id.fragment_container) {
            RegisterUsernameFragment.newInstance(userId, authToken)
        }
    }

    fun toChatList() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }

    fun toChatList(serverUrl: String) {
        activity.startActivity(activity.changeServerIntent(serverUrl))
        activity.finish()
    }

    fun toServerScreen() {
        activity.startActivity(activity.newServerIntent())
        activity.finish()
    }
}