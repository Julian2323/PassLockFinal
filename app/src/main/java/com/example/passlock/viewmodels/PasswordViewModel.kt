package com.example.passlock.viewmodels

import androidx.lifecycle.*
import com.example.passlock.data.PasswordDao
import com.example.passlock.model.Password
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PasswordViewModel(private val passwordDao: PasswordDao): ViewModel() {

    val allPasswords: LiveData<List<Password>> = passwordDao.getPasswords().asLiveData()


    private fun insertPassword(password: Password) {
        viewModelScope.launch {
            passwordDao.insert(password)
        }
    }

    private fun getNewPasswordEntry(passwordName: String, passwordActual: String, notes: String): Password {
        return Password(
            passwordName = passwordName,
            passwordActual = passwordActual,
            notes = notes
        )
    }

    fun addNewPassword(passwordName: String, passwordActual: String, notes: String) {
        val newPassword = getNewPasswordEntry(passwordName, passwordActual, notes)
        insertPassword(newPassword)
    }

    fun isEntryValid(passwordName: String, passwordActual: String, notes: String): Boolean {
        if (passwordName.isBlank() || passwordActual.isBlank()) {
            return false
        }
        return true
    }

    fun retrievePassword(id: Int): LiveData<Password> {
        return passwordDao.getPassword(id).asLiveData()
    }

    private fun updatePassword(password: Password) {
        viewModelScope.launch {
            passwordDao.update(password)
        }
    }

    fun deletePassword(password: Password) {
        viewModelScope.launch {
            passwordDao.delete(password)
        }
    }

    private fun getUpdatedPasswordEntry(
        passwordId: Int,
        passwordName: String,
        passwordActual: String,
        notes: String
    ): Password {
        return Password(
            passwordId = passwordId,
            passwordName = passwordName,
            passwordActual = passwordActual,
            notes = notes
        )
    }

    fun updatePassword(
        passwordId: Int,
        passwordName: String,
        passwordActual: String,
        notes: String
    ) {
        val updatedPassword = getUpdatedPasswordEntry(passwordId, passwordName, passwordActual, notes)
        updatePassword(updatedPassword)
    }
}

class PasswordViewModelFactory(private val passwordDao: PasswordDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            @Suppress("UNHCHECKED_CAST")
            return PasswordViewModel(passwordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }

}