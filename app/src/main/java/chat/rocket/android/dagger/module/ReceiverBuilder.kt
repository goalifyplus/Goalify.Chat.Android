package com.goalify.chat.android.dagger.module

import com.goalify.chat.android.push.DeleteReceiver
import com.goalify.chat.android.push.DirectReplyReceiver
import com.goalify.chat.android.push.DirectReplyReceiverProvider
import com.goalify.chat.android.push.di.DeleteReceiverProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ReceiverBuilder {

    @ContributesAndroidInjector(modules = [DeleteReceiverProvider::class])
    abstract fun bindDeleteReceiver(): DeleteReceiver

    @ContributesAndroidInjector(modules = [DirectReplyReceiverProvider::class])
    abstract fun bindDirectReplyReceiver(): DirectReplyReceiver
}