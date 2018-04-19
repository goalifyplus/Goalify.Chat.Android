package com.goalify.chat.android.members.presentation

import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.member.ui.newInstance

class MembersNavigator(internal val activity: ChatRoomActivity) {

    fun toMemberDetails(avatarUri: String, realName: String, username: String, email: String, utcOffset: String) {
        activity.apply {
            newInstance(avatarUri, realName, username, email, utcOffset)
                .show(supportFragmentManager, "MemberBottomSheetFragment")
        }
    }
}
