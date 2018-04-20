package com.goalify.chat.android.push

import com.goalify.chat.android.dagger.module.AppModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DirectReplyReceiverProvider {
    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun provideDirectReplyReceiver(): DirectReplyReceiver
}