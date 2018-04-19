package com.goalify.chat.android.dagger.module

import android.app.Application
import android.app.NotificationManager
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import androidx.content.systemService
import com.goalify.chat.android.BuildConfig
import com.goalify.chat.android.R
import com.goalify.chat.android.app.RocketChatDatabase
import com.goalify.chat.android.authentication.infraestructure.SharedPreferencesMultiServerTokenRepository
import com.goalify.chat.android.authentication.infraestructure.SharedPreferencesTokenRepository
import com.goalify.chat.android.dagger.qualifier.ForFresco
import com.goalify.chat.android.helper.FrescoAuthInterceptor
import com.goalify.chat.android.helper.MessageParser
import com.goalify.chat.android.infrastructure.LocalRepository
import com.goalify.chat.android.infrastructure.SharedPrefsLocalRepository
import com.goalify.chat.android.push.GroupedPush
import com.goalify.chat.android.server.domain.AccountsRepository
import com.goalify.chat.android.server.domain.ChatRoomsRepository
import com.goalify.chat.android.server.domain.CurrentServerRepository
import com.goalify.chat.android.server.domain.GetCurrentServerInteractor
import com.goalify.chat.android.server.domain.GetPermissionsInteractor
import com.goalify.chat.android.server.domain.MessagesRepository
import com.goalify.chat.android.server.domain.MultiServerTokenRepository
import com.goalify.chat.android.server.domain.RoomRepository
import com.goalify.chat.android.server.domain.SettingsRepository
import com.goalify.chat.android.server.domain.TokenRepository
import com.goalify.chat.android.server.domain.UsersRepository
import com.goalify.chat.android.server.infraestructure.MemoryChatRoomsRepository
import com.goalify.chat.android.server.infraestructure.MemoryMessagesRepository
import com.goalify.chat.android.server.infraestructure.MemoryRoomRepository
import com.goalify.chat.android.server.infraestructure.MemoryUsersRepository
import com.goalify.chat.android.server.infraestructure.ServerDao
import com.goalify.chat.android.server.infraestructure.SharedPreferencesAccountsRepository
import com.goalify.chat.android.server.infraestructure.SharedPreferencesSettingsRepository
import com.goalify.chat.android.server.infraestructure.SharedPrefsCurrentServerRepository
import com.goalify.chat.android.util.AppJsonAdapterFactory
import com.goalify.chat.android.util.TimberLogger
import chat.rocket.common.internal.FallbackSealedClassJsonAdapter
import chat.rocket.common.util.PlatformLogger
import chat.rocket.core.RocketChatClient
import com.facebook.drawee.backends.pipeline.DraweeConfig
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.Job
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.noties.markwon.SpannableConfiguration
import ru.noties.markwon.il.AsyncDrawableLoader
import ru.noties.markwon.spans.SpannableTheme
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRocketChatClient(okHttpClient: OkHttpClient, repository: TokenRepository, logger: PlatformLogger): RocketChatClient {
        return RocketChatClient.create {
            httpClient = okHttpClient
            tokenRepository = repository
            platformLogger = logger

            // TODO remove
            restUrl = "https://demo.goalify.chat"
        }
    }

    @Provides
    @Singleton
    fun provideRocketChatDatabase(context: Application): RocketChatDatabase {
        return Room.databaseBuilder(context, RocketChatDatabase::class.java, "rocketchat-db").build()
    }

    @Provides
    fun provideJob(): Job {
        return Job()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideServerDao(database: RocketChatDatabase): ServerDao {
        return database.serverDao()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.d(message)
        })
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            // TODO - change to HEADERS on production...
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(logger)
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @ForFresco
    @Singleton
    fun provideFrescoAuthIntercepter(tokenRepository: TokenRepository, currentServerInteractor: GetCurrentServerInteractor): Interceptor {
        return FrescoAuthInterceptor(tokenRepository, currentServerInteractor)
    }

    @Provides
    @ForFresco
    @Singleton
    fun provideFrescoOkHttpClient(okHttpClient: OkHttpClient, @ForFresco authInterceptor: Interceptor): OkHttpClient {
        return okHttpClient.newBuilder().apply {
            //addInterceptor(authInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideImagePipelineConfig(context: Context, @ForFresco okHttpClient: OkHttpClient): ImagePipelineConfig {
        val listeners = HashSet<RequestListener>()
        listeners.add(RequestLoggingListener())

        return OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient)
                .setRequestListeners(listeners)
                .setDownsampleEnabled(true)
                //.experiment().setBitmapPrepareToDraw(true).experiment()
                .experiment().setPartialImageCachingEnabled(true).build()
    }

    @Provides
    @Singleton
    fun provideDraweeConfig(): DraweeConfig {
        return DraweeConfig.newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideTokenRepository(prefs: SharedPreferences, moshi: Moshi): TokenRepository {
        return SharedPreferencesTokenRepository(prefs, moshi)
    }

    @Provides
    @Singleton
    fun providePlatformLogger(): PlatformLogger {
        return TimberLogger
    }

    @Provides
    fun provideSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences("rocket.chat", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(prefs: SharedPreferences): LocalRepository {
        return SharedPrefsLocalRepository(prefs)
    }

    @Provides
    @Singleton
    fun provideCurrentServerRepository(prefs: SharedPreferences): CurrentServerRepository {
        return SharedPrefsCurrentServerRepository(prefs)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(localRepository: LocalRepository): SettingsRepository {
        return SharedPreferencesSettingsRepository(localRepository)
    }

    @Provides
    @Singleton
    fun provideRoomRepository(): RoomRepository {
        return MemoryRoomRepository()
    }

    @Provides
    @Singleton
    fun provideChatRoomRepository(): ChatRoomsRepository {
        return MemoryChatRoomsRepository()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(FallbackSealedClassJsonAdapter.ADAPTER_FACTORY)
                .add(AppJsonAdapterFactory.INSTANCE)
                .build()
    }

    @Provides
    @Singleton
    fun provideMultiServerTokenRepository(repository: LocalRepository, moshi: Moshi): MultiServerTokenRepository {
        return SharedPreferencesMultiServerTokenRepository(repository, moshi)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(): MessagesRepository {
        return MemoryMessagesRepository()
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UsersRepository {
        return MemoryUsersRepository()
    }

    @Provides
    @Singleton
    fun provideConfiguration(context: Application, client: OkHttpClient): SpannableConfiguration {
        val res = context.resources
        return SpannableConfiguration.builder(context)
                .asyncDrawableLoader(AsyncDrawableLoader.builder()
                        .client(client)
                        .executorService(Executors.newCachedThreadPool())
                        .resources(res)
                        .build())
                .theme(SpannableTheme.builder()
                        .linkColor(res.getColor(R.color.colorAccent))
                        .build())
                .build()
    }

    @Provides
    @Singleton
    fun provideMessageParser(context: Application, configuration: SpannableConfiguration): MessageParser {
        return MessageParser(context, configuration)
    }

    @Provides
    @Singleton
    fun providePermissionInteractor(settingsRepository: SettingsRepository, serverRepository: CurrentServerRepository): GetPermissionsInteractor {
        return GetPermissionsInteractor(settingsRepository, serverRepository)
    }

    @Provides
    @Singleton
    fun provideAccountsRepository(preferences: SharedPreferences, moshi: Moshi): AccountsRepository =
            SharedPreferencesAccountsRepository(preferences, moshi)

    @Provides
    fun provideNotificationManager(context: Context): NotificationManager = context.systemService()

    @Provides
    @Singleton
    fun provideGroupedPush() = GroupedPush()
}