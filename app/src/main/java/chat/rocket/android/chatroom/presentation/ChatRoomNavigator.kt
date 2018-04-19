package com.goalify.chat.android.chatroom.presentation

import com.goalify.chat.android.R
import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.members.ui.newInstance
import com.goalify.chat.android.util.extensions.addFragmentBackStack

class ChatRoomNavigator(internal val activity: ChatRoomActivity) {

    fun toMembersList(chatRoomId: String, chatRoomType: String) {
        activity.addFragmentBackStack("MembersFragment", R.id.fragment_container) {
            newInstance(chatRoomId, chatRoomType)
        }
    }
}