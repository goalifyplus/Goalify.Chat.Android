package com.goalify.chat.android.authentication.login.di

import com.goalify.chat.android.authentication.login.ui.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class LoginFragmentProvider {

    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    abstract fun provideLoginFragment(): LoginFragment
}