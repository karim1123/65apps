package com.karimgabbasov.a65apps

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import com.karimgabbasov.a65apps.interactors.birthday.AlarmManagerInteractor
import com.karimgabbasov.a65apps.interactors.birthday.BirthdayNotificationInteractorImpl
import com.karimgabbasov.a65apps.interactors.birthday.countMills
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactDetailsInteractorImpl
import com.karimgabbasov.a65apps.viewmodel.ContactDetailsViewModel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar
import java.util.GregorianCalendar

class BirthdayIntegrationTests : DescribeSpec() {
    @MockK
    private lateinit var alarmManagerInteractor: AlarmManagerInteractor

    @MockK
    private lateinit var contactDetailsInteractorImpl: ContactDetailsInteractorImpl

    @MockK
    private lateinit var contact: DetailedContactModel

    private var birthdayNotificationInteractorImpl: BirthdayNotificationInteractorImpl
    private var viewmodel: ContactDetailsViewModel

    init {
        MockKAnnotations.init(this)
        val currentDate = GregorianCalendar.getInstance()
        val expectedDate = GregorianCalendar.getInstance()
        birthdayNotificationInteractorImpl =
            BirthdayNotificationInteractorImpl(alarmManagerInteractor)
        viewmodel = ContactDetailsViewModel(
            contactDetailsInteractorImpl,
            birthdayNotificationInteractorImpl,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        RxJavaPlugins.setErrorHandler(Consumer { })
        every { contact.id } returns "1"
        every { contact.name } returns "Иван Иванович"

        describe("добавление и удаление уведомления о дне рождения  для контакта ") {
            it("если день рождения был в текущем году и год не високосный, то уведомление должно сработать в следующем году") {
                every { contact.birthday } returns "1981-09-08"
                currentDate.set(1999, 8, 9)
                expectedDate.set(2000, 8, 8)
                every { contactDetailsInteractorImpl.getContactData("1") } returns listOf(contact)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                viewmodel.loadDetailContact("1")
                viewmodel.changeNotifyStatus(true, currentDate)
                birthdayNotificationInteractorImpl.setNotification(
                    contact,
                    currentDate
                ) shouldBe Unit
            }
            it("если дня рождения не было текущем году и год не високосный, то уведомление должно сработать в текущем году") {
                every { contact.birthday } returns "1981-09-08"
                currentDate.set(1999, 8, 7)
                expectedDate.set(1999, 8, 8)
                every { contactDetailsInteractorImpl.getContactData("1") } returns listOf(contact)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                viewmodel.loadDetailContact("1")
                viewmodel.changeNotifyStatus(true, currentDate)
                birthdayNotificationInteractorImpl.setNotification(
                    contact,
                    currentDate
                ) shouldBe Unit
            }
            it("удаление напоминания") {
                every { alarmManagerInteractor.cancelAlarmManager(contact) } returns Unit
                viewmodel.loadDetailContact("1")
                viewmodel.changeNotifyStatus(false, currentDate)
                birthdayNotificationInteractorImpl.cancelNotification(contact) shouldBe Unit
            }
            it(
                "если день рождения 29 февраля, текущий год не високосный, а следующий - високосный, то уведомление должно сработать в следующем году 29 февраля"
            ) {
                every { contact.birthday } returns "1980-02-29"
                currentDate.set(1999, 2, 2)
                expectedDate.set(2000, 1, 29)
                every { contactDetailsInteractorImpl.getContactData("1") } returns listOf(contact)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                viewmodel.loadDetailContact("1")
                viewmodel.changeNotifyStatus(true, currentDate)
                birthdayNotificationInteractorImpl.setNotification(
                    contact,
                    currentDate
                ) shouldBe Unit
            }
            it(
                "если день рождения 29 февраля, текущий год високосный, день рождения был в текущем году, то уведомление должно сработать в следующем високосном году 28 февраля"
            ) {
                every { contact.birthday } returns "1980-02-29"
                currentDate.set(2000, 2, 1)
                expectedDate.set(2004, 1, 29)
                every { contactDetailsInteractorImpl.getContactData("1") } returns listOf(contact)
                val notifyDate = contact.birthday.countMills(currentDate)
                compareDates(expectedDate, notifyDate)
                every { alarmManagerInteractor.setupAlarmManager(any(), notifyDate) } returns Unit
                viewmodel.loadDetailContact("1")
                viewmodel.changeNotifyStatus(true, currentDate)
                birthdayNotificationInteractorImpl.setNotification(
                    contact,
                    currentDate
                ) shouldBe Unit
            }
        }
    }

    private fun compareDates(expected: Calendar, result: Calendar) {
        result.get(Calendar.DAY_OF_MONTH) shouldBe expected.get(Calendar.DAY_OF_MONTH)
        result.get(Calendar.MONTH) shouldBe expected.get(Calendar.MONTH)
        result.get(Calendar.YEAR) shouldBe expected.get(Calendar.YEAR)
    }
}
