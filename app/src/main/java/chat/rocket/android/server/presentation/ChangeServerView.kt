package com.goalify.chat.android.server.presentation

interface ChangeServerView {
    fun showInvalidCredentials()
    fun showProgress()
    fun hideProgress()
}