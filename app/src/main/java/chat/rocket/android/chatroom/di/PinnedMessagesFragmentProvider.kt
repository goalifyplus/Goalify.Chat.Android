package com.goalify.chat.android.chatroom.di

import com.goalify.chat.android.chatroom.ui.PinnedMessagesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PinnedMessagesFragmentProvider {

    @ContributesAndroidInjector(modules = [PinnedMessagesFragmentModule::class])
    abstract fun providePinnedMessageFragment(): PinnedMessagesFragment
}