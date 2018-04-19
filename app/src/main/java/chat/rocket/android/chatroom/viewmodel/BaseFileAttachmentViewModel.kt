package com.goalify.chat.android.chatroom.viewmodel

interface BaseFileAttachmentViewModel<out T> : BaseViewModel<T> {
    val attachmentUrl: String
    val attachmentTitle: CharSequence
    val id: Long
}