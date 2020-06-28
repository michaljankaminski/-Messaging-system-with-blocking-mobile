package com.example.messagingapp

import com.example.messagingapp.models.MessageJson
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer


class RabbitService {
    init {
        val factory = ConnectionFactory()

        factory.username = "guest"
        factory.password = "guest"
        factory.host = "10.0.2.2"
        factory.port = 8081

        val conn = factory.newConnection()
        val channel = conn.createChannel()

        val queue = channel.queueDeclare().queue

        val consumer = DefaultConsumer(channel)
        val userConsumer = DefaultConsumer(channel)

        val props = AMQP.BasicProperties.Builder()
        props.correlationId("temp")
        props.replyTo(queue)

        var msg = MessageJson()
        msg.content = "Start"
        msg.sender = 1
        msg.receiver = 2

        var messageByte = Gson().toJson(msg).toByteArray()

        /*channel.basicPublish(
            "users",
            "user." + msg.receiver.toString(),
            props.build(),
            messageByte
        )*/
    }
}