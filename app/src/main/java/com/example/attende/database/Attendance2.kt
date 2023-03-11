package com.example.attende.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Attendance2(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val Day: String?,
    val Month: String?,
    val Attended: Boolean?
)