package com.example.messagingapp.rabbit

import com.rabbitmq.client.*
import java.util.*


class RabbitService {
    var connection: Connection
    var channel: Channel
    var responseQueue: String
    var props: AMQP.BasicProperties
    init {
        val factory = ConnectionFactory()

        factory.username = "guest"
        factory.password = "guest"
        factory.host = "10.0.2.2"
        factory.port = 8081

        connection = factory.newConnection()
        channel = connection.createChannel()

        responseQueue = channel.queueDeclare().queue

        val propsTemp = AMQP.BasicProperties.Builder()
        propsTemp.correlationId(UUID.randomUUID().toString())
        propsTemp.replyTo(responseQueue)

        props = propsTemp.build()
       // val queue = channel.queueBind(queueName, "users", "");

        //val consumer = DefaultConsumer(channel)
        //val userConsumer = DefaultConsumer(channel)

        /*val props = AMQP.BasicProperties.Builder()
        props.correlationId("temp")
        props.replyTo(queue)*/

    }
    companion object{
        var rabbitService: RabbitService? = null
        fun getService() : RabbitService {
            if (rabbitService == null)
                rabbitService = RabbitService()

            return rabbitService!!
        }
    }
}