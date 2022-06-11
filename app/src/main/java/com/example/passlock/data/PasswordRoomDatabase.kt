package com.example.passlock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passlock.model.Password


@Database(entities = [Password::class], version = 1, exportSchema = false)
abstract class PasswordRoomDatabase : RoomDatabase() {
    abstract fun PasswordDao() : PasswordDao
    companion object {
        @Volatile
        private var INSTANCE: PasswordRoomDatabase? = null
        fun getDatabase(context: Context): PasswordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PasswordRoomDatabase::class.java,
                    "password_room_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}