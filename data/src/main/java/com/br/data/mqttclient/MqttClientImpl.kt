package com.br.data.mqttclient

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

private const val QUALITY_SERVICE = 1

@ExperimentalCoroutinesApi
class MqttClientImpl(
    private val mqtt: MqttAsyncClient
) : MqttClient {

    override fun connect(): Flow<Unit> = callbackFlow {
        mqtt.connect(buildMqttOptions(), null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                trySend(Unit)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                cancel(cause = exception as CancellationException?)
            }
        })
        awaitClose()
    }

    override fun publish(
        topic: String,
        msg: String,
    ): Flow<Unit> = callbackFlow {
        val message = MqttMessage().apply {
            payload = msg.toByteArray()
            qos = QUALITY_SERVICE
            isRetained = false
        }
        mqtt.publish(topic, message, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                trySend(Unit)
            }

            override fun onFailure(
                asyncActionToken: IMqttToken?,
                exception: Throwable?
            ) {
                cancel(cause = exception as CancellationException?)
            }
        })
        awaitClose()
    }

    override fun onMessageArrived(): Flow<String> {
        return callbackFlow {
            mqtt.setCallback(
                object : MqttCallback {
                    override fun connectionLost(cause: Throwable?) {
                        cancel(cause = cause as CancellationException?)
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) = Unit

                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        trySend(message.toString())
                    }
                }
            )
            awaitClose()
        }
    }

    override fun subscribe(
        topic: String,
    ): Flow<Unit> {
        return callbackFlow {
            mqtt.subscribe(topic, QUALITY_SERVICE,
                null,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        trySend(Unit)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        cancel(cause = exception as CancellationException?)
                    }
                }
            )
            awaitClose()
        }
    }

    override fun unsubscribe(
        topic: String,
    ): Flow<Unit> {
        return callbackFlow {
            mqtt.unsubscribe(topic,
                null,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        trySend(Unit)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        cancel(cause = exception as CancellationException?)
                    }
                }
            )
            awaitClose()
        }
    }

    private fun buildMqttOptions() = MqttConnectOptions().apply {
        userName = "gabrixx"
        password = "PUT_KEY_HERE".toCharArray()
    }
}