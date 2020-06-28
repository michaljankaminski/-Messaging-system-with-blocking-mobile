package com.example.messagingapp.models

class Settings {
    companion object{
        var userId: Int? = null
        var threads: List<Thread>? = null

        fun setSettings(id: Int){
            userId = id
        }

        fun setThreadLists(users: List<Thread>){
            threads = users
        }
    }
}