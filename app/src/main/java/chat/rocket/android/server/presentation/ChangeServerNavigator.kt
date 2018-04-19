package com.goalify.chat.android.server.presentation

import android.content.Intent
import com.goalify.chat.android.authentication.ui.newServerIntent
import com.goalify.chat.android.main.ui.MainActivity
import com.goalify.chat.android.server.ui.ChangeServerActivity

class ChangeServerNavigator (internal val activity: ChangeServerActivity) {
    fun toServerScreen() {
        activity.startActivity(activity.newServerIntent())
        activity.finish()
    }

    fun toChatRooms() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }

}