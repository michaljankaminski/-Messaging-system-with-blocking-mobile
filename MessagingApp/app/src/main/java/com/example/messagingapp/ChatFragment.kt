package com.example.messagingapp

import android.R.attr.password
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.models.Message
import com.example.messagingapp.models.User
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.time.LocalDateTime


class ChatFragment : Fragment() {
    private lateinit var mEditText: EditText
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter: MessageListAdapter
    private var id: Int? = 1
    private val mMessageList: MutableList<Message> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_chat, container, false)
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list)
        mEditText = view.findViewById(R.id.edittext_chatbox)
        initRecyclerView()

        mMessageRecycler.setOnClickListener { view ->
            receive(view)
        }

        view.findViewById<Button>(R.id.button_chatbox_send).setOnClickListener { view ->
            send(view)
        }
        id = 0
        return view
    }

    private fun send(view: View){
        var msg = Message()
        var text = mEditText.text.toString()
        msg.message = text
        msg.sender = User()
        msg.sender!!.userId = id
        msg.createdAt = LocalDateTime.now()

        mMessageList.add(msg)
        RabbitService()
        mMessageAdapter.notifyDataSetChanged()
        mEditText.text.clear()
    }

    private fun receive(view: View){
        var msg = Message()

        var text = mEditText.text.toString()

        msg.message = text
        msg.sender = User()
        msg.createdAt = LocalDateTime.now()

        mMessageList.add(msg)

        mMessageAdapter.notifyDataSetChanged()
        mEditText.text.clear()

    }

    private fun initRecyclerView() {
        mMessageAdapter = MessageListAdapter(this.context!!, mMessageList)
        mMessageRecycler.adapter = mMessageAdapter
        mMessageRecycler.setLayoutManager(LinearLayoutManager(this.context))
    }
}