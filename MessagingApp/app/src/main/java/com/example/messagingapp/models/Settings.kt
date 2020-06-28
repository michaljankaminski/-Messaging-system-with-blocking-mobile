package com.example.messagingapp.models

class Settings {
    companion object{
        var userId: Int? = null

        fun setSettings(id: Int){
            userId = id
        }
    }
}