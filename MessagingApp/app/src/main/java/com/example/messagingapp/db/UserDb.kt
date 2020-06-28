package com.example.messagingapp.db

import com.example.messagingapp.db.Thread.primaryKey
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object User : Table<Nothing>("user") {
    val id = int("id").primaryKey()
    val login = varchar("login")
    val password = varchar("password")
}