package com.goalify.chat.android.dagger

import android.app.Application
import com.goalify.chat.android.app.RocketChatApplication
import com.goalify.chat.android.dagger.module.ActivityBuilder
import com.goalify.chat.android.dagger.module.AppModule
import com.goalify.chat.android.dagger.module.ReceiverBuilder
import com.goalify.chat.android.dagger.module.ServiceBuilder
import com.goalify.chat.android.push.FirebaseTokenService
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class, ActivityBuilder::class, ServiceBuilder::class, ReceiverBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: RocketChatApplication)

    fun inject(service: FirebaseTokenService)

    /*@Component.Builder
    abstract class Builder : AndroidInjector.Builder<RocketChatApplication>()*/
}
