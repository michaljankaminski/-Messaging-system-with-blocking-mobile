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
        sender = jsonObject["Sender"].asInt
        receiver = jsonObject["Receiver"].asInt
        content = jsonObject["Content"].asString
        createdAt = LocalDateTime.now()//.parse(jsonObject["Created"].asString)
    }

    fun toJsonClass() : MessageJson{
        return MessageJson(sender!!,receiver!!,content!!,createdAt!!)
    }
}

class MessageJson(senderId: Int, receiverId: Int, contentMsg: String, created: LocalDateTime ){
    var Sender: Int = senderId
    var Receiver: Int = receiverId
    var Content: String = contentMsg
    var Created: String = created.toString()
}