package com.goalify.chat.android.chatrooms.di

import com.goalify.chat.android.chatrooms.ui.ChatRoomsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChatRoomsFragmentProvider {

    @ContributesAndroidInjector(modules = [ChatRoomsFragmentModule::class])
    abstract fun provideChatRoomsFragment(): ChatRoomsFragment
}