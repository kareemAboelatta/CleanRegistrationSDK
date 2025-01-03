package com.kareem.registrationsdk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kareem.registrationsdk.data.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
