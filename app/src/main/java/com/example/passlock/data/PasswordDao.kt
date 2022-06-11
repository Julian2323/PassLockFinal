package com.example.passlock.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.passlock.model.Password

@Dao
interface PasswordDao {

    @Query("SELECT * FROM password_database ORDER BY password_name ASC")
    fun getPasswords():Flow<List<Password>>

    @Query("SELECT * FROM password_database WHERE passwordId = :id")
    fun getPassword(id: Int):Flow<Password>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(password: Password)

    @Update
    suspend fun update(password: Password)

    @Delete
    suspend fun delete(password: Password)

}