package com.example.roomjetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomjetpack.database.ContactDao
import com.example.roomjetpack.entities.Contacts
import com.example.roomjetpack.states.ContactEvent
import com.example.roomjetpack.states.ContactState
import com.example.roomjetpack.states.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ContactViewModel @Inject constructor(private val dao: ContactDao) : ViewModel() {
    private var _sortType = MutableStateFlow(SortType.FIRST_NAME)


    private var _contacts = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.FIRST_NAME -> {
                dao.getContactsOrderedByFirstName()
            }
            SortType.LAST_NAME -> {
                dao.getContactsOrderedByLastName()
            }
            SortType.PHONE_NUMBER -> {
                dao.getContactsOrderedByPhoneNumber()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }
    private var _state = MutableStateFlow(ContactState())
    val state = combine(_state, _sortType, _contacts) { state, sortType,contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            is ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber
                val contactImage = state.value.contactImage

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                    return
                }
                val contacts = Contacts(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    contactImage = contactImage,
                )
                viewModelScope.launch {
                    dao.upsertContact(contacts)
                }
                _state.update {
                    it.copy(
                        firstName = "",
                        lastName = "",
                        phoneNumber = "",
                        contactImage = null,
                        isAddingContact = false,
                    )
                }
            }
            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }
            }
            is ContactEvent.SetLastName -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            is ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }
            is ContactEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }
            }
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }

            is ContactEvent.SetContactImage -> {
                _state.update {
                    it.copy(
                        contactImage = event.imageUri
                    )
                }
            }
            is ContactEvent.UpdateContact -> {
                viewModelScope.launch {
                    dao.upsertContact(event.contact)
                }
            }
        }
    }
}