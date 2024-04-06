package com.example.roomjetpack.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.roomjetpack.entities.Contacts
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: Contacts)

    @Delete
    suspend fun deleteContact(contact: Contacts)

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactsOrderedByFirstName():Flow<List<Contacts>>

    @Query("SELECT * FROM contact ORDER BY lastName ASC")
    fun getContactsOrderedByLastName():Flow<List<Contacts>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun getContactsOrderedByPhoneNumber():Flow<List<Contacts>>

}