package com.example.attende.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sub: Attendance)

    @Query("delete from records where name = :name")
    suspend fun delete(name: String)

    @Query("delete from attende where subName = :name")
    suspend fun delete2(name: String)

    @Query("SELECT * FROM attende")
    fun getAll(): Flow<List<Attendance>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: Attendance2)

    @Query("SELECT name FROM records where day = :day and name = :subject and month = :month")
    suspend fun getDay(day: String, month: String, subject: String): Attendance2?

    @Query("SELECT COUNT(name) FROM records WHERE name = :sName")
    suspend fun getSub(sName: String): Int

    @Query("SELECT COUNT(name) FROM records WHERE name = :sName and attended = 1")
    suspend fun getAttended(sName: String): Int


    //For Calendar
    @Query("SELECT name FROM records WHERE day = :day AND month = :month and attended = 1")
    fun getAttendedName(day: String, month: String): Flow<List<String>>


    //For Stats
    @Query("SELECT COUNT(name) FROM records WHERE month = :month")
    suspend fun getAllName(month: String): Int

    @Query("SELECT COUNT(name) FROM records WHERE month = :month AND attended = 1" )
    suspend fun getAllNameAttended(month: String): Int

    @Query("DELETE FROM records")
    suspend fun deleteFromRecords()

    @Query("DELETE FROM attende")
    suspend fun deleteFromAttende()


}