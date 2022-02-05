package com.karimgabbasov.a65apps

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import com.karimgabbasov.a65apps.interactors.birthday.AlarmManagerInteractor
import com.karimgabbasov.a65apps.interactors.birthday.BirthdayNotificationInteractorImpl
import com.karimgabbasov.a65apps.interactors.birthday.countMills
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import java.util.Calendar
import java.util.GregorianCalendar

class BirthdayModuleTests : DescribeSpec() {
    @MockK
    private lateinit var contact: DetailedContactModel

    @MockK
    private lateinit var alarmManagerInteractor: AlarmManagerInteractor

    private var birthdayNotificationInteractorImpl: BirthdayNotificationInteractorImpl

    init {
        MockKAnnotations.init(this)
        val currentDate = GregorianCalendar.getInstance()
        val expectedDate = GregorianCalendar.getInstance()
        birthdayNotificationInteractorImpl =
            BirthdayNotificationInteractorImpl(alarmManagerInteractor)

        describe("добавление и удаление уведомления о дне рождения  для контакта ") {
            it("если день рождения был в текущем году и год не високосный, то уведомление должно сработать в следующем году") {
                every { contact.birthday } returns "1981-09-08"
                currentDate.set(1999, 8, 9)
                expectedDate.set(2000, 8, 8)
                // вычитываем дату для уведомления
                val notifyDate =
                    contact.birthday.countMills(currentDate)
                // проверяем правильно ли рассчитана дата для уведомления
                compareDates(
                    expectedDate,
                    notifyDate
                )
                // определяем поведение alarmManagerInteractor при передачи правильной даты
                every {
                    alarmManagerInteractor.setupAlarmManager(
                        any(),
                        notifyDate
                    )
                } returns Unit
                birthdayNotificationInteractorImpl.setNotification(contact, currentDate)
                // проверяем вызывался ли alarmManagerInteractor с правильной датой
                verify {
                    alarmManagerInteractor.setupAlarmManager(
                        any(),
                        notifyDate
                    )
                }
            }
            it("если дня рождения не было текущем году и год не високосный, то уведомление должно сработать в текущем году") {
                every { contact.birthday } returns "1981-09-08"
                currentDate.set(1999, 8, 7)
                expectedDate.set(1999, 8, 8)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                birthdayNotificationInteractorImpl.setNotification(contact, currentDate)
                verify { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) }
            }
            it("удаление напоминания") {
                every { contact.birthday } returns "1981-09-08"
                every { alarmManagerInteractor.cancelAlarmManager(any()) } returns Unit
                birthdayNotificationInteractorImpl.cancelNotification(contact)
                verify { alarmManagerInteractor.cancelAlarmManager(any()) }
            }
            it(
                "если день рождения 29 февраля, текущий год не високосный, а следующий - високосный, то уведомление должно сработать в следующем году 29 февраля"
            ) {
                every { contact.birthday } returns "1980-02-29"
                currentDate.set(1999, 2, 2)
                expectedDate.set(2000, 1, 29)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                birthdayNotificationInteractorImpl.setNotification(contact, currentDate)
                verify { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) }
            }
            it(
                "если день рождения 29 февраля, текущий год високосный, день рождения был в текущем году, то уведомление должно сработать в следующем високосном году 28 февраля"
            ) {
                every { contact.birthday } returns "1980-02-29"
                currentDate.set(2000, 2, 1)
                expectedDate.set(2004, 1, 29)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                birthdayNotificationInteractorImpl.setNotification(contact, currentDate)
                verify { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) }
            }
        }
    }

    private fun compareDates(expected: Calendar, result: Calendar) {
        result.get(Calendar.DAY_OF_MONTH) shouldBe expected.get(Calendar.DAY_OF_MONTH)
        result.get(Calendar.MONTH) shouldBe expected.get(Calendar.MONTH)
        result.get(Calendar.YEAR) shouldBe expected.get(Calendar.YEAR)
    }
}
