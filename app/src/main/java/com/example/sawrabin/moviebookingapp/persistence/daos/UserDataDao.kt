package com.example.sawrabin.moviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sawrabin.moviebookingapp.data.vos.UserDataVO

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDataVO)

    @Query("SELECT userToken FROM users WHERE userToken Not Null")
    fun getToken(): String?

    @Query("SELECT * FROM users WHERE userToken NOT NUll")
    fun getUserdata(): UserDataVO?

    @Query("UPDATE users SET userToken = NULL WHERE userToken Not Null")
    fun updateUserLogout()

}