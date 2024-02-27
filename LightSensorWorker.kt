package com.example.mobicomp2024

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.*

class LightSensorWorker(context: Context, private val sensorManager: SensorManager) : SensorEventListener {
    private var job: Job? = null
    private var previousIsLight: Boolean? = null
    val class_context: Context = context

    fun start() {
        job = CoroutineScope(Dispatchers.Default).launch {
            val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this@LightSensorWorker, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop() {
        job?.cancel()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val lumens = event?.values?.get(0) ?: 0f
        val isLight = lumens > 60f

        if (previousIsLight == null || previousIsLight != isLight) {
            previousIsLight = isLight
            if (isLight) {
                println("It is light outside")
                NotificationUtils.sendNotification(class_context, "It is light outside, lumens: $lumens", "Lighting conditions")
            } else {
                println("It is dark outside")
                NotificationUtils.sendNotification(class_context, "It is dark outside, lumens: $lumens", "Lighting conditions")
            }
        }

    }
}