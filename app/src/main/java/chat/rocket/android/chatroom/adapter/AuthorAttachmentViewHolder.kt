package com.goalify.chat.android.chatroom.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.goalify.chat.android.chatroom.viewmodel.AuthorAttachmentViewModel
import com.goalify.chat.android.util.extensions.content
import com.goalify.chat.android.widget.emoji.EmojiReactionListener
import chat.rocket.common.util.ifNull
import kotlinx.android.synthetic.main.item_author_attachment.view.*

class AuthorAttachmentViewHolder(itemView: View,
                                 listener: ActionsListener,
                                 reactionListener: EmojiReactionListener? = null)
    : BaseViewHolder<AuthorAttachmentViewModel>(itemView, listener, reactionListener) {

    init {
        with(itemView) {
            setupActionMenu(author_attachment_container)
            setupActionMenu(text_fields)
            setupActionMenu(text_author_name)
        }
    }


    override fun bindViews(data: AuthorAttachmentViewModel) {
        with(itemView) {
            data.icon?.let { icon ->
                author_icon.isVisible = true
                author_icon.setImageURI(icon)
            }.ifNull {
                author_icon.isGone = true
            }

            author_icon.setImageURI(data.icon)
            text_author_name.content = data.name

            data.fields?.let { fields ->
                text_fields.content = fields
                text_fields.isVisible = true
            }.ifNull {
                text_fields.isGone = true
            }

            text_author_name.setOnClickListener {
                it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.attachmentUrl)))
            }
        }
    }
}