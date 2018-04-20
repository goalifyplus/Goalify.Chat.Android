package com.goalify.chat.android.chatrooms.presentation

import com.goalify.chat.android.core.behaviours.LoadingView
import com.goalify.chat.android.core.behaviours.MessageView
import chat.rocket.core.internal.realtime.socket.model.State
import chat.rocket.core.model.ChatRoom

interface ChatRoomsView : LoadingView, MessageView {

    /**
     * Shows the chat rooms.
     *
     * @param newDataSet The new data set to show.
     */
    suspend fun updateChatRooms(newDataSet: List<ChatRoom>)

    /**
     *  Shows no chat rooms to display.
     */
    fun showNoChatRoomsToDisplay()

    fun showConnectionState(state: State)
}