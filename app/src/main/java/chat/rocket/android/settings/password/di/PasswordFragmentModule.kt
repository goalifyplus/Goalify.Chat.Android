package com.goalify.chat.android.settings.password.di

import android.arch.lifecycle.LifecycleOwner
import com.goalify.chat.android.core.lifecycle.CancelStrategy
import com.goalify.chat.android.dagger.scope.PerFragment
import com.goalify.chat.android.settings.password.presentation.PasswordView
import com.goalify.chat.android.settings.password.ui.PasswordFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.Job

@Module
@PerFragment
class PasswordFragmentModule {
    @Provides
    fun passwordView(frag: PasswordFragment): PasswordView {
        return frag
    }

    @Provides
    fun settingsLifecycleOwner(frag: PasswordFragment): LifecycleOwner {
        return frag
    }

    @Provides
    fun provideCancelStrategy(owner: LifecycleOwner, jobs: Job): CancelStrategy {
        return CancelStrategy(owner, jobs)
    }
}
