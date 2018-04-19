package com.goalify.chat.android.push.di

import com.goalify.chat.android.dagger.module.AppModule
import com.goalify.chat.android.push.GcmListenerService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class GcmListenerServiceProvider {
    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun provideGcmListenerService(): GcmListenerService
}