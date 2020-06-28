package com.example.messagingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.holders.ThreadHolder
import com.example.messagingapp.models.Thread as Thread

class ThreadsListAdapter(contactList: MutableList<Thread>) : RecyclerView.Adapter<ThreadHolder>() {
    private var mContactList: MutableList<Thread>? = contactList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thread, parent, false)
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