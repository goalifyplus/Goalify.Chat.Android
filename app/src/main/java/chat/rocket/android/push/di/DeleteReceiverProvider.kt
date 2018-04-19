package com.goalify.chat.android.push.di

import com.goalify.chat.android.dagger.module.AppModule
import com.goalify.chat.android.push.DeleteReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DeleteReceiverProvider {
    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun provideDeleteReceiver(): DeleteReceiver
}