package com.example.passlock.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_database")
data class Password (

    @PrimaryKey(autoGenerate = true)
    val passwordId: Int = 0,
    @ColumnInfo(name = "password_name")
    val passwordName: String,
    @ColumnInfo(name = "password")
    val passwordActual: String,
    @ColumnInfo(name = "notes")
    val notes: String?
)