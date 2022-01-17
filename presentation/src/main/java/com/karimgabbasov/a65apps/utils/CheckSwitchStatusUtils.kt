package com.karimgabbasov.a65apps.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.SwitchCompat
import com.karimgabbasov.a65apps.receirvers.AlarmReceiver

fun SwitchCompat.checkNotificationSwitchStatusUtil(
    context: Context,
    contactId: String
) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val existingIntent =
        PendingIntent.getBroadcast(
            context,
            contactId.toInt(),
            intent,
            PendingIntent.FLAG_NO_CREATE
        )
    this.isChecked = existingIntent != null
}
