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
import com.example.messagingapp.models.Thread

class ThreadsFragment : Fragment() {
    private lateinit var mThreadRecycler: RecyclerView
    private lateinit var mThreadAdapter: ThreadsListAdapter
    private val mThreadList: MutableList<Thread> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_threads, container, false)
        val th = Thread()
        th.userId = 1
        mThreadList.add(th)
        mThreadRecycler = view.findViewById(R.id.reyclerview_thread_list)
        initRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun initRecyclerView() {
        mThreadAdapter = ThreadsListAdapter(mThreadList)
        mThreadRecycler.adapter = mThreadAdapter
        mThreadRecycler.setLayoutManager(LinearLayoutManager(this.context))
    }
}