package com.goalify.chat.android.chatroom.viewmodel

interface BaseAttachmentViewModel<out T> : BaseViewModel<T> {
    val attachmentUrl: String
}