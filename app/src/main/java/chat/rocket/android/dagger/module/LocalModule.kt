package com.goalify.chat.android.dagger.module

import android.content.Context
import android.content.SharedPreferences
import com.goalify.chat.android.infrastructure.LocalRepository
import com.goalify.chat.android.infrastructure.SharedPreferencesLocalRepository
import com.goalify.chat.android.server.domain.CurrentServerRepository
import com.goalify.chat.android.server.domain.GetCurrentServerInteractor
import com.goalify.chat.android.server.infraestructure.SharedPrefsCurrentServerRepository
import com.goalify.chat.android.util.AppJsonAdapterFactory
import com.goalify.chat.android.util.TimberLogger
import chat.rocket.common.internal.FallbackSealedClassJsonAdapter
import chat.rocket.common.internal.ISO8601Date
import chat.rocket.common.model.TimestampAdapter
import chat.rocket.common.util.CalendarISO8601Converter
import chat.rocket.common.util.Logger
import chat.rocket.common.util.PlatformLogger
import chat.rocket.core.internal.AttachmentAdapterFactory
import chat.rocket.core.internal.ReactionsAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun providePlatformLogger(): PlatformLogger {
        return TimberLogger
    }

    @Provides
    @Singleton
    fun provideCurrentServerRepository(prefs: SharedPreferences): CurrentServerRepository {
        return SharedPrefsCurrentServerRepository(prefs)
    }

    @Provides
    @Singleton
    fun provideMoshi(
        logger: PlatformLogger,
        currentServerInteractor: GetCurrentServerInteractor
    ): Moshi {
        val url = currentServerInteractor.get() ?: ""
        return Moshi.Builder()
            .add(FallbackSealedClassJsonAdapter.ADAPTER_FACTORY)
            .add(AppJsonAdapterFactory.INSTANCE)
            .add(AttachmentAdapterFactory(Logger(logger, url)))
            .add(
                java.lang.Long::class.java,
                ISO8601Date::class.java,
                TimestampAdapter(CalendarISO8601Converter())
            )
            .add(
                Long::class.java,
                ISO8601Date::class.java,
                TimestampAdapter(CalendarISO8601Converter())
            )
            .add(ReactionsAdapter())
            .build()
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("rocket.chat", Context.MODE_PRIVATE)
    }



    @Provides
    @Singleton
    fun provideLocalRepository(sharedPreferences: SharedPreferences, moshi: Moshi): LocalRepository {
        return SharedPreferencesLocalRepository(sharedPreferences, moshi)
    }
}