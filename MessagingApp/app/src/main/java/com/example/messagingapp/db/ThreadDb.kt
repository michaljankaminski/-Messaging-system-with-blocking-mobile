package com.example.messagingapp.db

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.date
import me.liuwj.ktorm.schema.int

object ThreadDb : Table<Nothing>("thread") {
    val id = int("id").primaryKey()
    val id_sender = int("id_sender")
    val id_receiver = int("id_receiver")
    val created = date("created")
    val modified = date("modified")
}