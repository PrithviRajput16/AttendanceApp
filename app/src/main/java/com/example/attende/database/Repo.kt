package com.example.attende.database

import kotlinx.coroutines.flow.Flow

class Repo(private val dao: AttendanceDao) {

    fun getAttendedName(day: String, month: String): Flow<List<String>> {
        return dao.getAttendedName(day,month)
    }



}