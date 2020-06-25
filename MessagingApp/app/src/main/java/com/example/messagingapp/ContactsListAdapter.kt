package com.example.messagingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.holders.CommonMessageHolder
import com.example.messagingapp.holders.ReceivedMessageHolder
import com.example.messagingapp.holders.SentMessageHolder
import com.example.messagingapp.holders.ThreadHolder
import com.example.messagingapp.models.Message

class ContactsListAdapter(context: Context, contactList: MutableList<Thread>) : RecyclerView.Adapter<ThreadHolder>() {
    private var mContext: Context? = context
    private var mContactList: MutableList<Thread>? = contactList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message_received, parent, false)
        return ThreadHolder(view)
    }

    override fun getItemCount(): Int {
        return mContactList!!.count()
    }

    override fun onBindViewHolder(holder: ThreadHolder, position: Int) {
        val thread = mContactList!![position]
        holder.bind(thread)
    }
}