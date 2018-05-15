package com.goalify.chat.android.pinnedmessages.presentation

import com.goalify.chat.android.chatroom.viewmodel.BaseViewModel
import com.goalify.chat.android.core.behaviours.LoadingView
import com.goalify.chat.android.core.behaviours.MessageView

interface PinnedMessagesView : MessageView, LoadingView {

    /**
     * Show list of pinned messages for the current room.
     *
     * @param pinnedMessages The list of pinned messages.
     */
    fun showPinnedMessages(pinnedMessages: List<BaseViewModel<*>>)
}