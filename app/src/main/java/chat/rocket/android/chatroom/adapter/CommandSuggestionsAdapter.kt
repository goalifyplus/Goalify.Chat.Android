package com.goalify.chat.android.chatroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.goalify.chat.android.R
import com.goalify.chat.android.chatroom.adapter.CommandSuggestionsAdapter.CommandSuggestionsViewHolder
import com.goalify.chat.android.chatroom.viewmodel.suggestion.CommandSuggestionViewModel
import com.goalify.chat.android.widget.autocompletion.model.SuggestionModel
import com.goalify.chat.android.widget.autocompletion.ui.BaseSuggestionViewHolder
import com.goalify.chat.android.widget.autocompletion.ui.SuggestionsAdapter

class CommandSuggestionsAdapter : SuggestionsAdapter<CommandSuggestionsViewHolder>(token = "/",
        constraint = CONSTRAINT_BOUND_TO_START, threshold = RESULT_COUNT_UNLIMITED) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandSuggestionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.suggestion_command_item, parent,
                false)
        return CommandSuggestionsViewHolder(view)
    }

    class CommandSuggestionsViewHolder(view: View) : BaseSuggestionViewHolder(view) {

        override fun bind(item: SuggestionModel, itemClickListener: SuggestionsAdapter.ItemClickListener?) {
            item as CommandSuggestionViewModel
            with(itemView) {
                val nameTextView = itemView.findViewById<TextView>(R.id.text_command_name)
                val descriptionTextView = itemView.findViewById<TextView>(R.id.text_command_description)
                nameTextView.text = "/${item.text}"
                val res = context.resources
                val id = res.getIdentifier(item.description, "string", context.packageName)
                val description = if (id > 0) res.getString(id) else ""
                descriptionTextView.text = description.toLowerCase()
                setOnClickListener {
                    itemClickListener?.onClick(item)
                }
            }
        }
    }
}