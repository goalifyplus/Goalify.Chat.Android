package com.goalify.chat.android.authentication.di

import com.goalify.chat.android.authentication.presentation.AuthenticationNavigator
import com.goalify.chat.android.authentication.ui.AuthenticationActivity
import com.goalify.chat.android.dagger.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class AuthenticationModule {

    @Provides
    @PerActivity
    fun provideAuthenticationNavigator(activity: AuthenticationActivity) = AuthenticationNavigator(activity)
}