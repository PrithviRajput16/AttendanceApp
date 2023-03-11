package com.example.attende.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "attende")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "subName") val sName: String,
    @ColumnInfo(name = "attended") val attended: Int
)