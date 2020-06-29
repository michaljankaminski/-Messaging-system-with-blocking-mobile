package com.example.messagingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.db.BanDb
import com.example.messagingapp.db.MessageDb
import com.example.messagingapp.db.ThreadDb
import com.example.messagingapp.db.UserDb
import com.example.messagingapp.models.Message
import com.example.messagingapp.models.Settings
import com.example.messagingapp.models.Thread
import com.example.messagingapp.rabbit.RabbitOperations
import com.example.messagingapp.rabbit.RabbitService
import com.google.android.material.snackbar.Snackbar
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import me.liuwj.ktorm.dsl.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ChatFragment : Fragment() {
    private lateinit var mEditText: EditText
    private lateinit var mButtonBlock: Button
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter: MessageListAdapter
    private var rabbitOperations: RabbitOperations = RabbitOperations()
    private var recieverId: Int? = 0
    private var isBlocked: Boolean = false
    private val mMessageList: MutableList<Message> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_chat, container, false)
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list)
        mEditText = view.findViewById(R.id.edittext_chatbox)
        recieverId = arguments?.getInt("rec")
        isBlocked = isBlocked()
        getArchieved()
        initRecyclerView()
        val deliverCallback =
            DeliverCallback { consumerTag: String?, delivery: Delivery ->
                val message = String(delivery.body)
                println(" [x] Received '$message'")
                var msg = Message(message)
                if (msg.sender == recieverId){
                    receive(msg)
                }

            }
        if (!isBlocked){
            rabbitOperations.listen(RabbitService.getService(),deliverCallback)
        }

        view.findViewById<Button>(R.id.button_chatbox_send).setOnClickListener { view ->
            if (isBlocked) {
                Snackbar.make(view, "The user is blocked", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            else{
                send(view)
            }
        }
        mButtonBlock = view.findViewById<Button>(R.id.button_block)

        if (!isBlocked){
            mButtonBlock.text = "Block"
        }
        else{
            mButtonBlock.text = "Unblock"
        }

        view.findViewById<Button>(R.id.button_block).setOnClickListener { view ->
            if (!isBlocked){
                block()
                mButtonBlock.text = "Unblock"
            }
            else{
                unblock()
                mButtonBlock.text = "Block"
            }
    }

        return view
    }

    private fun send(view: View){
        var msg = Message(
            Settings.userId!!,
            recieverId!!,
            mEditText.text.toString(),
            LocalDateTime.now())

        if (!isBeingBlocked()){
            rabbitOperations.sendMessage(RabbitService.getService(), msg)

        }

        mMessageList.add(msg)
        mMessageAdapter.notifyDataSetChanged()
        mEditText.text.clear()
    }

    private fun receive(message: Message){

        this.activity!!.runOnUiThread(Runnable {
            mMessageList.add(message)
            mMessageAdapter.notifyDataSetChanged()
            initRecyclerView()
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

    private fun block() {
        var database = DbConnection.getConnection()
        database!!.insert(BanDb) {
            it.id to Settings.userId
            it.ban_user_id to recieverId
        }

        isBlocked = true
    }

    private fun unblock() {
        var database = DbConnection.getConnection()
        database!!.delete(BanDb) {
            (it.id eq Settings.userId!!) and
                    (it.ban_user_id eq recieverId!!)
        }

        isBlocked = false
    }

    private fun isBlocked(): Boolean{
        var database = DbConnection.getConnection()
        val query = database!!
            .from(BanDb)
            .select()
            .where { (BanDb.id eq Settings.userId!!) and ( BanDb.ban_user_id eq recieverId!!)}

        if (query.totalRecords > 0)
        {
            return true
        }

        return false
    }

    private fun isBeingBlocked(): Boolean{
        var database = DbConnection.getConnection()
        val query = database!!
            .from(BanDb)
            .select()
            .where { (BanDb.id eq recieverId!!) and ( BanDb.ban_user_id eq Settings.userId!!)}

        if (query.totalRecords > 0)
        {
            return true
        }

        return false
    }

    private fun initRecyclerView() {
        mMessageAdapter = MessageListAdapter(this.context!!, mMessageList)
        mMessageRecycler.adapter = mMessageAdapter
        mMessageRecycler.setLayoutManager(LinearLayoutManager(this.context))
    }
}