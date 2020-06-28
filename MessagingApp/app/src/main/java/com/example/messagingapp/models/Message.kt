package com.example.messagingapp.models

import com.google.gson.JsonParser
import java.time.LocalDateTime


class Message {
    var sender: Int? = null
    var receiver: Int? = null
    var content: String? = null
    var createdAt: LocalDateTime? = null

    constructor(senderId: Int, receiverId: Int, contentMsg: String, created: LocalDateTime){
        sender = senderId
        receiver = receiverId
        content = contentMsg
        createdAt = created
    }

    constructor(json: String){
        val jsonObject = JsonParser().parse(json).asJsonObject
        sender = jsonObject["sender"].asInt
        receiver = jsonObject["receiver"].asInt
        content = jsonObject["content"].asString
        createdAt = LocalDateTime.parse(jsonObject["createdAt"].asString)
    }

    fun toJsonClass() : MessageJson{
        return MessageJson(sender!!,receiver!!,content!!,createdAt!!)
    }
}

class MessageJson(senderId: Int, receiverId: Int, contentMsg: String, created: LocalDateTime ){
    var sender: Int = senderId
    var receiver: Int = receiverId
    var content: String = contentMsg
    var created: String = created.toString()
}