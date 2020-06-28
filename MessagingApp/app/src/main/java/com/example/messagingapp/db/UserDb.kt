package com.example.messagingapp.db

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object UserDb : Table<Nothing>("user") {
    val id = int("id").primaryKey()
    val login = varchar("login")
    val password = varchar("password")
}