package com.example.messagingapp

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
import com.example.messagingapp.models.Settings
import com.example.messagingapp.rabbit.RabbitOperations
import com.example.messagingapp.rabbit.RabbitService
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import java.time.LocalDateTime


class ChatFragment : Fragment() {
    private lateinit var mEditText: EditText
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter: MessageListAdapter
    private var rabbitOperations: RabbitOperations = RabbitOperations()
    private var recieverId: Int? = 2
    private val mMessageList: MutableList<Message> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_chat, container, false)
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list)
        mEditText = view.findViewById(R.id.edittext_chatbox)
        initRecyclerView()

        val deliverCallback =
            DeliverCallback { consumerTag: String?, delivery: Delivery ->
                val message = String(delivery.body)
                println(" [x] Received '$message'")
                var msg = Message(message)
                receive(msg)
            }
        rabbitOperations.listen(RabbitService.getService(),deliverCallback)

        view.findViewById<Button>(R.id.button_chatbox_send).setOnClickListener { view ->
            send(view)
        }

        return view
    }

    private fun send(view: View){
        var msg = Message(
            Settings.userId!!,
            recieverId!!,
            mEditText.text.toString(),
            LocalDateTime.now())

        rabbitOperations.sendMessage(RabbitService.getService(), msg)
        mMessageList.add(msg)

        mMessageAdapter.notifyDataSetChanged()

        mEditText.text.clear()
    }

    private fun receive(message: Message){

        var text = mEditText.text.toString()
        mMessageList.add(message)

        this.activity!!.runOnUiThread(Runnable {
            mMessageAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        mMessageAdapter = MessageListAdapter(this.context!!, mMessageList)
        mMessageRecycler.adapter = mMessageAdapter
        mMessageRecycler.setLayoutManager(LinearLayoutManager(this.context))
    }
}