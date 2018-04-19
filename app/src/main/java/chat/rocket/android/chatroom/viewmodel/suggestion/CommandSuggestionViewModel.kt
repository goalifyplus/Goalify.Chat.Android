package com.goalify.chat.android.chatroom.viewmodel.suggestion

import com.goalify.chat.android.widget.autocompletion.model.SuggestionModel

class CommandSuggestionViewModel(text: String,
                                 val description: String,
                                 searchList: List<String>) : SuggestionModel(text, searchList)