package com.goalify.chat.android.dagger.module

import com.goalify.chat.android.authentication.di.AuthenticationModule
import com.goalify.chat.android.authentication.login.di.LoginFragmentProvider
import com.goalify.chat.android.authentication.registerusername.di.RegisterUsernameFragmentProvider
import com.goalify.chat.android.authentication.server.di.ServerFragmentProvider
import com.goalify.chat.android.authentication.signup.di.SignupFragmentProvider
import com.goalify.chat.android.authentication.twofactor.di.TwoFAFragmentProvider
import com.goalify.chat.android.authentication.ui.AuthenticationActivity
import com.goalify.chat.android.chatroom.di.ChatRoomFragmentProvider
import com.goalify.chat.android.chatroom.di.PinnedMessagesFragmentProvider
import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.chatroom.ui.PinnedMessagesActivity
import com.goalify.chat.android.chatrooms.di.ChatRoomsFragmentProvider
import com.goalify.chat.android.dagger.scope.PerActivity
import com.goalify.chat.android.main.di.MainModule
import com.goalify.chat.android.main.ui.MainActivity
import com.goalify.chat.android.members.di.MembersFragmentProvider
import com.goalify.chat.android.profile.di.ProfileFragmentProvider
import com.goalify.chat.android.server.di.ChangeServerModule
import com.goalify.chat.android.server.ui.ChangeServerActivity
import com.goalify.chat.android.settings.password.di.PasswordFragmentProvider
import com.goalify.chat.android.settings.password.ui.PasswordActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [AuthenticationModule::class,
        ServerFragmentProvider::class,
        LoginFragmentProvider::class,
        RegisterUsernameFragmentProvider::class,
        SignupFragmentProvider::class,
        TwoFAFragmentProvider::class
    ])
    abstract fun bindAuthenticationActivity(): AuthenticationActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class,
        ChatRoomsFragmentProvider::class,
        ProfileFragmentProvider::class
    ])
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ChatRoomFragmentProvider::class, MembersFragmentProvider::class])
    abstract fun bindChatRoomActivity(): ChatRoomActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [PinnedMessagesFragmentProvider::class])
    abstract fun bindPinnedMessagesActivity(): PinnedMessagesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [PasswordFragmentProvider::class])
    abstract fun bindPasswordActivity(): PasswordActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ChangeServerModule::class])
    abstract fun bindChangeServerActivity(): ChangeServerActivity
}