package com.goalify.chat.android.authentication.server.di

import com.goalify.chat.android.authentication.server.ui.ServerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServerFragmentProvider {

    @ContributesAndroidInjector(modules = [ServerFragmentModule::class])
    abstract fun provideServerFragment(): ServerFragment
}