package com.goalify.chat.android.dagger.module

import android.content.Context
import android.content.SharedPreferences
import com.goalify.chat.android.infrastructure.LocalRepository
import com.goalify.chat.android.infrastructure.SharedPrefsLocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("rocket.chat", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(prefs: SharedPreferences): LocalRepository {
        return SharedPrefsLocalRepository(prefs)
    }
}