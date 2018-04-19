package com.goalify.chat.android.chatroom.adapter

import android.text.method.LinkMovementMethod
import android.view.View
import com.goalify.chat.android.chatroom.viewmodel.MessageAttachmentViewModel
import com.goalify.chat.android.widget.emoji.EmojiReactionListener
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAttachmentViewHolder(
        itemView: View,
        listener: ActionsListener,
        reactionListener: EmojiReactionListener? = null
) : BaseViewHolder<MessageAttachmentViewModel>(itemView, listener, reactionListener) {

    init {
        with(itemView) {
            text_content.movementMethod = LinkMovementMethod()
            setupActionMenu(text_content)
        }
    }

    override fun bindViews(data: MessageAttachmentViewModel) {
        with(itemView) {
            text_message_time.text = data.time
            text_sender.text = data.senderName
            text_content.text = data.content
        }
    }
}