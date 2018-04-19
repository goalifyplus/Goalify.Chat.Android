package com.goalify.chat.android.members.di

import com.goalify.chat.android.members.ui.MembersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MembersFragmentProvider {

    @ContributesAndroidInjector(modules = [MembersFragmentModule::class])
    abstract fun provideMembersFragment(): MembersFragment
}