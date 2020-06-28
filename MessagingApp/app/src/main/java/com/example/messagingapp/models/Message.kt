package com.example.messagingapp.models

import java.time.LocalDateTime

class Message {
    var message: String? = null
    var sender: User? = null
    var createdAt: LocalDateTime? = null
}

class MessageJson {
    var sender: Int? = null
    var receiver: Int? = null
    var content: String? = null
}