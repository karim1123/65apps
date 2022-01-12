package com.karimgabbasov.a65apps.interactors.birthday

import java.text.SimpleDateFormat
import java.util.*

fun String?.countMills(
    currentDate: Calendar
): Calendar {
    val calendar = GregorianCalendar.getInstance()
    calendar.time = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    if ((calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) && (calendar.get(Calendar.DAY_OF_MONTH) == 29)) {
        val year = calendar.get(Calendar.YEAR)
        if (year.isLeapYear()) {
            calendar.set(
                Calendar.YEAR, currentDate.get(Calendar.YEAR)
                        + (4 - currentDate.get(Calendar.YEAR) % 4)
            )
            calendar.set(Calendar.DAY_OF_MONTH, 29)
            if (!calendar.get(Calendar.YEAR).isLeapYear()) {
                calendar.add(Calendar.YEAR, 4)
            }
            return calendar
        }
    } else {
        calendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR))
        if (calendar.timeInMillis < currentDate.timeInMillis) {
            calendar.add(Calendar.YEAR, 1)
        }
    }
    return calendar

}

fun Int.isLeapYear(): Boolean {
    var leap = false
    if (this % 4 == 0) {
        if (this % 100 == 0) {
            leap = this % 400 == 0
        } else
            leap = true
    } else
        leap = false
    return leap
}
