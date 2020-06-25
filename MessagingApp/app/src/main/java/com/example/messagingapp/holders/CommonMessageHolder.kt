package com.example.messagingapp.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.models.Message

open class CommonMessageHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}