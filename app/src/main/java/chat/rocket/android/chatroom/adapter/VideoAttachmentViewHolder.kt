package com.goalify.chat.android.chatroom.adapter

import android.view.View
import com.goalify.chat.android.chatroom.viewmodel.VideoAttachmentViewModel
import com.goalify.chat.android.player.PlayerActivity
import com.goalify.chat.android.util.extensions.setVisible
import com.goalify.chat.android.widget.emoji.EmojiReactionListener
import kotlinx.android.synthetic.main.message_attachment.view.*

class VideoAttachmentViewHolder(itemView: View,
                                listener: ActionsListener,
                                reactionListener: EmojiReactionListener? = null)
    : BaseViewHolder<VideoAttachmentViewModel>(itemView, listener, reactionListener) {

    init {
        with(itemView) {
            image_attachment.setVisible(false)
            audio_video_attachment.setVisible(true)
            setupActionMenu(attachment_container)
            setupActionMenu(audio_video_attachment)
        }
    }

    override fun bindViews(data: VideoAttachmentViewModel) {
        with(itemView) {
            file_name.text = data.attachmentTitle
            audio_video_attachment.setOnClickListener { view ->
                data.attachmentUrl.let { url ->
                    PlayerActivity.play(view.context, url)
                }
            }
        }
    }
}