package com.example.messagingapp.rabbit

import com.example.messagingapp.models.Message
import com.example.messagingapp.models.Settings
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import java.util.*


class RabbitOperations {
    fun sendMessage(service: RabbitService, message: Message){
        var messageByte = Gson().toJson( message.toJsonClass()).toByteArray()

        service.channel.basicPublish(
            "users",
            "user." + message.receiver.toString(),
            null,
            messageByte
        )

        service.channel.basicPublish(
            "logs",
            "MsgLogs",
            service.props,
            messageByte
        )

        service.channel.basicConsume(service.responseQueue, true, DeliverCallback
        {
                consumerTag: String?, delivery: Delivery ->
                val message = String(delivery.body)
                println(" [x] Response '$message'")
        }, CancelCallback {  })
    }

    fun listen(service: RabbitService, deliverCallback: DeliverCallback) {
        val queueName = "user-" + Settings.userId.toString()
        service.channel.basicConsume(queueName, true, deliverCallback, CancelCallback {  })
    }
}