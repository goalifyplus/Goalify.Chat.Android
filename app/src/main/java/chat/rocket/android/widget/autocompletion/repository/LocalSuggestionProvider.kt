package com.goalify.chat.android.widget.autocompletion.repository

interface LocalSuggestionProvider {
    fun find(prefix: String)
}