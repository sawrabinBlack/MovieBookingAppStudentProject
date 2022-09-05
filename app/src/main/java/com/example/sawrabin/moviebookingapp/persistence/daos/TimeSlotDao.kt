package com.example.sawrabin.moviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sawrabin.moviebookingapp.data.vos.CinemaDayVO

@Dao
interface TimeSlotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimeSlot (timeslotList :List<CinemaDayVO>)

    @Query("SELECT * FROM cinema_timeslots WHERE date = :date")
    fun getTimeslots(date: String):List<CinemaDayVO>

    @Query("DELETE  FROM cinema_timeslots WHERE date = :date")
    fun deleteCinemaTimeslots (date :String)
}