package com.example.sawrabin.moviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sawrabin.moviebookingapp.data.vos.SnackVO

@Dao
interface SnackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = SnackVO::class)
    fun insertSnack(snack : List<SnackVO>)

    @Query("SELECT * FROM snacks")
    fun getSnacks():List<SnackVO>

}