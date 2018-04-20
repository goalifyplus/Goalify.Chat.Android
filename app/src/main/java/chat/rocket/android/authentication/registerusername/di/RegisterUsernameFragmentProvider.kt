package com.goalify.chat.android.authentication.registerusername.di

import com.goalify.chat.android.authentication.registerusername.ui.RegisterUsernameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegisterUsernameFragmentProvider {

    @ContributesAndroidInjector(modules = [RegisterUsernameFragmentModule::class])
    abstract fun provideRegisterUsernameFragment(): RegisterUsernameFragment
}