package com.goalify.chat.android.dagger.module

import com.goalify.chat.android.push.FirebaseTokenService
import com.goalify.chat.android.push.GcmListenerService
import com.goalify.chat.android.push.di.FirebaseTokenServiceProvider
import com.goalify.chat.android.push.di.GcmListenerServiceProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class ServiceBuilder {

    @ContributesAndroidInjector(modules = [FirebaseTokenServiceProvider::class])
    abstract fun bindFirebaseTokenService(): FirebaseTokenService

    @ContributesAndroidInjector(modules = [GcmListenerServiceProvider::class])
    abstract fun bindGcmListenerService(): GcmListenerService
}