package com.goalify.chat.android.widget.autocompletion.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import com.goalify.chat.android.widget.autocompletion.model.SuggestionModel

abstract class BaseSuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: SuggestionModel, itemClickListener: SuggestionsAdapter.ItemClickListener?)
}