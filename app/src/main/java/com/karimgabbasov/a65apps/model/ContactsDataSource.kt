package com.karimgabbasov.a65apps.model

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.data.DetailedContactModel
import io.reactivex.rxjava3.core.Single

private const val SELECTION_NUMBER = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?"
private const val SELECTION_MAIL = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " =?"
private const val SELECTION_NOTE = ContactsContract.Data.RAW_CONTACT_ID + "=?" +
        " AND " + ContactsContract.Data.MIMETYPE + "='" +
        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE + "'"
private const val SELECTION_BIRTHDAY = ContactsContract.Data.CONTACT_ID + "=?" +
        " AND " + ContactsContract.Data.MIMETYPE + " = '" +
        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE +
        "' AND " + ContactsContract.CommonDataKinds.Event.TYPE +
        " = " + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
private const val SELECTION_CONTACTS = ContactsContract.Contacts._ID + " =?"

object ContactsDataSource {
    private fun getContactNumbers(context: Context, id: String): Array<String> {
        val numbers = arrayOf("-", "-")
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        context.contentResolver?.query(
            phoneUri,
            null,
            SELECTION_NUMBER,
            arrayOf(id),
            null
        ).use { numberCursor ->
            if (numberCursor != null) {
                if (numberCursor.moveToNext()) {
                    numbers[0] = numberCursor.getString(
                        numberCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }
                if (numberCursor.moveToNext()) {
                    numbers[1] = numberCursor.getString(
                        numberCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }
            }
        }
        return numbers
    }

    private fun getContactEmails(context: Context, id: String): Array<String> {
        val emails = arrayOf("-", "-")
        val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        context.contentResolver?.query(
            emailUri,
            null,
            SELECTION_MAIL,
            arrayOf(id),
            null
        ).use { emailCursor ->
            if (emailCursor != null) {
                if (emailCursor.moveToNext()) {
                    emails[0] = emailCursor.getString(
                        emailCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    )
                }
                if (emailCursor.moveToNext()) {
                    emails[0] = emailCursor.getString(
                        emailCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    )
                }
            }
        }
        return emails
    }

    private fun getContactNote(context: Context, id: String): String {
        var note = "-"
        context.contentResolver?.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Note.NOTE),
            SELECTION_NOTE,
            arrayOf(id),
            null
        ).use { noteCursor ->
            if (noteCursor != null) {
                if (noteCursor.moveToNext()) {
                    if (noteCursor.getType(0) == Cursor.FIELD_TYPE_STRING) {
                        note = noteCursor.getString(0)
                    }
                }
            }
        }
        return note
    }

    private fun getContactBirthday(context: Context, id: String): String {
        var birthday = "-"
        context.contentResolver?.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Event.START_DATE),
            SELECTION_BIRTHDAY,
            arrayOf(id),
            null
        ).use { birthdayCursor ->
            if (birthdayCursor != null) {
                if (birthdayCursor.moveToNext()) {
                    birthday = birthdayCursor.getString(0)
                }
            }
        }
        return birthday
    }

    private fun getContactListData(context: Context, query: String): List<ContactsModel> {
        val contactList = mutableListOf<ContactsModel>()
        val contactsUri = ContactsContract.Contacts.CONTENT_URI
        context.contentResolver?.query(contactsUri, null, null, null, null).use { contactsCursor ->
            if (contactsCursor != null) {
                while (contactsCursor.moveToNext()) {
                    val id = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
                    )
                    val image = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)
                    )
                    val name = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
                    )
                    val number = getContactNumbers(context, id)
                    if (name.uppercase().contains(query.uppercase())) {
                        contactList.add(
                            ContactsModel(
                                id = id,
                                name = name,
                                number = number[0],
                                image = image
                            )
                        )
                    }
                }
            }
        }
        return contactList
    }

    private fun getContactData(context: Context, id: String): List<DetailedContactModel> {
        val contact = mutableListOf<DetailedContactModel>()
        val contactsUri = ContactsContract.Contacts.CONTENT_URI
        context.contentResolver?.query(
            contactsUri,
            null,
            SELECTION_CONTACTS,
            arrayOf(id),
            null
        ).use { contactsCursor ->
            if (contactsCursor != null) {
                if (contactsCursor.moveToNext()) {
                    val image = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)
                    )
                    val name = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
                    )
                    val numbers: Array<String> = getContactNumbers(context, id)
                    val emails: Array<String> = getContactEmails(context, id)
                    val birthday = getContactBirthday(context, id)
                    val note = getContactNote(context, id)
                    contact.add(
                        DetailedContactModel(
                            id = id,
                            name = name,
                            number = numbers[0],
                            secondPhoneNumber = numbers[1],
                            mail = emails[0],
                            secondMail = emails[1],
                            birthday = birthday,
                            description = note,
                            image = image
                        )
                    )
                }
            }
        }
        return contact
    }

    fun getContacts(context: Context, query: String): Single<List<ContactsModel>> =
        Single.fromCallable {
            Thread.sleep(1000L)
            getContactListData(context, query) }

    fun getContact(context: Context, id: String): Single<List<DetailedContactModel>> =
        Single.fromCallable {
            Thread.sleep(1000L)
            getContactData(context, id) }
}