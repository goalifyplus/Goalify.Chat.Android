package com.goalify.chat.android.main.presentation

import android.content.Context
import com.goalify.chat.android.R
import com.goalify.chat.android.authentication.ui.newServerIntent
import com.goalify.chat.android.chatroom.ui.chatRoomIntent
import com.goalify.chat.android.chatrooms.ui.ChatRoomsFragment
import com.goalify.chat.android.main.ui.MainActivity
import com.goalify.chat.android.profile.ui.ProfileFragment
import com.goalify.chat.android.server.ui.changeServerIntent
import com.goalify.chat.android.settings.ui.SettingsFragment
import com.goalify.chat.android.util.extensions.addFragment

class MainNavigator(internal val activity: MainActivity) {

    fun toChatList() {
        activity.addFragment("ChatRoomsFragment", R.id.fragment_container) {
            ChatRoomsFragment.newInstance()
        }
    }

    fun toUserProfile() {
        activity.addFragment("ProfileFragment", R.id.fragment_container) {
            ProfileFragment.newInstance()
        }
    }

    fun toSettings() {
        activity.addFragment("SettingsFragment", R.id.fragment_container) {
            SettingsFragment.newInstance()
        }
    }

    fun toChatRoom(chatRoomId: String,
                   chatRoomName: String,
                   chatRoomType: String,
                   isChatRoomReadOnly: Boolean,
                   chatRoomLastSeen: Long,
                   isChatRoomSubscribed: Boolean) {
        activity.startActivity(activity.chatRoomIntent(chatRoomId, chatRoomName, chatRoomType,
                isChatRoomReadOnly, chatRoomLastSeen, isChatRoomSubscribed))
        activity.overridePendingTransition(R.anim.open_enter, R.anim.open_exit)
    }

    fun toNewServer(serverUrl: String? = null) {
        activity.startActivity(activity.changeServerIntent(serverUrl))
        activity.finish()
    }

    fun toServerScreen() {
        activity.startActivity(activity.newServerIntent())
    }
}