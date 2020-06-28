package com.example.messagingapp.holders

import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.messagingapp.models.Message
import com.example.messagingapp.models.Settings
import kotlinx.android.synthetic.main.item_message_received.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ReceivedMessageHolder(itemView: View) : CommonMessageHolder(itemView) {
    var messageText: TextView
    var timeText:TextView
    var nameText:TextView
    var profileImage: ImageView

    init {
        super.itemView
        messageText = itemView.text_message_body
        timeText = itemView.text_message_time
        nameText = itemView.text_message_name
        profileImage = itemView.image_message_profile
    }

    override fun bind(message: Message) {
        messageText.text = message.content

        // Format the stored timestamp into a readable String using method.
        timeText.text = message.createdAt!!.format(DateTimeFormatter.ofPattern("HH:mm"))
        for ( th in Settings.threads!!){
            if (th.userId == message.sender)
            {
                nameText.text = th.login
            }
        }
    }
}