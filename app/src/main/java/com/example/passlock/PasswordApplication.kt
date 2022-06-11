package com.example.passlock

import android.app.Application
import com.example.passlock.data.PasswordRoomDatabase

class PasswordApplication: Application() {
    val database: PasswordRoomDatabase by lazy { PasswordRoomDatabase.getDatabase(this) }
}