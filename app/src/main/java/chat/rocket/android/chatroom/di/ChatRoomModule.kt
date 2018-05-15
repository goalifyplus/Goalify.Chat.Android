package com.goalify.chat.android.chatroom.di

import com.goalify.chat.android.chatroom.presentation.ChatRoomNavigator
import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.dagger.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
@PerActivity
class ChatRoomModule {
    @Provides
    fun provideChatRoomNavigator(activity: ChatRoomActivity) = ChatRoomNavigator(activity)
}