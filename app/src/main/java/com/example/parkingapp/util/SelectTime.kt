package com.example.parkingapp.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log

class SelectTime {

    var bookingDate = ""
    var timeInMillis = 0L
    var hours = 0
    var totalCost = 0

    fun getTotalHoursBookingParkingPlace1(context: Context, onDateTimeSelected: (date: String, timeInMillis: Long, hours: Int, totalCost: Int) -> Unit) {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        DatePickerDialog(context, DatePickerDialog.OnDateSetListener{ view, myear, mmonth, mday ->

            val calendarTime = Calendar.getInstance()
            val hour = calendarTime.get(Calendar.HOUR_OF_DAY)
            val minute = calendarTime.get(Calendar.MINUTE)

            TimePickerDialog(context, TimePickerDialog.OnTimeSetListener{ view, houreOfDay, mminuteOfHoure ->

                bookingDate =  "$mday/${mmonth + 1}/$myear  $houreOfDay:$mminuteOfHoure"


                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(myear, mmonth, mday, houreOfDay, mminuteOfHoure)

                val currentCalendar = Calendar.getInstance()

                timeInMillis = (selectedDateCalendar.timeInMillis - currentCalendar.timeInMillis)
                Log.d("MyTag11", "TimeInMillis - $timeInMillis")

                val diffMillis = (selectedDateCalendar.timeInMillis - currentCalendar.timeInMillis)/(60*60*1000)

                hours = diffMillis.toInt()

                totalCost = (diffMillis * 20).toInt()

                onDateTimeSelected(bookingDate, timeInMillis, hours, totalCost)

            }, hour, minute, true).show()

        }, year, month, day).show()


    }

    companion object{

        fun convertMillis(millis: Long): Pair<Long, Long>{
            val seconds = millis / 1000
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60

            return Pair(hours, minutes)
        }

    }

}