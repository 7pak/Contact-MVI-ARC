package com.example.roomjetpack

import android.content.Context
import androidx.room.Room
import com.example.roomjetpack.database.ContactDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsModel {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ContactDataBase::class.java, "contact.dp")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun providesDao(db: ContactDataBase) = db.dao

}