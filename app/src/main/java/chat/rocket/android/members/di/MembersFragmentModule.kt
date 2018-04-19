package com.goalify.chat.android.members.di

import android.arch.lifecycle.LifecycleOwner
import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.dagger.scope.PerFragment
import com.goalify.chat.android.members.presentation.MembersNavigator
import com.goalify.chat.android.members.presentation.MembersView
import com.goalify.chat.android.members.ui.MembersFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.Job

@Module
@PerFragment
class MembersFragmentModule {

    @Provides
    fun provideChatRoomNavigator(activity: ChatRoomActivity) = MembersNavigator(activity)

    @Provides
    fun membersView(frag: MembersFragment): MembersView {
        return frag
    }

    @Provides
    fun provideLifecycleOwner(frag: MembersFragment): LifecycleOwner {
        return frag
    }

    @Provides
    fun provideCancelStrategy(owner: LifecycleOwner, jobs: Job): CancelStrategy {
        return CancelStrategy(owner, jobs)
    }
}