package com.example.messagingapp

import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.logging.ConsoleLogger
import me.liuwj.ktorm.logging.LogLevel

class DbConnection {
    companion object
    {
        fun getConnection() : Database?{
            try{
                return Database.connect(
                    url = "jdbc:postgresql://10.0.2.2:8082/broker",
                    driver = "org.postgresql.Driver",
                    user = "postgres",
                    password = "postgres",
                    logger = ConsoleLogger(threshold = LogLevel.INFO)
                )
            }
            catch (e: Exception)
            {
                return null
            }
        }
    }
}