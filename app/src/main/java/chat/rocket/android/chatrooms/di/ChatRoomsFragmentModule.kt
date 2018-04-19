package com.goalify.chat.android.chatrooms.di

import android.arch.lifecycle.LifecycleOwner
import com.goalify.chat.android.chatrooms.presentation.ChatRoomsView
import com.goalify.chat.android.chatrooms.ui.ChatRoomsFragment
import com.goalify.chat.android.dagger.scope.PerFragment
import dagger.Module
import dagger.Provides

@Module
@PerFragment
class ChatRoomsFragmentModule {

    @Provides
    fun chatRoomsView(frag: ChatRoomsFragment): ChatRoomsView {
        return frag
    }

    @Provides
    fun provideLifecycleOwner(frag: ChatRoomsFragment): LifecycleOwner {
        return frag
    }
}