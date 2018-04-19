package com.goalify.chat.android.profile.di

import com.goalify.chat.android.profile.ui.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileFragmentProvider {

    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    abstract fun provideProfileFragment(): ProfileFragment
}