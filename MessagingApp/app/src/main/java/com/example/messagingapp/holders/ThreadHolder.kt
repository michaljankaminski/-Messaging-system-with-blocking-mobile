package com.example.messagingapp.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_thread.view.*

class ThreadHolder (view: View) : RecyclerView.ViewHolder(view) {
    var threadText: TextView

    init {
        super.itemView
        threadText = itemView.text_thread_name
    }

    fun bind(thread: Thread) {
        threadText.text = thread.id.toString()
    }
}