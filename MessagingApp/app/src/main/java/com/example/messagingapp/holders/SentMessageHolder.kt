package com.example.messagingapp.holders

import android.text.format.DateUtils
import android.view.View

import android.widget.TextView
import com.example.messagingapp.models.Message
import kotlinx.android.synthetic.main.item_message_sent.view.*


class SentMessageHolder(itemView: View) : CommonMessageHolder(itemView) {
    var messageText: TextView
    var timeText: TextView

    init {
        messageText = itemView.text_message_body
        timeText = itemView.text_message_time
    }

    override fun bind(message: Message) {
        messageText.text = message.message
        timeText.text = DateUtils.formatDateTime(itemView.context,message.createdAt,DateUtils.FORMAT_SHOW_TIME)
    }
}