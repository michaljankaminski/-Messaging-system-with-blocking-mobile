package com.example.messagingapp.holders

import android.text.format.DateUtils
import android.view.View

import android.widget.TextView
import com.example.messagingapp.models.Message
import kotlinx.android.synthetic.main.item_message_sent.view.*
import java.time.format.DateTimeFormatter


class SentMessageHolder(itemView: View) : CommonMessageHolder(itemView) {
    var messageText: TextView
    var timeText: TextView

    init {
        messageText = itemView.text_message_body
        timeText = itemView.text_message_time
    }

    override fun bind(message: Message) {
        messageText.text = message.content
        timeText.text = message.createdAt!!.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }
}