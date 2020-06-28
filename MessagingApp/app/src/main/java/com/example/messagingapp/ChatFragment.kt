package com.example.messagingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.db.MessageDb
import com.example.messagingapp.db.ThreadDb
import com.example.messagingapp.db.UserDb
import com.example.messagingapp.models.Message
import com.example.messagingapp.models.Settings
import com.example.messagingapp.models.Thread
import com.example.messagingapp.rabbit.RabbitOperations
import com.example.messagingapp.rabbit.RabbitService
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import me.liuwj.ktorm.dsl.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ChatFragment : Fragment() {
    private lateinit var mEditText: EditText
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter: MessageListAdapter
    private var rabbitOperations: RabbitOperations = RabbitOperations()
    private var recieverId: Int? = 0
    private val mMessageList: MutableList<Message> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_chat, container, false)
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list)
        mEditText = view.findViewById(R.id.edittext_chatbox)
        recieverId = arguments?.getInt("rec")
        getArchieved()
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

    private fun getArchieved() {
        var database = DbConnection.getConnection()
        val query = database!!
            .from(ThreadDb)
            .select()
            .where { ((ThreadDb.id_receiver eq recieverId!!) and (ThreadDb.id_sender eq Settings.userId!!)) or ((ThreadDb.id_sender eq recieverId!!) and (ThreadDb.id_receiver eq Settings.userId!!))}

        var thread_id: Int = -1
        if (query.totalRecords > 0){
            for (row in query){
                thread_id = row[ThreadDb.id]!!
                break;
            }

            if (thread_id != -1){
                val msgQuery = database!!
                    .from(MessageDb)
                    .select()
                    .where { MessageDb.id_thread eq thread_id}

                for (row in msgQuery){
                    var created = row[MessageDb.created]

                    var content = row[MessageDb.content]!!
                    var id_sender = row[MessageDb.id_sender]!!
                    var senderId: Int = -1
                    var receiverId: Int = -1
                    if (id_sender == Settings.userId){
                        senderId = Settings.userId!!
                        receiverId = this.recieverId!!
                    }
                    else{
                        senderId = this.recieverId!!
                        receiverId = Settings.userId!!
                    }
                    mMessageList.add(Message(
                        senderId,
                        receiverId,
                        content,
                        LocalDateTime.parse(created!!.subSequence(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    ))
                }
            }
        }
    }

    private fun initRecyclerView() {
        mMessageAdapter = MessageListAdapter(this.context!!, mMessageList)
        mMessageRecycler.adapter = mMessageAdapter
        mMessageRecycler.setLayoutManager(LinearLayoutManager(this.context))
    }
}