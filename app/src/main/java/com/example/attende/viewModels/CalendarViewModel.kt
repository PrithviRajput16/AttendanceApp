package com.example.attende.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.attende.database.AppDatabase
import com.example.attende.database.Repo
import kotlinx.coroutines.flow.Flow

class CalendarViewModel(application: Application): AndroidViewModel(application) {
    private val repo: Repo
    init {
        val dao = AppDatabase.getDatabase(application).AttendanceDao()
        repo = Repo(dao)

    }

    fun getAttendedName(day: String, month: String): Flow<List<String>> {
        return repo.getAttendedName(day,month)
    }
}