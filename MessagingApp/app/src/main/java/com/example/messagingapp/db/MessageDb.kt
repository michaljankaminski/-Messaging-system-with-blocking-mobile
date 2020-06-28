package com.example.messagingapp.db

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.date
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object MessageDb : Table<Nothing>("message") {
    val id = int("id").primaryKey()
    val id_thread = int("id_thread")
    val id_sender = int("id_sender")
    val content = varchar("content")
    val created = date("created")
    val modified = date("modified")
}