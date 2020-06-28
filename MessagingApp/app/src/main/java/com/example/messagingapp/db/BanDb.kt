package com.example.messagingapp.db

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object BanDb : Table<Nothing>("ban") {
    val id = int("user_id")
    val ban_user_id = int("ban_user_id")
}