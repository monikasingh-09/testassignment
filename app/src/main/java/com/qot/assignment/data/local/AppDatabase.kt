package com.qot.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qot.assignment.data.models.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}