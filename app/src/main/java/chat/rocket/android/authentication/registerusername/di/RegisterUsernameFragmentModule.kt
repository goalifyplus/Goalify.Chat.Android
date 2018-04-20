package com.goalify.chat.android.authentication.registerusername.di

import android.arch.lifecycle.LifecycleOwner
import com.goalify.chat.android.authentication.registerusername.presentation.RegisterUsernameView
import com.goalify.chat.android.authentication.registerusername.ui.RegisterUsernameFragment
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.dagger.scope.PerFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.Job

@Module
@PerFragment
class RegisterUsernameFragmentModule {

    @Provides
    fun registerUsernameView(frag: RegisterUsernameFragment): RegisterUsernameView {
        return frag
    }

    @Provides
    fun provideLifecycleOwner(frag: RegisterUsernameFragment): LifecycleOwner {
        return frag
    }

    @Provides
    fun provideCancelStrategy(owner: LifecycleOwner, jobs: Job): CancelStrategy {
        return CancelStrategy(owner, jobs)
    }
}