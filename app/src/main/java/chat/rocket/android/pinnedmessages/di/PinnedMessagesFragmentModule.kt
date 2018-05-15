package com.goalify.chat.android.chatroom.di

import android.arch.lifecycle.LifecycleOwner
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.dagger.scope.PerFragment
import com.goalify.chat.android.pinnedmessages.presentation.PinnedMessagesView
import com.goalify.chat.android.pinnedmessages.ui.PinnedMessagesFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.Job

@Module
@PerFragment
class PinnedMessagesFragmentModule {

    @Provides
    fun provideLifecycleOwner(frag: PinnedMessagesFragment): LifecycleOwner {
        return frag
    }

    @Provides
    fun provideCancelStrategy(owner: LifecycleOwner, jobs: Job): CancelStrategy {
        return CancelStrategy(owner, jobs)
    }

    @Provides
    fun providePinnedMessagesView(frag: PinnedMessagesFragment): PinnedMessagesView {
        return frag
    }
}