package com.example.messagingapp.db

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object Thread : Table<Nothing>("thread") {
    val id = int("id").primaryKey()
    val name = varchar("name")
    val location = varchar("location")
}