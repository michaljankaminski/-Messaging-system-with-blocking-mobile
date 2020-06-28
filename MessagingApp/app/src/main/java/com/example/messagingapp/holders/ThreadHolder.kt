package com.example.messagingapp.holders

import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.R
import com.example.messagingapp.models.Thread
import androidx.navigation.fragment.findNavController

class ThreadHolder (view: View) : RecyclerView.ViewHolder(view) {
    var threadText: TextView

    init {
        super.itemView
        threadText = itemView.findViewById(R.id.text_thread_name)
    }

    fun bind(thread: Thread) {
        threadText.text = thread.login
        this.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_FirstFragment_to_SecondFragment,bundleOf("rec" to thread.userId))
        )
    }
}