package com.karimgabbasov.a65apps.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.karimgabbasov.a65apps.*
import com.karimgabbasov.a65apps.data.DetailedContactModel
import com.karimgabbasov.a65apps.databinding.FragmentContactDetailBinding
import java.lang.ref.WeakReference

class ContactDetailFragment : Fragment() {
    private var service: ContactService? = null
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private var contactName: String? = null
    private var contactId: Int? = null
    private var contactBirthday: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        service = (context as? ServiceOwner)?.getService()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        createChannel(
            getString(R.string.birthday_notification_channel_id),
            getString(R.string.birthday_notification_channel_name)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactId = arguments?.getInt(ARGUMENT_ID)
        service?.getDetailContact(WeakReference(this))
        switchChecked() //проверка состояния switch
        binding.switchNotification.setOnClickListener { //обработка нажатий switch
            if (binding.switchNotification.isChecked) {
                setupAlarm()
            } else {
                cancelAlarm()
            }
        }
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.contact_detail_fragment_title)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        service = null
        super.onDetach()
    }

    fun setData(data: List<DetailedContactModel>) {
        requireActivity().runOnUiThread {
            binding.apply {
                contactName.text = data.first().firstName
                phoneNumber.text = data.first().number
                secondPhoneNumber.text = data.first().secondPhoneNumber
                mail.text = data.first().mail
                secondMail.text = data.first().secondMail
                description.text = data.first().description
                birthday.text = data.first().birthday
                contactPhoto.setImageResource(data.first().imageResourceId)
            }
            contactName = data.first().firstName
            contactBirthday = data.first().birthday
        }
    }

    fun getContactData() {
        val serviceOwner = (context as? ServiceOwner)?.getService()
        serviceOwner?.getDetailContact(WeakReference(this))
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun setupAlarm() { //функция для вызова alarm manager
        val alrmMgr: AlarmManager? = requireContext().getSystemService(ALARM_SERVICE) as? AlarmManager
        val notificationBody = "Today $contactName birthday!"
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("name", notificationBody)
            putExtra("id", contactId)
            putExtra("date", contactBirthday)
        }
        val existingIntent =
            PendingIntent.getBroadcast(context, contactId!!, intent, PendingIntent.FLAG_NO_CREATE)
        if (existingIntent == null) {
            val alarmIntent = PendingIntent.getBroadcast(context,
                contactId!!,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            val timeBeforeBirthdayInMills: Long = contactBirthday.countMills()

            alrmMgr?.set(AlarmManager.RTC_WAKEUP,
                timeBeforeBirthdayInMills, alarmIntent)
        }
    }

    private fun cancelAlarm() { //функция для отмены alarm manager
        val alrmMgr: AlarmManager? = null
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, contactId!!, intent, 0)
        alrmMgr?.cancel(alarmIntent)
        alarmIntent.cancel()
    }

    private fun switchChecked() { //функция для проверки состояни switch
        val intent = Intent(context, AlarmReceiver::class.java)
        val existingIntent =
            PendingIntent.getBroadcast(context, contactId!!, intent, PendingIntent.FLAG_NO_CREATE)
        binding.switchNotification.isChecked = existingIntent != null
    }

    companion object {
        private const val ARGUMENT_ID = "id"
        fun getNewInstance(id: Int): ContactDetailFragment =
            ContactDetailFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_ID to id
                )
            }
    }
}

