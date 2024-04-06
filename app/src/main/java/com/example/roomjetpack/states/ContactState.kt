package com.example.roomjetpack.states

import com.example.roomjetpack.entities.Contacts

data class ContactState(
    val contacts:List<Contacts> = emptyList(),
    val firstName:String="",
    val lastName:String="",
    val phoneNumber:String="",
    val contactImage: String? = null,
    val isAddingContact:Boolean= false,
    val sortType:SortType = SortType.FIRST_NAME
)
