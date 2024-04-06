package com.example.roomjetpack.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contacts(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val contactImage:String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

