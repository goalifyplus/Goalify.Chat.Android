package com.goalify.chat.android.chatroom.viewmodel.suggestion

import com.goalify.chat.android.widget.autocompletion.model.SuggestionModel

class ChatRoomSuggestionViewModel(text: String,
                                  val fullName: String,
                                  val name: String,
                                  searchList: List<String>) : SuggestionModel(text, searchList, false) {
}