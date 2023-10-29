package com.example.parkingapp.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.parkingapp.ParkingActivity
import com.example.parkingapp.R
import com.example.parkingapp.util.Constance.ACTION_SHOW_PARKING_FRAGMENT
import com.example.parkingapp.util.Constance.MILLIS_TO_SERVICE
import com.example.parkingapp.util.Constance.NOTIFICATION_CHANNEL_ID1
import com.example.parkingapp.util.Constance.NOTIFICATION_CHANNEL_ID2
import com.example.parkingapp.util.Constance.NOTIFICATION_ID1
import com.example.parkingapp.util.Constance.NOTIFICATION_ID2
import com.example.parkingapp.util.Constance.NUMBER_TO_SERVICE
import com.example.parkingapp.util.Constance.PENDING_INTENT_NUMBER
import com.example.parkingapp.util.SelectTime

class ParkingService: Service() {

    private var number = 0

    companion object{
         val timerLiveData1 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData2 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData3 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData4 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData5 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData6 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData7 = MutableLiveData<Pair<Long, Long>>()
         val timerLiveData8 = MutableLiveData<Pair<Long, Long>>()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTag21", "Service is started")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyTag21", "Intent action -${intent?.action}")
        when(intent?.action){
            Action.START.toString() -> {
                val time = intent.getLongExtra(MILLIS_TO_SERVICE, 0)
                number = intent.getIntExtra(NUMBER_TO_SERVICE, 0)
                startForegroundService(time)
            }
            Action.STOP.toString() ->  stopSelf()

        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService(time: Long){
         timerNumberOfPlace(time)

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID1).setAutoCancel(false)
                .setOngoing(true).setContentTitle("Parking App")
                .setSmallIcon(R.drawable.icon_car)
                .setContentText("Паркомісце №$number 00:00")
                .setContentIntent(getParkingActivityPendingIntent())
                .build()

            startForeground(NOTIFICATION_ID1, notificationBuilder)
    }

    private fun updateNotification(timeText: String){

            val updateNotificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID1)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentTitle("Parking App")
                .setSmallIcon(R.drawable.icon_car).
                setContentText("Паркомісце №$number $timeText")
                .setContentIntent(getParkingActivityPendingIntent())
                .build()

            startForeground(NOTIFICATION_ID1, updateNotificationBuilder)

    }

    private fun getParkingActivityPendingIntent(): PendingIntent{
        val intent = PendingIntent.getActivity(this, 0, Intent(this, ParkingActivity::class.java).also {
            it.action = ACTION_SHOW_PARKING_FRAGMENT
        }, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)
        return intent
    }

    private fun timer(time:Long, liveData: MutableLiveData<Pair<Long, Long>>){
        Log.d("MyTag20", "Start timer")
        object : CountDownTimer(time, 1000){
            override fun onTick(p0: Long) {
                Log.d("MyTag20", "Time in timer - $time")
                val (hours, minutes) = SelectTime.convertMillis(p0)
                liveData.postValue(Pair(hours,minutes))
                val timeText = String.format("%02d:%02d", hours, minutes)
                updateNotification("Час $timeText")
                Log.d("MyTag20", "Update notification time - $timeText")
            }

            override fun onFinish() {
                stopSelf()
            }
        }.start()
    }

    private fun timerNumberOfPlace(time: Long){
        Log.d("MyTag", "Number of parking place - $number")
        when(number){
            1 -> {
                timer(time, timerLiveData1)
            }
            2 -> {
                timer(time, timerLiveData2)
            }
            3 -> {
                timer(time, timerLiveData3)
            }
            4 -> {
                timer(time, timerLiveData4)
            }
            5 -> {
                timer(time, timerLiveData5)
            }
            6 -> {
                timer(time, timerLiveData6)
            }
            7 -> {
                timer(time, timerLiveData7)
            }
            8 -> {
                timer(time, timerLiveData8)
            }
        }

    }

    enum class Action {
        START, STOP
    }

}