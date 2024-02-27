package com.example.mobicomp2024

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mobicomp2024.ui.theme.Mobicomp2024Theme
import androidx.core.app.NotificationCompat

class MainActivity : ComponentActivity() {


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "users.db"
            ).allowMainThreadQueries().build()
    }


    private val viewModel by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(db.dao) as T
                }
            }
        }
    )

    val CHANNEL_ID = "mobicomp2024_notifications"

    // Create notif channel here and call the function in onCreate
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifications"
            val descriptionText = "channel for Mobicomp2024 app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            println("*** CHANNEL CREATED ***")
        }else{
            println("*** CHANNEL NOT CREATED ***")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val lightSensorWorker = LightSensorWorker(context = this, sensorManager)
        lightSensorWorker.start()

        setContent {
            Mobicomp2024Theme {
                val state by viewModel.state.collectAsState()
                Navigation(state = state, onEvent = viewModel::onEvent, context = this)

            }
        }

    }
}

