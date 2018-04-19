package com.goalify.chat.android.push.di

import com.goalify.chat.android.dagger.module.AppModule
import com.goalify.chat.android.push.FirebaseTokenService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class FirebaseTokenServiceProvider {
    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun provideFirebaseTokenService(): FirebaseTokenService
}