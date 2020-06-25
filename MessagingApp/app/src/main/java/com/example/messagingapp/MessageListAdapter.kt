package com.example.messagingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.holders.CommonMessageHolder
import com.example.messagingapp.holders.ReceivedMessageHolder
import com.example.messagingapp.holders.SentMessageHolder
import com.example.messagingapp.models.Message

class MessageListAdapter(context: Context, messageList: MutableList<Message>) : RecyclerView.Adapter<CommonMessageHolder>() {
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    private var mContext: Context? = context
    private var mMessageList: MutableList<Message>? = messageList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonMessageHolder {
        val view: View

        if (viewType === VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false)
            return SentMessageHolder(view)
        }

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message_received, parent, false)
        return ReceivedMessageHolder(view)
    }

    override fun getItemCount(): Int {
        return mMessageList!!.count()
    }

    override fun getItemViewType(position: Int): Int {
        var currentUser = 1
        val message: Message = mMessageList!![position]
        return if (message.sender!!.userId == currentUser) {
            // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        }
        else {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onBindViewHolder(holder: CommonMessageHolder, position: Int) {
        val message: Message = mMessageList!![position]

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> holder.bind(message)
        }
    }
}

