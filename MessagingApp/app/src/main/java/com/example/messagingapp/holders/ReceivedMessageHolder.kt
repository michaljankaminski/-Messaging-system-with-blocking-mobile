package com.example.messagingapp.holders

import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.messagingapp.models.Message
import kotlinx.android.synthetic.main.item_message_received.view.*


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
        messageText.setText(message.message)

        // Format the stored timestamp into a readable String using method.
        timeText.setText(DateUtils.formatDateTime(itemView.context,message.createdAt, DateUtils.FORMAT_SHOW_TIME))
        nameText.setText(message.sender?.nickname)

        // Insert the profile image from the URL into the ImageView.
        //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage)
    }
}