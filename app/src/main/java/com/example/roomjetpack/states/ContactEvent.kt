package com.example.roomjetpack.states

import com.example.roomjetpack.entities.Contacts

sealed interface ContactEvent{
    data class SetFirstName(val firstName:String):ContactEvent
    data class SetLastName(val lastName:String) :ContactEvent
    data class SetPhoneNumber(val phoneNumber:String):ContactEvent
    data class SetContactImage(val imageUri: String?): ContactEvent
    data class SortContacts(val sortType:SortType):ContactEvent
    data class DeleteContact(val contact: Contacts):ContactEvent
    data class UpdateContact(val contact: Contacts):ContactEvent

    object SaveContact : ContactEvent
    object ShowDialog : ContactEvent
    object HideDialog : ContactEvent


}
