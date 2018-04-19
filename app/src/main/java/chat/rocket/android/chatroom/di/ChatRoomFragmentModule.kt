package com.goalify.chat.android.chatroom.di

import android.arch.lifecycle.LifecycleOwner
import com.goalify.chat.android.chatroom.presentation.ChatRoomNavigator
import com.goalify.chat.android.chatroom.presentation.ChatRoomView
import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.chatroom.ui.ChatRoomFragment
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.dagger.scope.PerFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.Job

@Module
@PerFragment
class ChatRoomFragmentModule {

    @Provides
    fun provideChatRoomNavigator(activity: ChatRoomActivity) = ChatRoomNavigator(activity)

    @Provides
    fun chatRoomView(frag: ChatRoomFragment): ChatRoomView {
        return frag
    }

    @Provides
    fun provideLifecycleOwner(frag: ChatRoomFragment): LifecycleOwner {
        return frag
    }

    @Provides
    fun provideCancelStrategy(owner: LifecycleOwner, jobs: Job): CancelStrategy {
        return CancelStrategy(owner, jobs)
    }
}