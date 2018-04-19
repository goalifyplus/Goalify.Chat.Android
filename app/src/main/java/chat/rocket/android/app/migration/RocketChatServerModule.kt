package com.goalify.chat.android.app.migration

import com.goalify.chat.android.app.migration.model.RealmBasedServerInfo
import io.realm.annotations.RealmModule

@RealmModule(library = true, classes = arrayOf(RealmBasedServerInfo::class))
class RocketChatServerModule