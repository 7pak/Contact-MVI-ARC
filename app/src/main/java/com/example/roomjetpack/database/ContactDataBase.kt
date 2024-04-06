package com.example.roomjetpack.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomjetpack.entities.Contacts

@Database(entities = [Contacts::class], version = 2,exportSchema = false)

abstract class ContactDataBase:RoomDatabase() {

    abstract val dao : ContactDao


}