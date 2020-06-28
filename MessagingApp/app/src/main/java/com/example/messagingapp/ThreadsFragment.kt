package com.example.messagingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.db.UserDb
import com.example.messagingapp.models.Settings
import com.example.messagingapp.models.Thread
import com.example.messagingapp.models.User
import me.liuwj.ktorm.dsl.*

class ThreadsFragment : Fragment() {
    private lateinit var mThreadRecycler: RecyclerView
    private lateinit var mThreadAdapter: ThreadsListAdapter
    private val mThreadList: MutableList<Thread> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_threads, container, false)
        mThreadList.clear()
        val th = Thread()
        getContacts()
        mThreadRecycler = view.findViewById(R.id.reyclerview_thread_list)
        initRecyclerView()

        return view
    }

    private fun initRecyclerView() {
        mThreadAdapter = ThreadsListAdapter(mThreadList)
        mThreadRecycler.adapter = mThreadAdapter
        mThreadRecycler.setLayoutManager(LinearLayoutManager(this.context))
    }

    private fun getContacts(){
        val database = DbConnection.getConnection()

        val query = database!!
            .from(UserDb)
            .select()
            .where { (UserDb.id notEq Settings.userId!!) }

        for (row in query){
            var id = row[UserDb.id]
            var login = row[UserDb.login]
            var th = Thread()
            th.userId = id
            th.login = login
            mThreadList.add(th)
        }
        Settings.setThreadLists(mThreadList)
    }
}